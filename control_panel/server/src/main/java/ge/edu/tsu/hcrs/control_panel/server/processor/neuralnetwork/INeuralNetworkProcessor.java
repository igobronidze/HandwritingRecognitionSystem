package ge.edu.tsu.hcrs.control_panel.server.processor.neuralnetwork;

import ge.edu.tsu.hcrs.control_panel.model.network.CharSequence;
import ge.edu.tsu.hcrs.control_panel.model.network.NetworkInfo;
import ge.edu.tsu.hcrs.control_panel.model.network.NetworkResult;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizedData;

import java.util.List;

public interface INeuralNetworkProcessor {

    void trainNeural(NetworkInfo networkInfo);

    NetworkResult getNetworkResult(NormalizedData normalizedData, String networkPath, CharSequence charSequence);

    float test(int width, int height, List<String> generations, String path, int networkId, CharSequence charSequence);
}
