package ge.edu.tsu.hcrs.control_panel.service.neuralnetwork;

import ge.edu.tsu.hcrs.control_panel.model.network.CharSequence;
import ge.edu.tsu.hcrs.control_panel.model.network.NetworkInfo;
import ge.edu.tsu.hcrs.control_panel.model.network.NetworkResult;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizedData;

import java.util.List;

public interface NeuralNetworkService {

    void trainNeural(NetworkInfo networkInfo);

    NetworkResult getNetworkResult(NormalizedData normalizedData, String networkPath, CharSequence charSequence);

    float test(int width, int height, List<GroupedNormalizedData> groupedNormalizedDatum, String path, int networkId, CharSequence charSequence);
}
