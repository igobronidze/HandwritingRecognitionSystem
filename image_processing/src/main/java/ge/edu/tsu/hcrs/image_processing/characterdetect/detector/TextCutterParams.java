package ge.edu.tsu.hcrs.image_processing.characterdetect.detector;

public class TextCutterParams {

    private int checkedRGBMaxValue = -2;

    private int checkNeighborRGBMaxValue = -5777216;

    private boolean doubleQuoteAsTwoChar = true;

    private boolean useJoiningFunctional = true;

    private boolean saveAnyway = true;

    private int numberOfSameForJoining = 2;

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

    public boolean isDoubleQuoteAsTwoChar() {
        return doubleQuoteAsTwoChar;
    }

    public void setDoubleQuoteAsTwoChar(boolean doubleQuoteAsTwoChar) {
        this.doubleQuoteAsTwoChar = doubleQuoteAsTwoChar;
    }

    public boolean isUseJoiningFunctional() {
        return useJoiningFunctional;
    }

    public void setUseJoiningFunctional(boolean useJoiningFunctional) {
        this.useJoiningFunctional = useJoiningFunctional;
    }

    public boolean isSaveAnyway() {
        return saveAnyway;
    }

    public void setSaveAnyway(boolean saveAnyway) {
        this.saveAnyway = saveAnyway;
    }

    public int getNumberOfSameForJoining() {
        return numberOfSameForJoining;
    }

    public void setNumberOfSameForJoining(int numberOfSameForJoining) {
        this.numberOfSameForJoining = numberOfSameForJoining;
    }
}
