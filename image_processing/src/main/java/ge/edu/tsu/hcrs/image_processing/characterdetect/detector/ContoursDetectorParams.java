package ge.edu.tsu.hcrs.image_processing.characterdetect.detector;

public class ContoursDetectorParams {

    private int checkedRGBMaxValue = -2;

    public ContoursDetectorParams() {
    }

    public ContoursDetectorParams(int checkedRGBMaxValue) {
        this.checkedRGBMaxValue = checkedRGBMaxValue;
    }

    public int getCheckedRGBMaxValue() {
        return checkedRGBMaxValue;
    }

    public void setCheckedRGBMaxValue(int checkedRGBMaxValue) {
        this.checkedRGBMaxValue = checkedRGBMaxValue;
    }
}
