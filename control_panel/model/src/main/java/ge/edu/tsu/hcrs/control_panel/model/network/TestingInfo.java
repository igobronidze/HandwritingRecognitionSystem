package ge.edu.tsu.hcrs.control_panel.model.network;

import java.io.Serializable;

public class TestingInfo implements Serializable {

    private int networkId;

    private String generation;

    private int numberOfTest;

    private float squaredError;

    private float percentageOfCorrects;

    private float diffBetweenAnsAndBest;

    private float normalizedGeneralError;

    public int getNetworkId() {
        return networkId;
    }

    public void setNetworkId(int networkId) {
        this.networkId = networkId;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public int getNumberOfTest() {
        return numberOfTest;
    }

    public void setNumberOfTest(int numberOfTest) {
        this.numberOfTest = numberOfTest;
    }

    public float getSquaredError() {
        return squaredError;
    }

    public void setSquaredError(float squaredError) {
        this.squaredError = squaredError;
    }

    public float getPercentageOfCorrects() {
        return percentageOfCorrects;
    }

    public void setPercentageOfCorrects(float percentageOfCorrects) {
        this.percentageOfCorrects = percentageOfCorrects;
    }

    public float getDiffBetweenAnsAndBest() {
        return diffBetweenAnsAndBest;
    }

    public void setDiffBetweenAnsAndBest(float diffBetweenAnsAndBest) {
        this.diffBetweenAnsAndBest = diffBetweenAnsAndBest;
    }

    public float getNormalizedGeneralError() {
        return normalizedGeneralError;
    }

    public void setNormalizedGeneralError(float normalizedGeneralError) {
        this.normalizedGeneralError = normalizedGeneralError;
    }
}
