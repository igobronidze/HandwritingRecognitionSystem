package ge.edu.tsu.hcrs.control_panel.service.neuralnetwork;

import ge.edu.tsu.hcrs.control_panel.model.network.NetworkResult;
import ge.edu.tsu.hcrs.control_panel.model.network.NormalizedData;
import ge.edu.tsu.hcrs.control_panel.server.manager.neuralnetwork.MyNeuralNetworkManager;
import ge.edu.tsu.hcrs.control_panel.server.manager.neuralnetwork.NeuralNetworkManager;
import ge.edu.tsu.hcrs.control_panel.server.manager.neuralnetwork.NeurophNeuralNetworkManager;

public class NeuralNetworkServiceImpl implements NeuralNetworkService {

    private NeuralNetworkManager neuralNetworkManager;

    public NeuralNetworkServiceImpl(NeuralNetworkManagerType type) {
        switch (type) {
            case MY_NEURAL_NETWORK:
                neuralNetworkManager = new MyNeuralNetworkManager();
            case NEUROPH_NEURAL_NETWORK:
                neuralNetworkManager = new NeurophNeuralNetworkManager();
            default:
                neuralNetworkManager = new MyNeuralNetworkManager();
        }
    }

    @Override
    public void trainNeural(int width, int height, String generation) {
        neuralNetworkManager.trainNeural(width, height, generation);
    }

    @Override
    public NetworkResult getNetworkResult(NormalizedData normalizedData, String networkPath) {
        return neuralNetworkManager.getNetworkResult(normalizedData, networkPath);
    }

    @Override
    public float test(int width, int height, String generation, String path, int networkId) {
        return neuralNetworkManager.test(width, height, generation, path, networkId);
    }
}
