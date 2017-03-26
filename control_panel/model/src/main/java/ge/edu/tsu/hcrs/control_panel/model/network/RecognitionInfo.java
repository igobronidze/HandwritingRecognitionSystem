package ge.edu.tsu.hcrs.control_panel.model.network;

import java.util.ArrayList;
import java.util.List;

public class RecognitionInfo {

    private List<String> texts = new ArrayList<>();

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

    public List<String> getTexts() {
        return texts;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
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
