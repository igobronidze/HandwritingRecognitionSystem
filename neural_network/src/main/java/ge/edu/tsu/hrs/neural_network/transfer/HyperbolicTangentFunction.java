package ge.edu.tsu.hrs.neural_network.transfer;

public class HyperbolicTangentFunction implements TransferFunction {

    @Override
    public float transfer(float value) {
        if ((float)(Math.exp(value) + Math.exp(-value)) == 0) {
            return  value >= 0 ? 1 : -1;
        }
        return (float)(Math.exp(value) - Math.exp(-value)) / (float)(Math.exp(value) + Math.exp(-value));
    }
}
