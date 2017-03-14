package ge.edu.tsu.hcrs.control_panel.model.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NetworkResult implements Serializable {

    private char answer;

    private List<Float> outputActivation = new ArrayList<>();

    private CharSequence charSequence;

    public NetworkResult() {
    }

    public char getAnswer() {
        return answer;
    }

    public void setAnswer(char answer) {
        this.answer = answer;
    }

    public List<Float> getOutputActivation() {
        return outputActivation;
    }

    public void setOutputActivation(List<Float> outputActivation) {
        this.outputActivation = outputActivation;
    }

    public CharSequence getCharSequence() {
        return charSequence;
    }

    public void setCharSequence(CharSequence charSequence) {
        this.charSequence = charSequence;
    }
}
