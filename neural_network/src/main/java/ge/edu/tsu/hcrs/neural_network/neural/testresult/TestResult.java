package ge.edu.tsu.hcrs.neural_network.neural.testresult;

public class TestResult {

    private int numberOfData;

    private float squaredError;

    private float percentageOfCorrects;

    private float diffBetweenAnsAndBest;

    private float normalizedGeneralError;

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

    public int getNumberOfData() {
        return numberOfData;
    }

    public void setNumberOfData(int numberOfData) {
        this.numberOfData = numberOfData;
    }

    public float getNormalizedGeneralError() {
        return normalizedGeneralError;
    }

    public void setNormalizedGeneralError(float normalizedGeneralError) {
        this.normalizedGeneralError = normalizedGeneralError;
    }
}
