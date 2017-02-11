package ge.edu.tsu.hcrs.neural_network.neural.network;

import ge.edu.tsu.hcrs.neural_network.exception.LayersSizeNNException;
import ge.edu.tsu.hcrs.neural_network.exception.NNException;
import ge.edu.tsu.hcrs.neural_network.exception.TrainingDataNNException;
import ge.edu.tsu.hcrs.neural_network.neural.action.Activation;
import ge.edu.tsu.hcrs.neural_network.neural.action.Backpropagation;
import ge.edu.tsu.hcrs.neural_network.neural.testresult.TestResult;
import ge.edu.tsu.hcrs.neural_network.neural.testresult.TestResultUtil;
import ge.edu.tsu.hcrs.neural_network.util.Randomizer;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NeuralNetwork implements Serializable {

    public static final long serialVersionUID = 25245248L;

    private NeuralNetworkParameter neuralNetworkParameter = new NeuralNetworkParameter();

    private List<Integer> layersSize;

    private List<Neuron> inputNeurons = new ArrayList<>();

    private List<Neuron> hiddenNeurons = new ArrayList<>();

    private List<Neuron> outputNeurons = new ArrayList<>();

    private List<TrainingData> trainingDataList = new ArrayList<>();

    public NeuralNetworkParameter getNeuralNetworkParameter() {
        return neuralNetworkParameter;
    }

    public List<Neuron> getInputNeurons() {
        return inputNeurons;
    }

    public List<Neuron> getHiddenNeurons() {
        return hiddenNeurons;
    }

    public List<Neuron> getOutputNeurons() {
        return outputNeurons;
    }

    public List<TrainingData> getTrainingDataList() {
        return trainingDataList;
    }

    public List<Integer> getLayersSize() {
        return layersSize;
    }

    public NeuralNetwork(List<Integer> layersSize) throws LayersSizeNNException {
        this.layersSize = layersSize;
        if (layersSize.size() < 2) {
            throw new LayersSizeNNException("Layers size must be more than one");
        }
        List<Neuron> lastLayerNeurons = new ArrayList<>();
        List<Neuron> tmpNeurons = new ArrayList<>();
        for (int i = 0; i < layersSize.get(0); i++) {
            Neuron neuron = Randomizer.getRandomNeuron(this);
            inputNeurons.add(neuron);
            tmpNeurons.add(neuron);
        }
        lastLayerNeurons.clear();
        lastLayerNeurons.addAll(tmpNeurons);
        tmpNeurons.clear();
        for (int i = 1; i < layersSize.size(); i++) {
            for (int j = 0; j < layersSize.get(i); j++) {
                Neuron neuron = Randomizer.getRandomNeuron(this);
                if (i == layersSize.size() - 1) {
                    outputNeurons.add(neuron);
                } else {
                    hiddenNeurons.add(neuron);
                }
                tmpNeurons.add(neuron);
                for (int k = 0; k < lastLayerNeurons.size(); k++) {
                    Connection connection = Randomizer.getRandomConnection(this, lastLayerNeurons.get(k), neuron);
                    neuron.getInConnections().add(connection);
                    lastLayerNeurons.get(k).getOutConnections().add(connection);
                    connection.setLeftNeuron(lastLayerNeurons.get(k));
                    connection.setRightNeuron(neuron);
                }
            }
            lastLayerNeurons.clear();
            lastLayerNeurons.addAll(tmpNeurons);
            tmpNeurons.clear();
        }
    }

    public void addTrainingData(TrainingData trainingData) throws TrainingDataNNException {
        if (trainingData.getInput().size() != layersSize.get(0)) {
            throw new TrainingDataNNException("Training data's input size and neural network input neurons size must be equal");
        }
        if (trainingData.getOutput().size() != layersSize.get(layersSize.size() - 1)) {
            throw new TrainingDataNNException("Training data's output size and neural network output neurons size must be equal");
        }
        this.trainingDataList.add(trainingData);
    }

    public long train() {
        int counter = 0;
        float error;
        long startTime = new Date().getTime();
        do {
            error = 0.0f;
            List<Integer> randomList = Randomizer.getRandomIntegerList(trainingDataList.size());
            long min = Math.min(neuralNetworkParameter.getNumberOfTrainingDataInOneIteration(), trainingDataList.size());
            for (int i = 0; i < min; i++) {
                TrainingData trainingData = trainingDataList.get(randomList.get(i));
                Activation.activate(this, trainingData);
                List<Float> outputActivation = getOutputActivation(trainingData);
                for (Neuron neuron : outputNeurons) {
                    outputActivation.add(neuron.getActivationValue());
                }
                error += TestResultUtil.getSquaredError(trainingData.getOutput(), outputActivation);
                Backpropagation.backpropagation(this, trainingData);
            }
            counter++;
            if (counter % 500 == 0) {
                System.out.println(counter);
                System.out.println(error);
            }
        } while (counter < neuralNetworkParameter.getTrainingMaxIteration() && error > neuralNetworkParameter.getMinError());
        return new Date().getTime() - startTime;
    }

    public TestResult test(List<TrainingData> trainingDataList) throws TrainingDataNNException {
        if (trainingDataList.size() == 0) {
            throw new TrainingDataNNException("Test data size must not be zero");
        }
        float squaredError = 0;
        float numberOfCorrects = 0;
        float diffBetweenAnsAndBest = 0;
        for (TrainingData trainingData : trainingDataList) {
            Activation.activate(this, trainingData);
            List<Float> outputActivation = getOutputActivation(trainingData);
            for (Neuron neuron : outputNeurons) {
                outputActivation.add(neuron.getActivationValue());
            }
            squaredError += TestResultUtil.getSquaredError(trainingData.getOutput(), outputActivation);
            numberOfCorrects += TestResultUtil.isCorrect(trainingData.getOutput(), outputActivation) ? 1 : 0;
            diffBetweenAnsAndBest += TestResultUtil.getDiffBetweenAnsAndBest(trainingData.getOutput(), outputActivation);
        }
        TestResult testResult = new TestResult();
        testResult.setNumberOfData(trainingDataList.size());
        testResult.setSquaredError(squaredError);
        testResult.setPercentageOfCorrects(numberOfCorrects * 100 / trainingDataList.size());
        testResult.setDiffBetweenAnsAndBest(diffBetweenAnsAndBest);
        testResult.setNormalizedGeneralError(TestResultUtil.getNormalizedGeneralError(testResult));
        return testResult;
    }

    public List<Float> getOutputActivation(TrainingData trainingData) {
        Activation.activate(this, trainingData);
        List<Float> output = new ArrayList<>();
        for (Neuron neuron : outputNeurons) {
            output.add(neuron.getActivationValue());
        }
        return output;
    }

    public static void save(String url, NeuralNetwork neuralNetwork) throws NNException {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(url));
            out.writeObject(neuralNetwork);
        } catch (IOException ex) {
            throw new NNException(ex.getMessage());
        }
    }

    public static NeuralNetwork load(String url) throws NNException {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(url));
            return (NeuralNetwork)in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            throw new NNException(ex.getMessage());
        }
    }
}
