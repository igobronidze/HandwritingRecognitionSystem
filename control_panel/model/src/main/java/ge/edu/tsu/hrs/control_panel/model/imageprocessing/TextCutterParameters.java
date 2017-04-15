package ge.edu.tsu.hrs.control_panel.model.imageprocessing;

public class TextCutterParameters {

    private int checkedRGBMaxValue;

    private int checkNeighborRGBMaxValue;

    private int percentageOfSameForJoining;

    private boolean doubleQuoteAsTwoChar;

    private boolean useJoiningFunctional;

    private int imageWidth;

    private int imageHeight;

    public int getCheckedRGBMaxValue() {
        return checkedRGBMaxValue;
    }

    public void setCheckedRGBMaxValue(int checkedRGBMaxValue) {
        this.checkedRGBMaxValue = checkedRGBMaxValue;
    }

    public int getCheckNeighborRGBMaxValue() {
        return checkNeighborRGBMaxValue;
    }

    public void setCheckNeighborRGBMaxValue(int checkNeighborRGBMaxValue) {
        this.checkNeighborRGBMaxValue = checkNeighborRGBMaxValue;
    }

    public boolean isUseJoiningFunctional() {
        return useJoiningFunctional;
    }

    public void setUseJoiningFunctional(boolean useJoiningFunctional) {
        this.useJoiningFunctional = useJoiningFunctional;
    }

    public int getPercentageOfSameForJoining() {
        return percentageOfSameForJoining;
    }

    public void setPercentageOfSameForJoining(int percentageOfSameForJoining) {
        this.percentageOfSameForJoining = percentageOfSameForJoining;
    }

    public boolean isDoubleQuoteAsTwoChar() {
        return doubleQuoteAsTwoChar;
    }

    public void setDoubleQuoteAsTwoChar(boolean doubleQuoteAsTwoChar) {
        this.doubleQuoteAsTwoChar = doubleQuoteAsTwoChar;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }
}
