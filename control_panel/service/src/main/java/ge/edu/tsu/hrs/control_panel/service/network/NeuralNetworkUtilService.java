package ge.edu.tsu.hrs.control_panel.service.network;

public interface NeuralNetworkUtilService {

	void deleteNeuralNetwork(int id);

	boolean deleteNetworkFromFile(int id);

	boolean deleteChildNetworks(int id);
}
