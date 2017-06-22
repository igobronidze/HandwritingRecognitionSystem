package ge.edu.tsu.hrs.neural_network.neural.network;

import ge.edu.tsu.hrs.neural_network.exception.LayersSizeNNException;
import ge.edu.tsu.hrs.neural_network.exception.NNException;
import ge.edu.tsu.hrs.neural_network.exception.TrainingDataNNException;
import ge.edu.tsu.hrs.neural_network.neural.action.Activation;
import ge.edu.tsu.hrs.neural_network.neural.action.Backpropagation;
import ge.edu.tsu.hrs.neural_network.neural.testresult.TestResult;
import ge.edu.tsu.hrs.neural_network.neural.testresult.TestResultUtil;
import ge.edu.tsu.hrs.neural_network.util.RandomUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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

    private transient List<TrainingData> trainingDataList = new ArrayList<>();

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

    public void setNeuralNetworkParameter(NeuralNetworkParameter neuralNetworkParameter) {
        this.neuralNetworkParameter = neuralNetworkParameter;
    }

    public void setLayersSize(List<Integer> layersSize) {
        this.layersSize = layersSize;
    }

    public void setInputNeurons(List<Neuron> inputNeurons) {
        this.inputNeurons = inputNeurons;
    }

    public void setHiddenNeurons(List<Neuron> hiddenNeurons) {
        this.hiddenNeurons = hiddenNeurons;
    }

    public void setOutputNeurons(List<Neuron> outputNeurons) {
        this.outputNeurons = outputNeurons;
    }

    public void setTrainingDataList(List<TrainingData> trainingDataList) {
        this.trainingDataList = trainingDataList;
    }

    public NeuralNetwork() {
    }

    public NeuralNetwork(List<Integer> layersSize) throws LayersSizeNNException {
        this.layersSize = layersSize;
        if (layersSize.size() < 2) {
            throw new LayersSizeNNException("Layers size must be more than one");
        }
        List<Neuron> lastLayerNeurons = new ArrayList<>();
        List<Neuron> tmpNeurons = new ArrayList<>();
        for (int i = 0; i < layersSize.get(0); i++) {
            Neuron neuron = RandomUtil.getRandomNeuron(this);
            inputNeurons.add(neuron);
            tmpNeurons.add(neuron);
        }
        lastLayerNeurons.clear();
        lastLayerNeurons.addAll(tmpNeurons);
        tmpNeurons.clear();
        for (int i = 1; i < layersSize.size(); i++) {
            for (int j = 0; j < layersSize.get(i); j++) {
                Neuron neuron = RandomUtil.getRandomNeuron(this);
                if (i == layersSize.size() - 1) {
                    outputNeurons.add(neuron);
                } else {
                    hiddenNeurons.add(neuron);
                }
                tmpNeurons.add(neuron);
                for (Neuron lastLayerNeuron : lastLayerNeurons) {
                    Connection connection = RandomUtil.getRandomConnection(this, lastLayerNeuron, neuron);
                    neuron.getInConnections().add(connection);
                    lastLayerNeuron.getOutConnections().add(connection);
                    connection.setLeftNeuron(lastLayerNeuron);
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

    public long train(TrainingProgress trainingProgress) {
        long counter = 0;
        float error;
        long startTime = new Date().getTime();
        File file = new File(trainingProgress.getTmpNetworksPath());
        file.mkdirs();
        do {
            error = 0.0f;
            List<Integer> randomList = RandomUtil.getRandomIntegerList(trainingDataList.size());
            long min = Math.min(neuralNetworkParameter.getNumberOfTrainingDataInOneIteration(), trainingDataList.size());
            for (int i = 0; i < min; i++) {
                TrainingData trainingData = trainingDataList.get(randomList.get(i));
                Activation.activate(this, trainingData);
                List<Float> outputActivation = getOutputActivation(trainingData);
                error += TestResultUtil.getSquaredError(trainingData.getOutput(), outputActivation);
                Backpropagation.backpropagation(this, trainingData);
            }
            counter++;
            if (counter % trainingProgress.getUpdatePerIteration() == 0) {
                trainingProgress.setCurrentDuration(new Date().getTime() - startTime);
                trainingProgress.setCurrentIterations(counter);
                trainingProgress.setCurrentSquaredError(error);
            }
            if (counter % trainingProgress.getSavePerIteration() == 0) {
                try {
                    save(trainingProgress.getTmpNetworksPath() + "/" + counter / trainingProgress.getSavePerIteration() + ".nnet", this, true);
                } catch (NNException ex) {
                    ex.printStackTrace();
                }
            }
        } while (counter < neuralNetworkParameter.getTrainingMaxIteration() && error > neuralNetworkParameter.getMinError());
        return new Date().getTime() - startTime;
    }

    public TestResult test(List<TrainingData> trainingDataList) throws TrainingDataNNException {
        if (trainingDataList.size() == 0) {
            throw new TrainingDataNNException("Test data size must not be zero");
        }
        Date start = new Date();
        float squaredError = 0;
        float numberOfIncorrect = 0;
        float diffBetweenAnsAndBest = 0;
        for (TrainingData trainingData : trainingDataList) {
            Activation.activate(this, trainingData);
            List<Float> outputActivation = getOutputActivation(trainingData);
            squaredError += TestResultUtil.getSquaredError(trainingData.getOutput(), outputActivation);
            numberOfIncorrect += TestResultUtil.isCorrect(trainingData.getOutput(), outputActivation) ? 0 : 1;
            diffBetweenAnsAndBest += TestResultUtil.getDiffBetweenAnsAndBest(trainingData.getOutput(), outputActivation);
        }
        TestResult testResult = new TestResult();
        testResult.setNumberOfData(trainingDataList.size());
        testResult.setSquaredError(squaredError);
        testResult.setPercentageOfIncorrect(numberOfIncorrect * 100 / trainingDataList.size());
        testResult.setDiffBetweenAnsAndBest(diffBetweenAnsAndBest);
        testResult.setNormalizedGeneralError(TestResultUtil.getNormalizedGeneralError(testResult));
        testResult.setDuration(new Date().getTime() - start.getTime());
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

    public static void save(String path, NeuralNetwork neuralNetwork, boolean newThread) throws NNException {
        if (newThread) {
            Thread thread = new Thread(null, () -> {
                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
                    out.writeObject(neuralNetwork);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }, "Load network from file system thread", 2000000);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException ignored) {}
        } else {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
                out.writeObject(neuralNetwork);
            } catch (IOException ex) {
                throw new NNException(ex.getMessage());
            }
        }
    }

    public static NeuralNetwork load(String url) throws NNException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(url))) {
            return (NeuralNetwork)in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            throw new NNException(ex.getMessage());
        }
    }

    public static void copyNetwork(NeuralNetwork srcNetwork, NeuralNetwork newNetwork) {
        if (srcNetwork == null) {
            newNetwork.setNeuralNetworkParameter(null);
            return;
        }
        newNetwork.setHiddenNeurons(srcNetwork.getHiddenNeurons());
        newNetwork.setInputNeurons(srcNetwork.getInputNeurons());
        newNetwork.setLayersSize(srcNetwork.getLayersSize());
        newNetwork.setNeuralNetworkParameter(srcNetwork.getNeuralNetworkParameter());
        newNetwork.setOutputNeurons(srcNetwork.getOutputNeurons());
        newNetwork.setTrainingDataList(srcNetwork.getTrainingDataList());
    }
}
