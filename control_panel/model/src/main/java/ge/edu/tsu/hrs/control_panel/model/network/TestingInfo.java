package ge.edu.tsu.hrs.control_panel.model.network;

import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;

import java.io.Serializable;
import java.util.List;

public class TestingInfo implements Serializable {

    private int id;

    private int networkId;

    private int networkExtraId;

    private List<GroupedNormalizedData> groupedNormalizedDatum;

    private int numberOfTest;

    private float squaredError;

    private float percentageOfIncorrect;

    private float diffBetweenAnsAndBest;

    private float normalizedGeneralError;

    private long duration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNetworkId() {
        return networkId;
    }

    public void setNetworkId(int networkId) {
        this.networkId = networkId;
    }

    public int getNetworkExtraId() {
        return networkExtraId;
    }

    public void setNetworkExtraId(int networkExtraId) {
        this.networkExtraId = networkExtraId;
    }

    public List<GroupedNormalizedData> getGroupedNormalizedDatum() {
        return groupedNormalizedDatum;
    }

    public void setGroupedNormalizedDatum(List<GroupedNormalizedData> groupedNormalizedDatum) {
        this.groupedNormalizedDatum = groupedNormalizedDatum;
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

    public float getPercentageOfIncorrect() {
        return percentageOfIncorrect;
    }

    public void setPercentageOfIncorrect(float percentageOfIncorrect) {
        this.percentageOfIncorrect = percentageOfIncorrect;
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
