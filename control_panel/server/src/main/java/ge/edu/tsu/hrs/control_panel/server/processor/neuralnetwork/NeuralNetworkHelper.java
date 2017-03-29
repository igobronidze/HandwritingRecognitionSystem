package ge.edu.tsu.hrs.control_panel.server.processor.neuralnetwork;

import ge.edu.tsu.hrs.control_panel.model.common.HRSPath;
import ge.edu.tsu.hrs.control_panel.server.dao.neuralnetwork.NeuralNetworkDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.neuralnetwork.NeuralNetworkDAOImpl;
import ge.edu.tsu.hrs.control_panel.server.processor.common.HRSPathProcessor;
import ge.edu.tsu.hrs.neural_network.exception.NNException;
import ge.edu.tsu.hrs.neural_network.neural.network.NeuralNetwork;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class NeuralNetworkHelper {

    private static final NeuralNetworkDAO neuralNetworkDAO = new NeuralNetworkDAOImpl();

    private static final HRSPathProcessor hrsPathProcessor = new HRSPathProcessor();

    public static void saveNeuralNetwork(int id, NeuralNetwork neuralNetwork) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(neuralNetwork);
            out.flush();
            byte[] bytes = bos.toByteArray();
            neuralNetworkDAO.addNeuralNetwork(id, bytes);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static NeuralNetwork loadNeuralNetwork(int id) {
        NeuralNetwork neuralNetwork;
        try {
            neuralNetwork = NeuralNetwork.load(hrsPathProcessor.getPath(HRSPath.NEURAL_NETWORKS_PATH) + id + ".nnet");
        } catch (NNException ex) {
            System.out.println(ex.getMessage());
            neuralNetwork = loadFromDatabase(id);
        }
        return neuralNetwork;
    }

    private static NeuralNetwork loadFromDatabase(int id) {
        NeuralNetwork neuralNetwork = null;
        byte[] bytes = neuralNetworkDAO.getNeuralNetworkData(id);
        if (bytes == null) {
            return null;
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try (ObjectInput in = new ObjectInputStream(bis)){
            neuralNetwork = (NeuralNetwork)in.readObject();
        } catch (IOException | ClassCastException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return neuralNetwork;
    }
}
