package ge.edu.tsu.hcrs.control_panel.service.neuralnetwork;

import ge.edu.tsu.hcrs.control_panel.model.network.*;
import ge.edu.tsu.hcrs.control_panel.model.network.CharSequence;
import ge.edu.tsu.hcrs.control_panel.server.processor.neuralnetwork.HCRSNeuralNetworkProcessor;
import ge.edu.tsu.hcrs.control_panel.server.processor.neuralnetwork.INeuralNetworkProcessor;
import ge.edu.tsu.hcrs.control_panel.server.processor.neuralnetwork.NeurophNeuralNetworkProcessor;

public class NeuralNetworkServiceImpl implements NeuralNetworkService {

    private INeuralNetworkProcessor iNeuralNetworkProcessor;

    public NeuralNetworkServiceImpl(NetworkProcessorType type) {
        switch (type) {
            case MY_NEURAL_NETWORK:
                iNeuralNetworkProcessor = new HCRSNeuralNetworkProcessor();
            case NEUROPH_NEURAL_NETWORK:
                iNeuralNetworkProcessor = new NeurophNeuralNetworkProcessor();
            default:
                iNeuralNetworkProcessor = new HCRSNeuralNetworkProcessor();
        }
    }

    @Override
    public void trainNeural(NetworkInfo networkInfo) {
        iNeuralNetworkProcessor.trainNeural(networkInfo);
    }

    @Override
    public NetworkResult getNetworkResult(NormalizedData normalizedData, String networkPath, CharSequence charSequence) {
        return iNeuralNetworkProcessor.getNetworkResult(normalizedData, networkPath, charSequence);
    }

    @Override
    public float test(int width, int height, String generation, String path, int networkId, CharSequence charSequence) {
        return iNeuralNetworkProcessor.test(width, height, generation, path, networkId, charSequence);
    }
}
