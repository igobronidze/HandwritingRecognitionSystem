package ge.edu.tsu.hrs.control_panel.model.imageprocessing.threshold;

public class ThresholdParameters {

    private ThresholdType thresholdMethodType;

    private Integer thresh;

    private Integer maxValue;

    private Integer type;

    private Integer adaptiveMethod;

    private Integer thresholdType;

    private Integer blockSize;

    private Integer c;

    public ThresholdType getThresholdMethodType() {
        return thresholdMethodType;
    }

    public void setThresholdMethodType(ThresholdType thresholdMethodType) {
        this.thresholdMethodType = thresholdMethodType;
    }

    public Integer getThresh() {
        return thresh;
    }

    public void setThresh(Integer thresh) {
        this.thresh = thresh;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAdaptiveMethod() {
        return adaptiveMethod;
    }

    public void setAdaptiveMethod(Integer adaptiveMethod) {
        this.adaptiveMethod = adaptiveMethod;
    }

    public Integer getThresholdType() {
        return thresholdType;
    }

    public void setThresholdType(Integer thresholdType) {
        this.thresholdType = thresholdType;
    }

    public Integer getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(Integer blockSize) {
        this.blockSize = blockSize;
    }

    public Integer getC() {
        return c;
    }

    public void setC(Integer c) {
        this.c = c;
    }
}
