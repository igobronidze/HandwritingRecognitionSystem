package ge.edu.tsu.hcrs.control_panel.server.processor.neuralnetwork;

import ge.edu.tsu.hcrs.control_panel.model.network.*;
import ge.edu.tsu.hcrs.control_panel.model.network.CharSequence;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.hcrs.control_panel.server.dao.networkinfo.NetworkInfoDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.networkinfo.NetworkInfoDAOImpl;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.NormalizedDataDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.NormalizedDataDAOImpl;
import ge.edu.tsu.hcrs.control_panel.server.dao.testinginfo.TestingInfoDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.testinginfo.TestingInfoDAOImpl;
import ge.edu.tsu.hcrs.control_panel.server.processor.systemparameter.SystemParameterProcessor;
import ge.edu.tsu.hcrs.neural_network.exception.NNException;
import ge.edu.tsu.hcrs.neural_network.neural.network.NeuralNetwork;
import ge.edu.tsu.hcrs.neural_network.neural.network.NeuralNetworkParameter;
import ge.edu.tsu.hcrs.neural_network.neural.network.TrainingData;
import ge.edu.tsu.hcrs.neural_network.neural.network.TrainingProgress;
import ge.edu.tsu.hcrs.neural_network.neural.testresult.TestResult;
import ge.edu.tsu.hcrs.neural_network.transfer.TransferFunctionType;

import java.util.ArrayList;
import java.util.List;

public class HCRSNeuralNetworkProcessor implements INeuralNetworkProcessor {

    private SystemParameterProcessor systemParameterProcessor = new SystemParameterProcessor();

    private NormalizedDataDAO normalizedDataDAO = new NormalizedDataDAOImpl();

    private NetworkInfoDAO networkInfoDAO = new NetworkInfoDAOImpl();

    private TestingInfoDAO testingInfoDAO = new TestingInfoDAOImpl();

    private Parameter neuralNetworkDirectoryParameter = new Parameter("neuralNetworkDirectory", "C:\\BachelorProject\\hcrs\\network");

    private Parameter updatePerIterationParameter = new Parameter("updatePerIteration", "1000");

    private Parameter updatePerSeconds = new Parameter("updatePerSeconds", "10");

    public HCRSNeuralNetworkProcessor() {
    }

    @Override
    public void trainNeural(NetworkInfo networkInfo) {
        try {
            int width = networkInfo.getWidth();
            int height = networkInfo.getHeight();
            CharSequence charSequence = networkInfo.getCharSequence();
            List<NormalizedData> normalizedDataList = normalizedDataDAO.getNormalizedDatum(networkInfo.getGroupedNormalizedDatum());
            List<Integer> layers = new ArrayList<>();
            layers.add(width * height);
            for (int x : networkInfo.getHiddenLayer()) {
                layers.add(x);
            }
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
                    NeuralNetwork.save(systemParameterProcessor.getStringParameterValue(neuralNetworkDirectoryParameter) + "\\" + id + ".nnet", neuralNetwork);
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
        } catch (NNException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public NetworkResult getNetworkResult(NormalizedData normalizedData, String networkPath, CharSequence charSequence) {
        try {
            NeuralNetwork neuralNetwork = NeuralNetwork.load(networkPath);
            TrainingData trainingData = NetworkDataCreator.getTrainingData(normalizedData, charSequence);
            List<Float> output = neuralNetwork.getOutputActivation(trainingData);
            int ans = 0;
            for (int i = 1; i < charSequence.getNumberOfChars(); i++) {
                if (output.get(i) > output.get(ans)) {
                    ans = i;
                }
            }
            char c = charSequence.getIndexToCharMap().get(ans);
            NetworkResult networkResult = new NetworkResult(output, c);
            return networkResult;
        } catch (NNException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public float test(int width, int height, List<GroupedNormalizedData> groupedNormalizedDatum, String path, int networkId, CharSequence charSequence) {
        List<NormalizedData> normalizedDataList = normalizedDataDAO.getNormalizedDatum(groupedNormalizedDatum);
        try {
            NeuralNetwork neuralNetwork = NeuralNetwork.load(path);
            List<TrainingData> trainingDataList = new ArrayList<>();
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
            testingInfo.setDiffBetweenAnsAndBest(testResult.getDuration());
            testingInfoDAO.addTestingInfo(testingInfo);
            return testingInfo.getNormalizedGeneralError();
        } catch (Exception ex) {
            System.out.println("Neural network don's exist");
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
}
