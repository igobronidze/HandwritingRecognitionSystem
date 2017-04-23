package ge.edu.tsu.hrs.control_panel.model.network;

import java.awt.image.BufferedImage;
import java.util.List;

public class RecognitionInfo {

    private String text;

    private BufferedImage cleanedImage;

    private List<BufferedImage> cutSymbolImages;

    private List<NetworkResult> networkResults;

    private long networkInfoGatheringDuration;

    private long cleanImageDuration;

    private long detectContoursDuration;

    private long inputDataGatheringDuration;

    private long activationDuration;

    private long extraDuration;

    public long getNetworkInfoGatheringDuration() {
        return networkInfoGatheringDuration;
    }

    public void setNetworkInfoGatheringDuration(long networkInfoGatheringDuration) {
        this.networkInfoGatheringDuration = networkInfoGatheringDuration;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getDetectContoursDuration() {
        return detectContoursDuration;
    }

    public void setDetectContoursDuration(long detectContoursDuration) {
        this.detectContoursDuration = detectContoursDuration;
    }

    public long getInputDataGatheringDuration() {
        return inputDataGatheringDuration;
    }

    public void setInputDataGatheringDuration(long inputDataGatheringDuration) {
        this.inputDataGatheringDuration = inputDataGatheringDuration;
    }

    public long getActivationDuration() {
        return activationDuration;
    }

    public void setActivationDuration(long activationDuration) {
        this.activationDuration = activationDuration;
    }

    public long getExtraDuration() {
        return extraDuration;
    }

    public void setExtraDuration(long extraDuration) {
        this.extraDuration = extraDuration;
    }

    public BufferedImage getCleanedImage() {
        return cleanedImage;
    }

    public void setCleanedImage(BufferedImage cleanedImage) {
        this.cleanedImage = cleanedImage;
    }

    public List<BufferedImage> getCutSymbolImages() {
        return cutSymbolImages;
    }

    public void setCutSymbolImages(List<BufferedImage> cutSymbolImages) {
        this.cutSymbolImages = cutSymbolImages;
    }

    public List<NetworkResult> getNetworkResults() {
        return networkResults;
    }

    public void setNetworkResults(List<NetworkResult> networkResults) {
        this.networkResults = networkResults;
    }

    public long getCleanImageDuration() {
        return cleanImageDuration;
    }

    public void setCleanImageDuration(long cleanImageDuration) {
        this.cleanImageDuration = cleanImageDuration;
    }
}
