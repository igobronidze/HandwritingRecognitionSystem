package ge.edu.tsu.hcrs.control_panel.model.network;

import java.io.Serializable;
import java.util.List;

public class NetworkInfo implements Serializable {

    private int id;

    private int width;

    private int height;

    private List<String> generations;

    private int numberOfData;

    private long trainingDuration;

    private float weightMinValue;

    private float weightMaxValue;

    private float biasMinValue;

    private float biasMaxValue;

    private TransferFunction transferFunction;

    private float learningRate;

    private float minError;

    private long trainingMaxIteration;

    private long numberOfTrainingDataInOneIteration;

    private List<TestingInfo> testingInfoList;

    private CharSequence charSequence;

    private List<Integer> hiddenLayer;

    private NetworkProcessorType networkProcessorType;

    private String networkMetaInfo;

    private String description;

    private NetworkTrainingStatus trainingStatus;

    private float currentSquaredError;

    private long currentIterations;

    private long currentDuration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<String> getGenerations() {
        return generations;
    }

    public void setGenerations(List<String> generations) {
        this.generations = generations;
    }

    public int getNumberOfData() {
        return numberOfData;
    }

    public void setNumberOfData(int numberOfData) {
        this.numberOfData = numberOfData;
    }

    public long getTrainingDuration() {
        return trainingDuration;
    }

    public void setTrainingDuration(long trainingDuration) {
        this.trainingDuration = trainingDuration;
    }

    public float getWeightMinValue() {
        return weightMinValue;
    }

    public void setWeightMinValue(float weightMinValue) {
        this.weightMinValue = weightMinValue;
    }

    public float getWeightMaxValue() {
        return weightMaxValue;
    }

    public void setWeightMaxValue(float weightMaxValue) {
        this.weightMaxValue = weightMaxValue;
    }

    public float getBiasMinValue() {
        return biasMinValue;
    }

    public void setBiasMinValue(float biasMinValue) {
        this.biasMinValue = biasMinValue;
    }

    public float getBiasMaxValue() {
        return biasMaxValue;
    }

    public void setBiasMaxValue(float biasMaxValue) {
        this.biasMaxValue = biasMaxValue;
    }

    public TransferFunction getTransferFunction() {
        return transferFunction;
    }

    public void setTransferFunction(TransferFunction transferFunction) {
        this.transferFunction = transferFunction;
    }

    public float getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(float learningRate) {
        this.learningRate = learningRate;
    }

    public float getMinError() {
        return minError;
    }

    public void setMinError(float minError) {
        this.minError = minError;
    }

    public long getTrainingMaxIteration() {
        return trainingMaxIteration;
    }

    public void setTrainingMaxIteration(long trainingMaxIteration) {
        this.trainingMaxIteration = trainingMaxIteration;
    }

    public long getNumberOfTrainingDataInOneIteration() {
        return numberOfTrainingDataInOneIteration;
    }

    public void setNumberOfTrainingDataInOneIteration(long numberOfTrainingDataInOneIteration) {
        this.numberOfTrainingDataInOneIteration = numberOfTrainingDataInOneIteration;
    }

    public List<TestingInfo> getTestingInfoList() {
        return testingInfoList;
    }

    public void setTestingInfoList(List<TestingInfo> testingInfoList) {
        this.testingInfoList = testingInfoList;
    }

    public CharSequence getCharSequence() {
        return charSequence;
    }

    public void setCharSequence(CharSequence charSequence) {
        this.charSequence = charSequence;
    }

    public List<Integer> getHiddenLayer() {
        return hiddenLayer;
    }

    public void setHiddenLayer(List<Integer> hiddenLayer) {
        this.hiddenLayer = hiddenLayer;
    }

    public NetworkProcessorType getNetworkProcessorType() {
        return networkProcessorType;
    }

    public void setNetworkProcessorType(NetworkProcessorType networkProcessorType) {
        this.networkProcessorType = networkProcessorType;
    }

    public String getNetworkMetaInfo() {
        return networkMetaInfo;
    }

    public void setNetworkMetaInfo(String networkMetaInfo) {
        this.networkMetaInfo = networkMetaInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public NetworkTrainingStatus getTrainingStatus() {
        return trainingStatus;
    }

    public void setTrainingStatus(NetworkTrainingStatus trainingStatus) {
        this.trainingStatus = trainingStatus;
    }

    public float getCurrentSquaredError() {
        return currentSquaredError;
    }

    public void setCurrentSquaredError(float currentSquaredError) {
        this.currentSquaredError = currentSquaredError;
    }

    public long getCurrentIterations() {
        return currentIterations;
    }

    public void setCurrentIterations(long currentIterations) {
        this.currentIterations = currentIterations;
    }

    public long getCurrentDuration() {
        return currentDuration;
    }

    public void setCurrentDuration(long currentDuration) {
        this.currentDuration = currentDuration;
    }
}
