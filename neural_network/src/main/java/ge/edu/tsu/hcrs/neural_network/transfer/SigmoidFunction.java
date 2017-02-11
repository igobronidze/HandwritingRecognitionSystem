package ge.edu.tsu.hcrs.neural_network.transfer;

public class SigmoidFunction implements TransferFunction {

    @Override
    public float transfer(float value) {
        return (float)(1.0 / (1.0 + Math.exp(-1 * value)));
    }
}
