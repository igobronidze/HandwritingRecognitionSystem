package ge.edu.tsu.hrs.control_panel.model.imageprocessing.blurrin;

public class BlurringParameters {

    private BlurringType type;

    private int amount;

    private Integer kSize;

    private Integer diameter;

    private Integer sigmaColor;

    private Integer sigmaSpace;

    private Integer kSizeWidth;

    private Integer kSizeHeight;

    private Double sigmaX;

    private Double sigmaY;

    private Integer borderType;

    public BlurringType getType() {
        return type;
    }

    public void setType(BlurringType type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Integer getkSize() {
        return kSize;
    }

    public void setkSize(Integer kSize) {
        this.kSize = kSize;
    }

    public Integer getDiameter() {
        return diameter;
    }

    public void setDiameter(Integer diameter) {
        this.diameter = diameter;
    }

    public Integer getSigmaColor() {
        return sigmaColor;
    }

    public void setSigmaColor(Integer sigmaColor) {
        this.sigmaColor = sigmaColor;
    }

    public Integer getSigmaSpace() {
        return sigmaSpace;
    }

    public void setSigmaSpace(Integer sigmaSpace) {
        this.sigmaSpace = sigmaSpace;
    }

    public Integer getkSizeWidth() {
        return kSizeWidth;
    }

    public void setkSizeWidth(Integer kSizeWidth) {
        this.kSizeWidth = kSizeWidth;
    }

    public Integer getkSizeHeight() {
        return kSizeHeight;
    }

    public void setkSizeHeight(Integer kSizeHeight) {
        this.kSizeHeight = kSizeHeight;
    }

    public Double getSigmaX() {
        return sigmaX;
    }

    public void setSigmaX(Double sigmaX) {
        this.sigmaX = sigmaX;
    }

    public Double getSigmaY() {
        return sigmaY;
    }

    public void setSigmaY(Double sigmaY) {
        this.sigmaY = sigmaY;
    }

    public Integer getBorderType() {
        return borderType;
    }

    public void setBorderType(Integer borderType) {
        this.borderType = borderType;
    }
}
