package ge.edu.tsu.hrs.control_panel.server.dao.neuralnetwork;

public interface NeuralNetworkDAO {

    void addNeuralNetwork(int id, byte[] data);

    byte[] getNeuralNetworkData(int id);

    void deleteNeuralNetwork(int id);
}
