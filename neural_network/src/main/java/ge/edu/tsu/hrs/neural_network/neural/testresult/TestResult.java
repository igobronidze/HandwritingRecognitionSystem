package ge.edu.tsu.hrs.neural_network.neural.testresult;

public class TestResult {

    private int numberOfData;

    private float squaredError;

    private float percentageOfIncorrect;

    private float diffBetweenAnsAndBest;

    private float normalizedGeneralError;

    private long duration;

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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
