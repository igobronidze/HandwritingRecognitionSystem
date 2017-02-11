package ge.edu.tsu.hcrs.control_panel.service.neuralnetwork;

import ge.edu.tsu.hcrs.control_panel.model.network.NetworkResult;
import ge.edu.tsu.hcrs.control_panel.model.network.NormalizedData;

public interface NeuralNetworkService {

    void trainNeural(int width, int height, String generation);

    NetworkResult getNetworkResult(NormalizedData normalizedData, String networkPath);

    float test(int width, int height, String generation, String path, int networkId);
}
