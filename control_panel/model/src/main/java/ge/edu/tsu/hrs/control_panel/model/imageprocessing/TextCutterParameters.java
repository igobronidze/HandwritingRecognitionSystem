package ge.edu.tsu.hrs.control_panel.model.imageprocessing;

public class TextCutterParameters {

    private int checkedRGBMaxValue;

    private int checkNeighborRGBMaxValue;

    private int percentageOfSameForJoining;

    private boolean doubleQuoteAsTwoChar;

    private boolean useJoiningFunctional;

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
}
