package ge.edu.tsu.hcrs.control_panel.model.network;

import java.io.Serializable;

public class CharSequence implements Serializable {

    private char firstSymbol;

    private char lastSymbol;

    private int numberOfChars;

    private int firstCharASCI;

    public CharSequence() {
    }

    public CharSequence(char firstSymbol, char lastSymbol) {
        this.firstSymbol = firstSymbol;
        this.lastSymbol = lastSymbol;
        this.numberOfChars = lastSymbol - firstSymbol + 1;
        this.firstCharASCI = (int)firstSymbol;
    }

    public int getNumberOfChars() {
        return numberOfChars;
    }

    public void setNumberOfChars(int numberOfChars) {
        this.numberOfChars = numberOfChars;
    }

    public int getFirstCharASCI() {
        return firstCharASCI;
    }

    public void setFirstCharASCI(int firstCharASCI) {
        this.firstCharASCI = firstCharASCI;
    }

    public char getFirstSymbol() {
        return firstSymbol;
    }

    public void setFirstSymbol(char firstSymbol) {
        this.firstSymbol = firstSymbol;
    }

    public char getLastSymbol() {
        return lastSymbol;
    }

    public void setLastSymbol(char lastSymbol) {
        this.lastSymbol = lastSymbol;
    }
}
