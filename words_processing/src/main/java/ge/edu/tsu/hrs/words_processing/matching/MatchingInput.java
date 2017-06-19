package ge.edu.tsu.hrs.words_processing.matching;

import java.util.List;
import java.util.Map;

public class MatchingInput {

    private String exemp;

    private List<String> texts;

    private float insertRate;

    private float deleteRate;

    private float changeRate;

    private List<Map<Character, Float>> changePossibilities;

    public String getExemp() {
        return exemp;
    }

    public void setExemp(String exemp) {
        this.exemp = exemp;
    }

    public List<String> getTexts() {
        return texts;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
    }

    public float getInsertRate() {
        return insertRate;
    }

    public void setInsertRate(float insertRate) {
        this.insertRate = insertRate;
    }

    public float getDeleteRate() {
        return deleteRate;
    }

    public void setDeleteRate(float deleteRate) {
        this.deleteRate = deleteRate;
    }

    public float getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(float changeRate) {
        this.changeRate = changeRate;
    }

    public List<Map<Character, Float>> getChangePossibilities() {
        return changePossibilities;
    }

    public void setChangePossibilities(List<Map<Character, Float>> changePossibilities) {
        this.changePossibilities = changePossibilities;
    }
}
