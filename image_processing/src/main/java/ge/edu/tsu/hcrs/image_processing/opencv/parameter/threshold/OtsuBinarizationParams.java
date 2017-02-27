package ge.edu.tsu.hcrs.image_processing.opencv.parameter.threshold;

import org.bytedeco.javacpp.opencv_imgproc;

public class OtsuBinarizationParams implements ThresholdParams {

    // thresh – threshold value.
    // არის 0 რადგან მაინც არ იყენებს, თვითონ თვლის კარგ მნიშვნელობას და იუენებს მას
    private double thresh = 0;

    // maxval – maximum value to use with the THRESH_BINARY and THRESH_BINARY_INV thresholding types.
    private double maxValue = 255;

    // type – thresholding type (see the details below).
    private int type = opencv_imgproc.THRESH_BINARY + opencv_imgproc.THRESH_OTSU;

    public double getThresh() {
        return thresh;
    }

    public void setThresh(double thresh) {
        this.thresh = thresh;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
