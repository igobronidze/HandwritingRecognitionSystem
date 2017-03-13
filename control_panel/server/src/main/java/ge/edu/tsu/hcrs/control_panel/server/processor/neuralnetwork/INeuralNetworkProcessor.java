package ge.edu.tsu.hcrs.control_panel.server.processor.neuralnetwork;

import ge.edu.tsu.hcrs.control_panel.model.exception.ControlPanelException;
import ge.edu.tsu.hcrs.control_panel.model.network.CharSequence;
import ge.edu.tsu.hcrs.control_panel.model.network.NetworkInfo;
import ge.edu.tsu.hcrs.control_panel.model.network.NetworkResult;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizedData;

import java.util.List;

public interface INeuralNetworkProcessor {

    void trainNeural(NetworkInfo networkInfo) throws ControlPanelException;

    NetworkResult getNetworkResult(NormalizedData normalizedData, String networkPath, CharSequence charSequence);

    float testNeural(List<GroupedNormalizedData> groupedNormalizedDatum, int networkId) throws ControlPanelException;
}
