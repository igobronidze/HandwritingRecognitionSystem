package ge.edu.tsu.hrs.control_panel.server.processor.neuralnetwork;

import ge.edu.tsu.hrs.control_panel.model.exception.ControlPanelException;
import ge.edu.tsu.hrs.control_panel.model.network.NetworkInfo;
import ge.edu.tsu.hrs.control_panel.model.network.NetworkResult;
import ge.edu.tsu.hrs.control_panel.model.network.RecognitionInfo;
import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;

import java.awt.image.BufferedImage;
import java.util.List;

public interface INeuralNetworkProcessor {

    void trainNeural(NetworkInfo networkInfo, List<GroupedNormalizedData> groupedNormalizedDatum, boolean saveInDatabase) throws ControlPanelException;

    NetworkResult getNetworkResult(BufferedImage image, int networkId, int networkExtraId);

    float testNeural(List<GroupedNormalizedData> groupedNormalizedDatum, int networkId, int networkExtraId) throws ControlPanelException;

    List<RecognitionInfo> recognizeText(List<BufferedImage> images, Integer networkId, int networkExtraId, boolean analyseMode);
}
