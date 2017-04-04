package ge.edu.tsu.hrs.control_panel.service.neuralnetwork;

import ge.edu.tsu.hrs.control_panel.model.exception.ControlPanelException;
import ge.edu.tsu.hrs.control_panel.model.network.NetworkInfo;
import ge.edu.tsu.hrs.control_panel.model.network.NetworkResult;
import ge.edu.tsu.hrs.control_panel.model.network.RecognitionInfo;
import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;

import java.awt.image.BufferedImage;
import java.util.List;

public interface NeuralNetworkService {

    void trainNeural(NetworkInfo networkInfo, List<GroupedNormalizedData> groupedNormalizedDatum, boolean saveInDatabase) throws ControlPanelException;

    NetworkResult getNetworkResult(BufferedImage image, int networkId);

    float testNeural(List<GroupedNormalizedData> groupedNormalizedDatum, int networkId) throws ControlPanelException;

    List<RecognitionInfo> recognizeText(List<BufferedImage> images, int networkId);
}
