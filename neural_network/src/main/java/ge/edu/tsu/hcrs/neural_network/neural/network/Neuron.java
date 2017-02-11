package ge.edu.tsu.hcrs.neural_network.neural.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Neuron implements Serializable {

    public static final long serialVersionUID = 7356392L;

    private float bias;

    private float delta;

    private List<Connection> inConnections = new ArrayList<>();

    private List<Connection> outConnections = new ArrayList<>();

    private float activationValue;

    public float getBias() {
        return bias;
    }

    public void setBias(float bias) {
        this.bias = bias;
    }

    public List<Connection> getInConnections() {
        return inConnections;
    }

    public float getActivationValue() {
        return activationValue;
    }

    public void setActivationValue(float activationValue) {
        this.activationValue = activationValue;
    }

    public List<Connection> getOutConnections() {
        return outConnections;
    }

    public float getDelta() {
        return delta;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }
}
