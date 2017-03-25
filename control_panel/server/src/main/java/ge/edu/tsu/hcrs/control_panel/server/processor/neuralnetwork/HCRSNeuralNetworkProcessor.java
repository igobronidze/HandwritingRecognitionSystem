package ge.edu.tsu.hcrs.control_panel.server.processor.neuralnetwork;

import ge.edu.tsu.hcrs.control_panel.model.common.HCRSPath;
import ge.edu.tsu.hcrs.control_panel.model.exception.ControlPanelException;
import ge.edu.tsu.hcrs.control_panel.model.network.CharSequence;
import ge.edu.tsu.hcrs.control_panel.model.network.NetworkInfo;
import ge.edu.tsu.hcrs.control_panel.model.network.NetworkResult;
import ge.edu.tsu.hcrs.control_panel.model.network.NetworkTrainingStatus;
import ge.edu.tsu.hcrs.control_panel.model.network.TestingInfo;
import ge.edu.tsu.hcrs.control_panel.model.network.TrainingDataInfo;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.hcrs.control_panel.server.dao.networkinfo.NetworkInfoDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.networkinfo.NetworkInfoDAOImpl;
import ge.edu.tsu.hcrs.control_panel.server.dao.neuralnetwork.NeuralNetworkDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.neuralnetwork.NeuralNetworkDAOImpl;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.NormalizedDataDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.NormalizedDataDAOImpl;
import ge.edu.tsu.hcrs.control_panel.server.dao.testinginfo.TestingInfoDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.testinginfo.TestingInfoDAOImpl;
import ge.edu.tsu.hcrs.control_panel.server.dao.trainingdatainfo.TrainingDataInfoDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.trainingdatainfo.TrainingDataInfoDAOImpl;
import ge.edu.tsu.hcrs.control_panel.server.processor.common.HCRSPathProcessor;
import ge.edu.tsu.hcrs.control_panel.server.processor.normalizeddata.normalizationmethod.Normalization;
import ge.edu.tsu.hcrs.control_panel.server.processor.systemparameter.SystemParameterProcessor;
import ge.edu.tsu.hcrs.control_panel.server.util.CharSequenceInitializer;
import ge.edu.tsu.hcrs.control_panel.server.util.GroupedNormalizedDataUtil;
import ge.edu.tsu.hcrs.image_processing.characterdetect.detector.ContoursDetector;
import ge.edu.tsu.hcrs.image_processing.characterdetect.detector.TextCutterParams;
import ge.edu.tsu.hcrs.image_processing.characterdetect.model.Contour;
import ge.edu.tsu.hcrs.image_processing.characterdetect.model.TextAdapter;
import ge.edu.tsu.hcrs.image_processing.characterdetect.model.TextRow;
import ge.edu.tsu.hcrs.image_processing.characterdetect.util.TextAdapterUtil;
import ge.edu.tsu.hcrs.neural_network.exception.NNException;
import ge.edu.tsu.hcrs.neural_network.neural.network.NeuralNetwork;
import ge.edu.tsu.hcrs.neural_network.neural.network.NeuralNetworkParameter;
import ge.edu.tsu.hcrs.neural_network.neural.network.TrainingData;
import ge.edu.tsu.hcrs.neural_network.neural.network.TrainingProgress;
import ge.edu.tsu.hcrs.neural_network.neural.testresult.TestResult;
import ge.edu.tsu.hcrs.neural_network.transfer.TransferFunctionType;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class HCRSNeuralNetworkProcessor implements INeuralNetworkProcessor {

    private final SystemParameterProcessor systemParameterProcessor = new SystemParameterProcessor();

    private final NormalizedDataDAO normalizedDataDAO = new NormalizedDataDAOImpl();

    private final NetworkInfoDAO networkInfoDAO = new NetworkInfoDAOImpl();

    private final TestingInfoDAO testingInfoDAO = new TestingInfoDAOImpl();

    private final NeuralNetworkDAO neuralNetworkDAO = new NeuralNetworkDAOImpl();

    private final TrainingDataInfoDAO trainingDataInfoDAO = new TrainingDataInfoDAOImpl();

    private final HCRSPathProcessor hcrsPathProcessor = new HCRSPathProcessor();

    private final Parameter updatePerIterationParameter = new Parameter("updatePerIteration", "1000");

    private final Parameter updatePerSeconds = new Parameter("updatePerSeconds", "10");

    private final Parameter deltaForNotSpaces = new Parameter("deltaForNotSpaces", "3");

    @Override
    public void trainNeural(NetworkInfo networkInfo, boolean saveInDatabase) throws ControlPanelException {
        try {
            if (!GroupedNormalizedDataUtil.checkGroupedNormalizedDataList(networkInfo.getGroupedNormalizedDatum())) {
                throw new ControlPanelException();
            }
            GroupedNormalizedData groupedNormalizedData = networkInfo.getGroupedNormalizedDatum().get(0);
            int width = groupedNormalizedData.getWidth();
            int height = groupedNormalizedData.getHeight();
            CharSequence charSequence = networkInfo.getCharSequence();
            List<NormalizedData> normalizedDataList = normalizedDataDAO.getNormalizedDatum(networkInfo.getGroupedNormalizedDatum());
            List<Integer> layers = new ArrayList<>();
            layers.add(width * height);
            layers.addAll(networkInfo.getHiddenLayer());
            layers.add(charSequence.getNumberOfChars());
            NeuralNetwork neuralNetwork = new NeuralNetwork(layers);
            setNeuralNetworkParameters(neuralNetwork, networkInfo);
            for (NormalizedData aNormalizedDataList : normalizedDataList) {
                neuralNetwork.addTrainingData(NetworkDataCreator.getTrainingData(aNormalizedDataList, charSequence));
            }
            TrainingProgress trainingProgress = new TrainingProgress();
            trainingProgress.setUpdatePerIteration(systemParameterProcessor.getLongParameterValue(updatePerIterationParameter));
            networkInfo.setTrainingStatus(NetworkTrainingStatus.TRAINING);
            int id = networkInfoDAO.addNetworkInfo(networkInfo);
            TrainingDataInfo trainingDataInfo = new TrainingDataInfo(id, getIdsFromFromGroupedNormalizedDatum(networkInfo.getGroupedNormalizedDatum()), height, width, groupedNormalizedData.getMinValue(), groupedNormalizedData.getMaxValue(),
                    groupedNormalizedData.getNormalizationType(), neuralNetwork.getTrainingDataList().size());
            trainingDataInfoDAO.addTrainingDataInfo(trainingDataInfo);
            Runnable run = () -> {
                try {
                    long trainingDuration = neuralNetwork.train(trainingProgress);
                    networkInfoDAO.updateTrainedState(trainingDuration, id);
                    networkInfo.setTrainingStatus(NetworkTrainingStatus.TRAINED);
                    NeuralNetwork.save(hcrsPathProcessor.getPath(HCRSPath.NEURAL_NETWORKS_PATH) + "/" + id + ".nnet", neuralNetwork);
                    if (saveInDatabase) {
                        saveNeuralNetwork(id, neuralNetwork);
                    }
                } catch (NNException ex) {
                    System.out.println(ex.getMessage());
                }
            };
            new Thread(run).start();
            while (networkInfo.getTrainingStatus() == NetworkTrainingStatus.TRAINING) {
                try {
                    networkInfoDAO.updateTrainingCurrentState(trainingProgress.getCurrentSquaredError(), trainingProgress.getCurrentIterations(), trainingProgress.getCurrentDuration(), id);
                    Thread.sleep(systemParameterProcessor.getIntegerParameterValue(updatePerSeconds) * 1000);
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            networkInfoDAO.updateTrainingCurrentState(trainingProgress.getCurrentSquaredError(), trainingProgress.getCurrentIterations(), trainingProgress.getCurrentDuration(), id);
        } catch (NNException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public float testNeural(List<GroupedNormalizedData> groupedNormalizedDatum, int networkId) throws ControlPanelException {
        if (!GroupedNormalizedDataUtil.checkGroupedNormalizedDataList(groupedNormalizedDatum)) {
            throw new ControlPanelException();
        }
        NetworkInfo networkInfo = networkInfoDAO.getNetworkInfoList(networkId).get(0);
        if (!GroupedNormalizedDataUtil.compareGroupedNormalizedDatum(groupedNormalizedDatum.get(0), networkInfo.getGroupedNormalizedDatum().get(0))) {
            throw new ControlPanelException();
        }
        List<NormalizedData> normalizedDataList = normalizedDataDAO.getNormalizedDatum(groupedNormalizedDatum);
        try {
            NeuralNetwork neuralNetwork = loadNeuralNetwork(networkId);
            List<TrainingData> trainingDataList = new ArrayList<>();
            CharSequence charSequence = networkInfo.getCharSequence();
            CharSequenceInitializer.initializeCharSequence(charSequence);
            for (NormalizedData normalizedData : normalizedDataList) {
                trainingDataList.add(NetworkDataCreator.getTrainingData(normalizedData, charSequence));
            }
            TestResult testResult = neuralNetwork.test(trainingDataList);
            TestingInfo testingInfo = new TestingInfo();
            testingInfo.setNumberOfTest(testResult.getNumberOfData());
            testingInfo.setNetworkId(networkId);
            testingInfo.setGroupedNormalizedDatum(groupedNormalizedDatum);
            testingInfo.setSquaredError(testResult.getSquaredError());
            testingInfo.setDiffBetweenAnsAndBest(testResult.getDiffBetweenAnsAndBest());
            testingInfo.setPercentageOfIncorrect(testResult.getPercentageOfIncorrect());
            testingInfo.setNormalizedGeneralError(testResult.getNormalizedGeneralError());
            testingInfo.setDuration(testResult.getDuration());
            testingInfoDAO.addTestingInfo(testingInfo);
            return testingInfo.getNormalizedGeneralError();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }

    @Override
    public NetworkResult getNetworkResult(BufferedImage image, int networkId) {
        try {
            NeuralNetwork neuralNetwork = loadNeuralNetwork(networkId);
            CharSequence charSequence = networkInfoDAO.getCharSequenceById(networkId);
            TrainingDataInfo trainingDataInfo = trainingDataInfoDAO.getTrainingDataInfo(networkId);
            Normalization normalization = Normalization.getInstance(trainingDataInfo.getNormalizationType());
            NormalizedData normalizedData = normalization.getNormalizedDataFromImage(image, trainingDataInfo, null);
            CharSequenceInitializer.initializeCharSequence(charSequence);
            TrainingData trainingData = NetworkDataCreator.getTrainingData(normalizedData, charSequence);
            List<Float> output = neuralNetwork.getOutputActivation(trainingData);
            NetworkResult networkResult = new NetworkResult();
            networkResult.setOutputActivation(output);
            networkResult.setAnswer(getAns(output, charSequence));
            networkResult.setCharSequence(charSequence);
            return networkResult;
        } catch (ControlPanelException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public String recognizeText(BufferedImage image, int networkId) {
        StringBuilder text = new StringBuilder();
        NeuralNetwork neuralNetwork = loadNeuralNetwork(networkId);
        CharSequence charSequence = networkInfoDAO.getCharSequenceById(networkId);
        TrainingDataInfo trainingDataInfo = trainingDataInfoDAO.getTrainingDataInfo(networkId);
        Normalization normalization = Normalization.getInstance(trainingDataInfo.getNormalizationType());
        TextAdapter textAdapter = ContoursDetector.detectContours(image, new TextCutterParams());
        for (TextRow textRow : textAdapter.getRows()) {
            int rightPoint = -1;
            for (Contour contour : textRow.getContours()) {
                if (rightPoint != -1) {
                    if (TextAdapterUtil.isSpace(textAdapter, contour.getLeftPoint() - rightPoint + 1, systemParameterProcessor.getIntegerParameterValue(deltaForNotSpaces))) {
                        text.append(" ");
                    }
                }
                rightPoint = contour.getRightPoint();
                NormalizedData normalizedData = normalization.getNormalizedDataFromContour(contour, trainingDataInfo);
                TrainingData trainingData = NetworkDataCreator.getTrainingData(normalizedData, charSequence);
                List<Float> output = neuralNetwork.getOutputActivation(trainingData);
                text.append(getAns(output, charSequence));
                updateDoubleQuotes(text);
            }
            text.append(System.lineSeparator());
        }

        return text.toString();
    }

    private void updateDoubleQuotes(StringBuilder text) {
        if (text.length() <=1 ) {
            return;
        }
        int lastIndex = text.length() - 1;
        if ((text.charAt(lastIndex) == ',' || text.charAt(lastIndex) == '\'') && (text.charAt(lastIndex - 1) == ',' || text.charAt(lastIndex - 1) == '\'')) {
            text.delete(lastIndex - 1, lastIndex);
        }
    }

    private char getAns(List<Float> output, CharSequence charSequence) {
        int ans = 0;
        for (int i = 1; i < charSequence.getNumberOfChars(); i++) {
            if (output.get(i) > output.get(ans)) {
                ans = i;
            }
        }
        return charSequence.getIndexToCharMap().get(ans);
    }

    private List<Integer> getIdsFromFromGroupedNormalizedDatum(List<GroupedNormalizedData> groupedNormalizedDatum) {
        List<Integer> ids = new ArrayList<>();
        for (GroupedNormalizedData groupedNormalizedData : groupedNormalizedDatum) {
            ids.add(groupedNormalizedData.getId());
        }
        return ids;
    }

    private void saveNeuralNetwork(int id, NeuralNetwork neuralNetwork) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(neuralNetwork);
            out.flush();
            byte[] bytes = bos.toByteArray();
            neuralNetworkDAO.addNeuralNetwork(id, bytes);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void setNeuralNetworkParameters(NeuralNetwork neuralNetwork, NetworkInfo networkInfo) {
        NeuralNetworkParameter parameter = neuralNetwork.getNeuralNetworkParameter();
        parameter.setWeightMinValue(networkInfo.getWeightMinValue());
        parameter.setWeightMaxValue(networkInfo.getWeightMaxValue());
        parameter.setBiasMinValue(networkInfo.getBiasMinValue());
        parameter.setBiasMaxValue(networkInfo.getBiasMaxValue());
        parameter.setTransferFunctionType(TransferFunctionType.valueOf(networkInfo.getTransferFunction().name()));
        parameter.setLearningRate(networkInfo.getLearningRate());
        parameter.setMinError(networkInfo.getMinError());
        parameter.setTrainingMaxIteration(networkInfo.getTrainingMaxIteration());
        parameter.setNumberOfTrainingDataInOneIteration(networkInfo.getNumberOfTrainingDataInOneIteration());
    }

    private NeuralNetwork loadNeuralNetwork(int id) {
        NeuralNetwork neuralNetwork;
        try {
            neuralNetwork = NeuralNetwork.load(hcrsPathProcessor.getPath(HCRSPath.NEURAL_NETWORKS_PATH) + "/" + id + ".nnet");
        } catch (NNException ex) {
            System.out.println(ex.getMessage());
            neuralNetwork = loadFromDatabase(id);
        }
        return neuralNetwork;
    }

    private NeuralNetwork loadFromDatabase(int id) {
        NeuralNetwork neuralNetwork = null;
        byte[] bytes = neuralNetworkDAO.getNeuralNetworkData(id);
        if (bytes == null) {
            return null;
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try (ObjectInput in = new ObjectInputStream(bis)){
            neuralNetwork = (NeuralNetwork)in.readObject();
        } catch (IOException | ClassCastException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return neuralNetwork;
    }
}
