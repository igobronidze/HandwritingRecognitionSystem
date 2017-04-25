package ge.edu.tsu.hrs.control_panel.server.caching;

import ge.edu.tsu.hrs.control_panel.model.network.CharSequence;
import ge.edu.tsu.hrs.control_panel.model.network.TrainingDataInfo;
import ge.edu.tsu.hrs.control_panel.server.processor.neuralnetwork.ProductionNetworkProcessor;
import ge.edu.tsu.hrs.neural_network.neural.network.NeuralNetwork;

public class CachedProductionNetwork {

    private static final ProductionNetworkProcessor productionNetworkProcessor = new ProductionNetworkProcessor();

    private static NeuralNetwork neuralNetwork;

    private static CharSequence charSequence;

    private static TrainingDataInfo trainingDataInfo;

    public static void loadData() {
        neuralNetwork = productionNetworkProcessor.getProductionNeuralNetwork();
        charSequence = productionNetworkProcessor.getProductionCharSequence();
        trainingDataInfo = productionNetworkProcessor.getProductionTrainingDataInfo();
    }

    public static NeuralNetwork getProductionNeuralNetwork() {
        if (neuralNetwork == null) {
            loadData();
        }
        return neuralNetwork;
    }

    public static CharSequence getProductionCharSequence() {
        if (charSequence == null) {
            loadData();
        }
        return charSequence;
    }

    public static TrainingDataInfo getProductionTrainingDataInfo() {
        if (trainingDataInfo == null) {
            loadData();
        }
        return trainingDataInfo;
    }
}
