package ge.edu.tsu.hcrs.neural_network.neural.network;

import java.io.Serializable;

public class Connection implements Serializable {

    public static final long serialVersionUID = 134252L;

    private float weight;

    private Neuron leftNeuron;

    private Neuron rightNeuron;

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Neuron getLeftNeuron() {
        return leftNeuron;
    }

    public void setLeftNeuron(Neuron leftNeuron) {
        this.leftNeuron = leftNeuron;
    }

    public Neuron getRightNeuron() {
        return rightNeuron;
    }

    public void setRightNeuron(Neuron rightNeuron) {
        this.rightNeuron = rightNeuron;
    }
}
