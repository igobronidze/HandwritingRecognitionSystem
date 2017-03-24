package ge.edu.tsu.hcrs.control_panel.service.neuralnetwork;

import ge.edu.tsu.hcrs.control_panel.model.exception.ControlPanelException;
import ge.edu.tsu.hcrs.control_panel.model.network.NetworkInfo;
import ge.edu.tsu.hcrs.control_panel.model.network.NetworkProcessorType;
import ge.edu.tsu.hcrs.control_panel.model.network.NetworkResult;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.server.processor.neuralnetwork.HCRSNeuralNetworkProcessor;
import ge.edu.tsu.hcrs.control_panel.server.processor.neuralnetwork.INeuralNetworkProcessor;

import java.awt.image.BufferedImage;
import java.util.List;

public class NeuralNetworkServiceImpl implements NeuralNetworkService {

    private INeuralNetworkProcessor iNeuralNetworkProcessor;

    public NeuralNetworkServiceImpl(NetworkProcessorType type) {
        switch (type) {
            case HCRS_NEURAL_NETWORK:
                iNeuralNetworkProcessor = new HCRSNeuralNetworkProcessor();
//            case NEUROPH_NEURAL_NETWORK:
//                iNeuralNetworkProcessor = new NeurophNeuralNetworkProcessor();
            default:
                iNeuralNetworkProcessor = new HCRSNeuralNetworkProcessor();
        }
    }

    @Override
    public void trainNeural(NetworkInfo networkInfo, boolean saveInDatabase) throws ControlPanelException {
        iNeuralNetworkProcessor.trainNeural(networkInfo, saveInDatabase);
    }

    @Override
    public NetworkResult getNetworkResult(BufferedImage image, int networkId) {
        return iNeuralNetworkProcessor.getNetworkResult(image, networkId);
    }

    @Override
    public float testNeural(List<GroupedNormalizedData> groupedNormalizedDatum, int networkId) throws ControlPanelException {
        return iNeuralNetworkProcessor.testNeural(groupedNormalizedDatum, networkId);
    }

    @Override
    public String recognizeText(BufferedImage image, int networkId) {
        return iNeuralNetworkProcessor.recognizeText(image, networkId);
    }
}
