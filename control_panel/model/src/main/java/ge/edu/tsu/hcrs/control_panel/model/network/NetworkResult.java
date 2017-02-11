package ge.edu.tsu.hcrs.control_panel.model.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NetworkResult implements Serializable {

    private char answer;

    private List<Float> outputActivation = new ArrayList<>();

    public NetworkResult() {
    }

    public NetworkResult(List<Float> outputActivation, char answer) {
        this.outputActivation = outputActivation;
        this.answer = answer;
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
}
