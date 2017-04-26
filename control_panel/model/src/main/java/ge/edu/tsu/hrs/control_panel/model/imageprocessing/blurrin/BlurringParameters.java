package ge.edu.tsu.hrs.control_panel.model.imageprocessing.blurrin;

public class BlurringParameters {

    private BlurringType type;

    private int amount;

    private int kSize;

    private int diameter;

    private int sigmaColor;

    private int sigmaSpace;

    private int kSizeWidth;

    private int kSizeHeight;

    private double sigmaX;

    private double sigmaY;

    private int borderType;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BlurringType getType() {
        return type;
    }

    public void setType(BlurringType type) {
        this.type = type;
    }

    public int getkSize() {
        return kSize;
    }

    public void setkSize(int kSize) {
        this.kSize = kSize;
    }

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
