package ge.edu.tsu.hrs.control_panel.model.network;

public class RecognitionInfo {

    private String text;

    private long networkInfoGatheringDuration;

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
}
