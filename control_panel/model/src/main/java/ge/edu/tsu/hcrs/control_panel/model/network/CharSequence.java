package ge.edu.tsu.hcrs.control_panel.model.network;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CharSequence implements Serializable {

    private String charactersRegex;

    private transient Map<Character, Integer> charToIndexMap = new HashMap<>();

    private transient Map<Integer, Character> indexToCharMap = new HashMap<>();

    private transient int numberOfChars;

    public CharSequence() {
    }

    public CharSequence(String charactersRegex) {
        this.charactersRegex = charactersRegex;
    }

    public String getCharactersRegex() {
        return charactersRegex;
    }

    public void setCharactersRegex(String charactersRegex) {
        this.charactersRegex = charactersRegex;
    }

    public Map<Character, Integer> getCharToIndexMap() {
        return charToIndexMap;
    }

    public void setCharToIndexMap(Map<Character, Integer> charToIndexMap) {
        this.charToIndexMap = charToIndexMap;
    }

    public Map<Integer, Character> getIndexToCharMap() {
        return indexToCharMap;
    }

    public void setIndexToCharMap(Map<Integer, Character> indexToCharMap) {
        this.indexToCharMap = indexToCharMap;
    }

    public int getNumberOfChars() {
        return numberOfChars;
    }

    public void setNumberOfChars(int numberOfChars) {
        this.numberOfChars = numberOfChars;
    }
}
