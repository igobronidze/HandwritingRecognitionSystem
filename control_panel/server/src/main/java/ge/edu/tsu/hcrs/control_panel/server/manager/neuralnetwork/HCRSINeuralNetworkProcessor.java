package ge.edu.tsu.hcrs.control_panel.server.manager.neuralnetwork;

import ge.edu.tsu.hcrs.control_panel.model.network.*;
import ge.edu.tsu.hcrs.control_panel.model.network.CharSequence;
import ge.edu.tsu.hcrs.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.hcrs.control_panel.server.dao.*;
import ge.edu.tsu.hcrs.control_panel.server.manager.NormalizedDataProcessor;
import ge.edu.tsu.hcrs.control_panel.server.manager.SystemParameterProcessor;
import ge.edu.tsu.hcrs.neural_network.exception.NNException;
import ge.edu.tsu.hcrs.neural_network.neural.network.NeuralNetwork;
import ge.edu.tsu.hcrs.neural_network.neural.network.NeuralNetworkParameter;
import ge.edu.tsu.hcrs.neural_network.neural.network.TrainingData;
import ge.edu.tsu.hcrs.neural_network.neural.testresult.TestResult;
import ge.edu.tsu.hcrs.neural_network.transfer.TransferFunctionType;

import java.util.ArrayList;
import java.util.List;

public class HCRSINeuralNetworkProcessor implements INeuralNetworkProcessor {

    private SystemParameterProcessor systemParameterProcessor = new SystemParameterProcessor();

    private NormalizedDataProcessor normalizedDataProcessor = new NormalizedDataProcessor();

    private NormalizedDataDAO normalizedDataDAO = new NormalizedDataDAOImpl();

    private NetworkInfoDAO networkInfoDAO = new NetworkInfoDAOImpl();

    private TestingInfoDAO testingInfoDAO = new TestingInfoDAOImpl();

    private Parameter firstSymbolInCharSequenceParameter = new Parameter("firstSymbolInCharSequence", "ა");

    private Parameter lastSymbolInCharSequenceParameter = new Parameter("lastSymbolInCharSequence", "ჰ");

    private Parameter weightMinValueParameter = new Parameter("weightMinValue", "-0.5");

    private Parameter weightMaxValueParameter = new Parameter("weightMaxValue", "0.5");

    private Parameter neuralInHiddenLayersParameter = new Parameter("neuralInHiddenLayers", "");

    private Parameter biasMinValueParameter = new Parameter("biasMinValue", "-0.5");

    private Parameter biasMaxValueParameter = new Parameter("biasMaxValue", "0.5");

    private Parameter transferFunctionParameter = new Parameter("transferFunction", "SIGMOID");

    private Parameter learningRateParameter = new Parameter("learningRate", "0.2");

    private Parameter minErrorParameter = new Parameter("minError", "0.05");

    private Parameter trainingMaxIterationParameter = new Parameter("trainingMaxIteration", "100000");

    private Parameter numberOfTrainingDataInOneIterationParameter = new Parameter("numberOfTrainingDataInOneIteration", "100");

    private CharSequence charSequence;

    private Parameter neuralNetworkDirectoryParameter = new Parameter("neuralNetworkDirectory", "D:\\sg\\handwriting_recognition\\network");

    public HCRSINeuralNetworkProcessor() {
        char firstSymbolInCharSequence = systemParameterProcessor.getParameterValue(firstSymbolInCharSequenceParameter).charAt(0);
        char lastSymbolInCharSequence = systemParameterProcessor.getParameterValue(lastSymbolInCharSequenceParameter).charAt(0);
        charSequence = new CharSequence(firstSymbolInCharSequence, lastSymbolInCharSequence);
    }

