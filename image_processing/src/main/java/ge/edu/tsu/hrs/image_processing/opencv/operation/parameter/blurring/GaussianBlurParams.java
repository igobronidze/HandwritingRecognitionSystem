package ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.blurring;

import org.bytedeco.javacpp.opencv_core;

public class GaussianBlurParams implements BlurringParams {

    // ksize - Gaussian kernel size. ksize.width and ksize.height can differ but they
    // both must be positive and odd. Or, they can be zeroÃ¢Â€Â™s and then they are computed from sigma*.
    private int kSizeWidth = 5;

    private int kSizeHeight = 5;

    // sigmaX - Gaussian kernel standard deviation in X direction.
    private double sigmaX = 0.0;

    // sigmaY - Gaussian kernel standard deviation in Y direction; if sigmaY is zero, it is set to be equal to sigmaX,
    // if both sigmas are zeros, they are computed from ksize.width and ksize.height , respectively; to fully control
    // the result regardless of possible future modifications of all this semantics, it is recommended to specify all
    // of ksize, sigmaX, and sigmaY.
    private double sigmaY = 0.0;

    // borderType - pixel extrapolation method.
    private int borderType = opencv_core.BORDER_DEFAULT;

    public int getkSizeWidth() {
        return kSizeWidth;
    }

    public void setkSizeWidth(int kSizeWidth) {
        this.kSizeWidth = kSizeWidth;
    }

    public int getkSizeHeight() {
        return kSizeHeight;
    }

    public void setkSizeHeight(int kSizeHeight) {
        this.kSizeHeight = kSizeHeight;
    }

    public double getSigmaX() {
        return sigmaX;
    }

    public void setSigmaX(double sigmaX) {
        this.sigmaX = sigmaX;
    }

    public double getSigmaY() {
        return sigmaY;
    }

    public void setSigmaY(double sigmaY) {
        this.sigmaY = sigmaY;
    }

    public int getBorderType() {
        return borderType;
    }

    public void setBorderType(int borderType) {
        this.borderType = borderType;
    }
}
