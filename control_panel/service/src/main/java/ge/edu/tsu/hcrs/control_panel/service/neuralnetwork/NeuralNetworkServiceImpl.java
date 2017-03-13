package ge.edu.tsu.hcrs.control_panel.service.neuralnetwork;

import ge.edu.tsu.hcrs.control_panel.model.exception.ControlPanelException;
import ge.edu.tsu.hcrs.control_panel.model.network.*;
import ge.edu.tsu.hcrs.control_panel.model.network.CharSequence;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizedData;
import ge.edu.tsu.hcrs.control_panel.server.processor.neuralnetwork.HCRSNeuralNetworkProcessor;
import ge.edu.tsu.hcrs.control_panel.server.processor.neuralnetwork.INeuralNetworkProcessor;
import ge.edu.tsu.hcrs.control_panel.server.processor.neuralnetwork.NeurophNeuralNetworkProcessor;

import java.util.List;

public class NeuralNetworkServiceImpl implements NeuralNetworkService {

    private INeuralNetworkProcessor iNeuralNetworkProcessor;

    public NeuralNetworkServiceImpl(NetworkProcessorType type) {
        switch (type) {
            case HCRS_NEURAL_NETWORK:
                iNeuralNetworkProcessor = new HCRSNeuralNetworkProcessor();
            case NEUROPH_NEURAL_NETWORK:
                iNeuralNetworkProcessor = new NeurophNeuralNetworkProcessor();
            default:
                iNeuralNetworkProcessor = new HCRSNeuralNetworkProcessor();
        }
    }

    @Override
    public void trainNeural(NetworkInfo networkInfo) throws ControlPanelException {
        iNeuralNetworkProcessor.trainNeural(networkInfo);
    }

    @Override
    public NetworkResult getNetworkResult(NormalizedData normalizedData, String networkPath, CharSequence charSequence) {
        return iNeuralNetworkProcessor.getNetworkResult(normalizedData, networkPath, charSequence);
    }

    @Override
    public float testNeural(List<GroupedNormalizedData> groupedNormalizedDatum, int networkId) throws ControlPanelException {
        return iNeuralNetworkProcessor.testNeural(groupedNormalizedDatum, networkId);
    }
}
