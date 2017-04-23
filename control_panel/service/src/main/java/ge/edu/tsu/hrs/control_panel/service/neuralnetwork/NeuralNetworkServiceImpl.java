package ge.edu.tsu.hrs.control_panel.service.neuralnetwork;

import ge.edu.tsu.hrs.control_panel.model.exception.ControlPanelException;
import ge.edu.tsu.hrs.control_panel.model.network.NetworkInfo;
import ge.edu.tsu.hrs.control_panel.model.network.NetworkProcessorType;
import ge.edu.tsu.hrs.control_panel.model.network.NetworkResult;
import ge.edu.tsu.hrs.control_panel.model.network.RecognitionInfo;
import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hrs.control_panel.server.processor.neuralnetwork.HRSNeuralNetworkProcessor;
import ge.edu.tsu.hrs.control_panel.server.processor.neuralnetwork.INeuralNetworkProcessor;

import java.awt.image.BufferedImage;
import java.util.List;

public class NeuralNetworkServiceImpl implements NeuralNetworkService {

    private INeuralNetworkProcessor iNeuralNetworkProcessor;

    public NeuralNetworkServiceImpl(NetworkProcessorType type) {
        switch (type) {
            case HRS_NEURAL_NETWORK:
                iNeuralNetworkProcessor = new HRSNeuralNetworkProcessor();
//            case NEUROPH_NEURAL_NETWORK:
//                iNeuralNetworkProcessor = new NeurophNeuralNetworkProcessor();
            default:
                iNeuralNetworkProcessor = new HRSNeuralNetworkProcessor();
        }
    }

    @Override
    public void trainNeural(NetworkInfo networkInfo, List<GroupedNormalizedData> groupedNormalizedDatum, boolean saveInDatabase) throws ControlPanelException {
        iNeuralNetworkProcessor.trainNeural(networkInfo, groupedNormalizedDatum, saveInDatabase);
    }

    @Override
    public NetworkResult getNetworkResult(BufferedImage image, int networkId, int networkExtraId) {
        return iNeuralNetworkProcessor.getNetworkResult(image, networkId, networkExtraId);
    }

    @Override
    public float testNeural(List<GroupedNormalizedData> groupedNormalizedDatum, int networkId, int netwrkExtraId) throws ControlPanelException {
        return iNeuralNetworkProcessor.testNeural(groupedNormalizedDatum, networkId, netwrkExtraId);
    }

    @Override
    public List<RecognitionInfo> recognizeText(List<BufferedImage> images, int networkId, int networkExtraId, boolean analyseMode) {
        return iNeuralNetworkProcessor.recognizeText(images, networkId, networkExtraId, analyseMode);
    }
}
