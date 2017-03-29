package ge.edu.tsu.hrs.neural_network.transfer;

public class PieceWiseLinearFunction implements TransferFunction {

    @Override
    public float transfer(float value) {
        if (value >= 0.5f) {
            return 1;
        } else if (value <= -0.5f) {
            return 0;
        } else {
            return value + 0.5f;
        }
    }
}
