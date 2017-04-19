package ge.edu.tsu.hrs.control_panel.service.network;

import ge.edu.tsu.hrs.control_panel.server.dao.neuralnetwork.NeuralNetworkDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.neuralnetwork.NeuralNetworkDAOImpl;
import ge.edu.tsu.hrs.control_panel.server.processor.neuralnetwork.NeuralNetworkHelper;

public class NeuralNetworkUtilServiceImpl implements NeuralNetworkUtilService {

	private final NeuralNetworkDAO neuralNetworkDAO = new NeuralNetworkDAOImpl();

	@Override
	public void deleteNeuralNetwork(int id) {
		neuralNetworkDAO.deleteNeuralNetwork(id);
	}

	@Override
	public boolean deleteNetworkFromFile(int id) {
		return NeuralNetworkHelper.deleteNetworkFromFile(id);
	}

	@Override
	public boolean deleteChildNetworks(int id) {
		return NeuralNetworkHelper.deleteChildNetworks(id);
	}
}
