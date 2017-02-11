package ge.edu.tsu.hcrs.neural_network.neural.network;

import ge.edu.tsu.hcrs.neural_network.transfer.*;

import java.io.Serializable;

public class NeuralNetworkParameter implements Serializable {

    public static final long serialVersionUID = 854854845876L;

    private float weightMinValue = -0.5f;

    private float weightMaxValue = 0.5f;

    private float biasMinValue = -0.5f;

    private float biasMaxValue = 0.5f;

    private TransferFunction transferFunction = new SigmoidFunction();

    private TransferFunctionType transferFunctionType = TransferFunctionType.SIGMOID;

    private float learningRate = 0.2f;

    private float minError = 0.005f;

    private long trainingMaxIteration = 10_000_000L;

    private long numberOfTrainingDataInOneIteration = 10_000_000L;

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

    public TransferFunctionType getTransferFunctionType() {
        return transferFunctionType;
    }

    public void setTransferFunctionType(TransferFunctionType transferFunctionType) {
        this.transferFunctionType = transferFunctionType;
        switch (transferFunctionType) {
            case UNIT_STEP:
                transferFunction = new UnitStepFunction();
            case SIGN:
                transferFunction = new SigmoidFunction();
            case LINEAR:
                transferFunction = new LinearFunction();
            case PIECE_WISE_LINEAR:
                transferFunction = new PieceWiseLinearFunction();
            case SIGMOID:
                transferFunction = new SigmoidFunction();
            case HYPERBOLIC_TANGENT:
                transferFunction = new HyperbolicTangentFunction();
            default:
                transferFunction = new SigmoidFunction();
        }
    }
}
