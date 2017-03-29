package ge.edu.tsu.hrs.image_processing.opencv.parameter.blurring;

public class BilateralFilterParams implements BlurringParams {

    //diameter - Diameter of each pixel neighborhood that is used during filtering. If it is non-positive, it is computed from sigmaSpace.
    private int diameter = 9;

    // sigmaColor - Filter sigma in the color space. A larger value of the parameter means that farther colors within the pixel neighborhood
    // will be mixed together, resulting in larger areas of semi-equal color.
    private int sigmaColor = 75;

    // sigmaSpace - Filter sigma in the coordinate space. A larger value of the parameter means that farther pixels will influence each
    // other as long as their colors are close enough. When d>0 , it specifies the neighborhood size regardless of
    // sigmaSpace . Otherwise, d is proportional to sigmaSpace.
    private int sigmaSpace = 75;

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public int getSigmaColor() {
        return sigmaColor;
    }

    public void setSigmaColor(int sigmaColor) {
        this.sigmaColor = sigmaColor;
    }

    public int getSigmaSpace() {
        return sigmaSpace;
    }

    public void setSigmaSpace(int sigmaSpace) {
        this.sigmaSpace = sigmaSpace;
    }
}
