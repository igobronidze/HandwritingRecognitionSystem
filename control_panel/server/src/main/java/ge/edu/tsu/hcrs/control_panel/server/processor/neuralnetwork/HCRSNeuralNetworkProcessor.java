package ge.edu.tsu.hcrs.control_panel.server.processor.neuralnetwork;

import ge.edu.tsu.hcrs.control_panel.model.exception.ControlPanelException;
import ge.edu.tsu.hcrs.control_panel.model.network.*;
import ge.edu.tsu.hcrs.control_panel.model.network.CharSequence;
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
import ge.edu.tsu.hcrs.control_panel.server.processor.systemparameter.SystemParameterProcessor;
import ge.edu.tsu.hcrs.control_panel.server.util.CharSequenceInitializer;
import ge.edu.tsu.hcrs.control_panel.server.util.GroupedNormalizedDataUtil;
import ge.edu.tsu.hcrs.control_panel.server.util.SystemPathUtil;
import ge.edu.tsu.hcrs.neural_network.exception.NNException;
import ge.edu.tsu.hcrs.neural_network.neural.network.NeuralNetwork;
import ge.edu.tsu.hcrs.neural_network.neural.network.NeuralNetworkParameter;
import ge.edu.tsu.hcrs.neural_network.neural.network.TrainingData;
import ge.edu.tsu.hcrs.neural_network.neural.network.TrainingProgress;
import ge.edu.tsu.hcrs.neural_network.neural.testresult.TestResult;
import ge.edu.tsu.hcrs.neural_network.transfer.TransferFunctionType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HCRSNeuralNetworkProcessor implements INeuralNetworkProcessor {

    private SystemParameterProcessor systemParameterProcessor = new SystemParameterProcessor();

    private NormalizedDataDAO normalizedDataDAO = new NormalizedDataDAOImpl();

    private NetworkInfoDAO networkInfoDAO = new NetworkInfoDAOImpl();

    private TestingInfoDAO testingInfoDAO = new TestingInfoDAOImpl();

    private NeuralNetworkDAO neuralNetworkDAO = new NeuralNetworkDAOImpl();

    private Parameter updatePerIterationParameter = new Parameter("updatePerIteration", "1000");

    private Parameter updatePerSeconds = new Parameter("updatePerSeconds", "10");

    public HCRSNeuralNetworkProcessor() {
    }

    @Override
    public void trainNeural(NetworkInfo networkInfo, boolean saveInDatabase) throws ControlPanelException {
        try {
            if (!GroupedNormalizedDataUtil.checkGroupedNormalizedDataList(networkInfo.getGroupedNormalizedDatum())) {
                throw new ControlPanelException();
            }
            int width = networkInfo.getGroupedNormalizedDatum().get(0).getWidth();
            int height = networkInfo.getGroupedNormalizedDatum().get(0).getHeight();
            CharSequence charSequence = networkInfo.getCharSequence();
            List<NormalizedData> normalizedDataList = normalizedDataDAO.getNormalizedDatum(networkInfo.getGroupedNormalizedDatum());
            List<Integer> layers = new ArrayList<>();
            layers.add(width * height);
            layers.addAll(networkInfo.getHiddenLayer());
            layers.add(charSequence.getNumberOfChars());
            NeuralNetwork neuralNetwork = new NeuralNetwork(layers);
            setNeuralNetworkParameters(neuralNetwork, networkInfo);
            for (int i = 0; i < normalizedDataList.size(); i++) {
                neuralNetwork.addTrainingData(NetworkDataCreator.getTrainingData(normalizedDataList.get(i), charSequence));
            }
            TrainingProgress trainingProgress = new TrainingProgress();
            trainingProgress.setUpdatePerIteration(systemParameterProcessor.getLongParameterValue(updatePerIterationParameter));
            networkInfo.setTrainingStatus(NetworkTrainingStatus.TRAINING);
            networkInfo.setNumberOfData(neuralNetwork.getTrainingDataList().size());
            int id = networkInfoDAO.addNetworkInfo(networkInfo);
            Runnable run = () -> {
                try {
                    long trainingDuration = neuralNetwork.train(trainingProgress);
                    networkInfoDAO.updateTrainedState(trainingDuration, id);
                    networkInfo.setTrainingStatus(NetworkTrainingStatus.TRAINED);
                    NeuralNetwork.save(SystemPathUtil.getNeuralNetworkPath() + "/" + id + ".nnet", neuralNetwork);
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

    @Override
    public NetworkResult getNetworkResult(NormalizedData normalizedData, int networkId) {
        try {
            NeuralNetwork neuralNetwork = loadNeuralNetwork(networkId);
            CharSequence charSequence = networkInfoDAO.getCharSequenceById(networkId);
            CharSequenceInitializer.initializeCharSequence(charSequence);
            TrainingData trainingData = NetworkDataCreator.getTrainingData(normalizedData, charSequence);
            List<Float> output = neuralNetwork.getOutputActivation(trainingData);
            int ans = 0;
            for (int i = 1; i < charSequence.getNumberOfChars(); i++) {
                if (output.get(i) > output.get(ans)) {
                    ans = i;
                }
            }
            char c = charSequence.getIndexToCharMap().get(ans);
            NetworkResult networkResult = new NetworkResult();
            networkResult.setOutputActivation(output);
            networkResult.setAnswer(c);
            networkResult.setCharSequence(charSequence);
            return networkResult;
        } catch (ControlPanelException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
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
            neuralNetwork = NeuralNetwork.load(SystemPathUtil.getNeuralNetworkPath() + "/" + id + ".nnet");
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
