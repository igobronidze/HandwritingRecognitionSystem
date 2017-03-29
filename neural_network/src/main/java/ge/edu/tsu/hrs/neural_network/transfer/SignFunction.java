package ge.edu.tsu.hrs.neural_network.transfer;

public class SignFunction implements TransferFunction {

    @Override
    public float transfer(float value) {
        if (value < 0) {
            return -1;
        } else if (value == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
