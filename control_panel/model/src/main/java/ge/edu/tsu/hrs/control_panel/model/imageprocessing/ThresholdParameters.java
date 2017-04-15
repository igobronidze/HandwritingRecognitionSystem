package ge.edu.tsu.hrs.control_panel.model.imageprocessing;

public class ThresholdParameters {

    private ThresholdType thresholdMethodType;

    private int thresh;

    private int maxValue;

    private int type;

    private int adaptiveMethod;

    private int thresholdType;

    private int blockSize;

    private int c;

    public ThresholdType getThresholdMethodType() {
        return thresholdMethodType;
    }

    public void setThresholdMethodType(ThresholdType thresholdMethodType) {
        this.thresholdMethodType = thresholdMethodType;
    }

    public int getThresh() {
        return thresh;
    }

    public void setThresh(int thresh) {
        this.thresh = thresh;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAdaptiveMethod() {
        return adaptiveMethod;
    }

    public void setAdaptiveMethod(int adaptiveMethod) {
        this.adaptiveMethod = adaptiveMethod;
    }

    public int getThresholdType() {
        return thresholdType;
    }

    public void setThresholdType(int thresholdType) {
        this.thresholdType = thresholdType;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }
}
