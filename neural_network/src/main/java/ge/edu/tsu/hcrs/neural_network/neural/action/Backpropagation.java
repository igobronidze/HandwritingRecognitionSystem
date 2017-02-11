package ge.edu.tsu.hcrs.neural_network.neural.action;

import ge.edu.tsu.hcrs.neural_network.neural.network.Connection;
import ge.edu.tsu.hcrs.neural_network.neural.network.NeuralNetwork;
import ge.edu.tsu.hcrs.neural_network.neural.network.Neuron;
import ge.edu.tsu.hcrs.neural_network.neural.network.TrainingData;

import java.util.List;

public class Backpropagation {

    public static void backpropagation(NeuralNetwork neuralNetwork, TrainingData trainingData) {
        List<Neuron> outputNeurons = neuralNetwork.getOutputNeurons();
        List<Neuron> hiddenNeurons = neuralNetwork.getHiddenNeurons();
        List<Float> output = trainingData.getOutput();
        for (int i = 0; i < outputNeurons.size(); i++) {
            Neuron rightNeuron = outputNeurons.get(i);
            float rightNeuronRealOutput = output.get(i);
            float rightNeuronOutput = rightNeuron.getActivationValue();
            float delta = -1 * (rightNeuronRealOutput - rightNeuronOutput) * rightNeuronOutput * (1 - rightNeuronOutput);
            rightNeuron.setDelta(delta);
            for (int j = 0; j < rightNeuron.getInConnections().size(); j++) {
                Connection connection = rightNeuron.getInConnections().get(j);
                Neuron leftNeuron = connection.getLeftNeuron();
                float leftNeuronOutput = leftNeuron.getActivationValue();
                float partialDerivative = delta * leftNeuronOutput;
                connection.setWeight(connection.getWeight() - neuralNetwork.getNeuralNetworkParameter().getLearningRate() * partialDerivative);
            }
        }
        for (int i = hiddenNeurons.size() - 1; i >= 0; i--) {
            Neuron rightNeuron = hiddenNeurons.get(i);
            float deltasSum = 0.0f;
            for (int j = 0; j < rightNeuron.getOutConnections().size(); j++) {
                Connection connection = rightNeuron.getOutConnections().get(j);
                deltasSum += connection.getRightNeuron().getDelta() * connection.getWeight();
            }
            float rightNeuronOutput = rightNeuron.getActivationValue();
            float delta = deltasSum * rightNeuronOutput * (1 - rightNeuronOutput);
            rightNeuron.setDelta(delta);
            for (int j = 0; j < rightNeuron.getInConnections().size(); j++) {
                Connection connection = rightNeuron.getInConnections().get(j);
                float partialDerivative = delta * connection.getLeftNeuron().getActivationValue();
                connection.setWeight(connection.getWeight() - neuralNetwork.getNeuralNetworkParameter().getLearningRate() * partialDerivative);
            }
        }
    }
}
