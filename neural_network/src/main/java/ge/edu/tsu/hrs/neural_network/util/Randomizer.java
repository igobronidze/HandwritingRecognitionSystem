package ge.edu.tsu.hrs.neural_network.util;

import ge.edu.tsu.hrs.neural_network.neural.network.Connection;
import ge.edu.tsu.hrs.neural_network.neural.network.NeuralNetwork;
import ge.edu.tsu.hrs.neural_network.neural.network.Neuron;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Randomizer {

    private static Random random = new Random();

    public static Neuron getRandomNeuron(NeuralNetwork neuralNetwork) {
        float bias = neuralNetwork.getNeuralNetworkParameter().getBiasMinValue() + (neuralNetwork.getNeuralNetworkParameter().getBiasMaxValue()
                - neuralNetwork.getNeuralNetworkParameter().getBiasMinValue()) * random.nextFloat();
        Neuron neuron = new Neuron();
        neuron.setBias(bias);
        return neuron;
    }

    public static Connection getRandomConnection(NeuralNetwork neuralNetwork, Neuron leftNeuron, Neuron rightNeuron) {
        float weight = neuralNetwork.getNeuralNetworkParameter().getWeightMinValue() + (neuralNetwork.getNeuralNetworkParameter().getWeightMaxValue()
                - neuralNetwork.getNeuralNetworkParameter().getWeightMinValue()) * random.nextFloat();
        Connection connection = new Connection();
        connection.setLeftNeuron(leftNeuron);
        connection.setRightNeuron(rightNeuron);
        connection.setWeight(weight);
        return connection;
    }

    public static List<Integer> getRandomIntegerList(int size) {
        List<Integer> randomList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            randomList.add(i);
        }
        Collections.shuffle(randomList);
        return randomList;
    }
}