    @Override
    public void trainNeural(int width, int height, String generation) {
        try {
            List<NormalizedData> normalizedDataList = normalizedDataDAO.getNormalizedDatas(width, height, charSequence, generation);
            List<Integer> layers = new ArrayList<>();
            layers.add(width * height);
            for (int x : systemParameterProcessor.getIntegerListParameterValue(neuralInHiddenLayersParameter)) {
                layers.add(x);
            }
            layers.add(charSequence.getNumberOfChars());
            NeuralNetwork neuralNetwork = new NeuralNetwork(layers);
            setNeuralNetworkParameters(neuralNetwork);
            for (int i = 0; i < normalizedDataList.size(); i++) {
                neuralNetwork.addTrainingData(normalizedDataProcessor.getTrainingData(normalizedDataList.get(i), charSequence));
            }
            long trainingDuration = neuralNetwork.train();
            int id = networkInfoDAO.addNetworkInfo(getNetworkInfo(neuralNetwork, trainingDuration, width, height, generation));
            NeuralNetwork.save(systemParameterProcessor.getParameterValue(neuralNetworkDirectoryParameter) + "\\" + id + "_" + generation + "_" + width + "_" + height + ".nnet", neuralNetwork);
        } catch (NNException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public NetworkResult getNetworkResult(NormalizedData normalizedData, String networkPath) {
        try {
            NeuralNetwork neuralNetwork = NeuralNetwork.load(networkPath);
            TrainingData trainingData = normalizedDataProcessor.getTrainingData(normalizedData, charSequence);
            List<Float> output = neuralNetwork.getOutputActivation(trainingData);
            int ans = 0;
            for (int i = 1; i < charSequence.getNumberOfChars(); i++) {
                if (output.get(i) > output.get(ans)) {
                    ans = i;
                }
            }
            char c = (char) (ans + charSequence.getFirstCharASCI());
            NetworkResult networkResult = new NetworkResult(output, c);
            return networkResult;
        } catch (NNException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public float test(int width, int height, String generation, String path, int networkId) {
        List<NormalizedData> normalizedDataList = normalizedDataDAO.getNormalizedDatas(width, height, charSequence, generation);
        try {
            NeuralNetwork neuralNetwork = NeuralNetwork.load(path);
            List<TrainingData> trainingDataList = new ArrayList<>();
            for (NormalizedData normalizedData : normalizedDataList) {
                trainingDataList.add(normalizedDataProcessor.getTrainingData(normalizedData, charSequence));
            }
            TestResult testResult = neuralNetwork.test(trainingDataList);
            TestingInfo testingInfo = new TestingInfo();
            testingInfo.setNumberOfTest(testResult.getNumberOfData());
            testingInfo.setNetworkId(networkId);
            testingInfo.setGeneration(generation);
            testingInfo.setSquaredError(testResult.getSquaredError());
            testingInfo.setDiffBetweenAnsAndBest(testResult.getDiffBetweenAnsAndBest());
            testingInfo.setPercentageOfCorrects(testResult.getPercentageOfCorrects());
            testingInfo.setNormalizedGeneralError(testResult.getNormalizedGeneralError());
            testingInfoDAO.addTestingInfo(testingInfo);
            return testingInfo.getNormalizedGeneralError();
        } catch (Exception ex) {
            System.out.println("Neural network don's exist");
        }
        return -1;
    }

    private void setNeuralNetworkParameters(NeuralNetwork neuralNetwork) {
        NeuralNetworkParameter parameter = neuralNetwork.getNeuralNetworkParameter();
        parameter.setWeightMinValue(systemParameterProcessor.getFloatParameterValue(weightMinValueParameter));
        parameter.setWeightMaxValue(systemParameterProcessor.getFloatParameterValue(weightMaxValueParameter));
        parameter.setBiasMinValue(systemParameterProcessor.getFloatParameterValue(biasMinValueParameter));
        parameter.setBiasMaxValue(systemParameterProcessor.getFloatParameterValue(biasMaxValueParameter));
        parameter.setTransferFunctionType(TransferFunctionType.valueOf(systemParameterProcessor.getParameterValue(transferFunctionParameter)));
        parameter.setLearningRate(systemParameterProcessor.getFloatParameterValue(learningRateParameter));
        parameter.setMinError(systemParameterProcessor.getFloatParameterValue(minErrorParameter));
        parameter.setTrainingMaxIteration(systemParameterProcessor.getLongParameterValue(trainingMaxIterationParameter));
        parameter.setNumberOfTrainingDataInOneIteration(systemParameterProcessor.getLongParameterValue(numberOfTrainingDataInOneIterationParameter));
    }

    private NetworkInfo getNetworkInfo(NeuralNetwork neuralNetwork, long trainingDuration, int width, int height, String generation) {
        NeuralNetworkParameter parameter = neuralNetwork.getNeuralNetworkParameter();
        NetworkInfo networkInfo = new NetworkInfo();
        networkInfo.setWidth(width);
        networkInfo.setHeight(height);
        networkInfo.setGeneration(generation);
        networkInfo.setNumberOfData(neuralNetwork.getTrainingDataList().size());
        networkInfo.setTrainingDuration(trainingDuration);
        networkInfo.setWeightMinValue(parameter.getWeightMinValue());
        networkInfo.setWeightMaxValue(parameter.getWeightMaxValue());
        networkInfo.setBiasMinValue(parameter.getBiasMinValue());
        networkInfo.setBiasMaxValue(parameter.getBiasMaxValue());
        networkInfo.setTransferFunction(TransferFunction.valueOf(parameter.getTransferFunctionType().name()));
        networkInfo.setLearningRate(parameter.getLearningRate());
        networkInfo.setMinError(parameter.getMinError());
        networkInfo.setTrainingMaxIteration(parameter.getTrainingMaxIteration());
        networkInfo.setNumberOfTrainingDataInOneIteration(parameter.getNumberOfTrainingDataInOneIteration());
        networkInfo.setTestingInfoList(new ArrayList<>());
        networkInfo.setCharSequence(charSequence.getFirstSymbol() + "-" + charSequence.getLastSymbol());
        String hiddenLayer = "";
        for (int i = 1; i < neuralNetwork.getLayersSize().size() - 1; i++) {
            hiddenLayer += neuralNetwork.getLayersSize().get(i) + ",";
        }
        if (hiddenLayer.isEmpty()) {
            networkInfo.setHiddenLayer(hiddenLayer);
        } else {
            networkInfo.setHiddenLayer(hiddenLayer.substring(0, hiddenLayer.length() - 1));
        }
        return networkInfo;
    }
}
