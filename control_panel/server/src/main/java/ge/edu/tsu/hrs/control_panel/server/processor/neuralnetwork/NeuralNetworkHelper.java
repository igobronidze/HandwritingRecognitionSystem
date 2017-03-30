package ge.edu.tsu.hrs.control_panel.server.processor.neuralnetwork;

import ge.edu.tsu.hrs.control_panel.model.common.HRSPath;
import ge.edu.tsu.hrs.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.hrs.control_panel.server.dao.neuralnetwork.NeuralNetworkDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.neuralnetwork.NeuralNetworkDAOImpl;
import ge.edu.tsu.hrs.control_panel.server.processor.common.HRSPathProcessor;
import ge.edu.tsu.hrs.control_panel.server.processor.systemparameter.SystemParameterProcessor;
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

    private static final SystemParameterProcessor systemParameterProcessor = new SystemParameterProcessor();

    private static final NeuralNetworkDAO neuralNetworkDAO = new NeuralNetworkDAOImpl();

    private static final HRSPathProcessor hrsPathProcessor = new HRSPathProcessor();

    private static final Parameter stackSizeForNetworkStream = new Parameter("stackSizeForNetworkStream", "8000000");

    public static void saveNeuralNetwork(int id, NeuralNetwork neuralNetwork, boolean saveInDatabase, String path) {
        if (path != null && !path.isEmpty()) {
            Thread thread = new Thread(null, () -> {
                saveNetworkWithPath(neuralNetwork, path);
            }, "Save network in file system thread", systemParameterProcessor.getLongParameterValue(stackSizeForNetworkStream));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException ex) {
            }
            return;
        }
        Thread thread = new Thread(null, () -> {
            saveNetworkInFileSystem(id, neuralNetwork);
        }, "Save network in file system thread", systemParameterProcessor.getLongParameterValue(stackSizeForNetworkStream));
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
        }
        if (saveInDatabase) {
            thread = new Thread(null, () -> {
                saveNetworkInDatabase(id, neuralNetwork);
            }, "Save network in database thread", systemParameterProcessor.getLongParameterValue(stackSizeForNetworkStream));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException ex) {
            }
        }
    }

    public static NeuralNetwork loadNeuralNetwork(int id, boolean loadFromDatabase, String path) {
        final NeuralNetwork neuralNetwork = new NeuralNetwork();
        if (path != null && !path.isEmpty()) {
            Thread thread = new Thread(null, () -> {
                NeuralNetwork.copyNetwork(loadNetworkFromPath(path), neuralNetwork);
            }, "Save network in file system thread", systemParameterProcessor.getLongParameterValue(stackSizeForNetworkStream));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException ex) {
            }
        } else {
            Thread thread = new Thread(null, () -> {
                NeuralNetwork.copyNetwork(loadNetworkFromFileSystem(id), neuralNetwork);
            }, "Save network in file system thread", systemParameterProcessor.getLongParameterValue(stackSizeForNetworkStream));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException ex) {
            }
            if (neuralNetwork.getNeuralNetworkParameter() != null) {
                return neuralNetwork;
            }
            thread = new Thread(null, () -> {
                if (loadFromDatabase) {
                    NeuralNetwork.copyNetwork(loadNetworkFromDatabase(id), neuralNetwork);
                }
            }, "Save network in file system thread", systemParameterProcessor.getLongParameterValue(stackSizeForNetworkStream));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException ex) {
            }
        }
        if (neuralNetwork.getNeuralNetworkParameter() != null) {
            return neuralNetwork;
        }
        return null;
    }

    private static void saveNetworkInFileSystem(int id, NeuralNetwork neuralNetwork) {
        try {
            NeuralNetwork.save(hrsPathProcessor.getPath(HRSPath.NEURAL_NETWORKS_PATH) + id + ".nnet", neuralNetwork);
        } catch (NNException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void saveNetworkWithPath(NeuralNetwork neuralNetwork, String path) {
        try {
            NeuralNetwork.save(path, neuralNetwork);
        } catch (NNException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void saveNetworkInDatabase(int id, NeuralNetwork neuralNetwork) {
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

    private static NeuralNetwork loadNetworkFromFileSystem(int id) {
        try {
            return NeuralNetwork.load(hrsPathProcessor.getPath(HRSPath.NEURAL_NETWORKS_PATH) + id + ".nnet");
        } catch (NNException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    private static NeuralNetwork loadNetworkFromPath(String path) {
        try {
            return NeuralNetwork.load(path);
        } catch (NNException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    private static NeuralNetwork loadNetworkFromDatabase(int id) {
        NeuralNetwork neuralNetwork = null;
        byte[] bytes = neuralNetworkDAO.getNeuralNetworkData(id);
        if (bytes == null) {
            return null;
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try (ObjectInput in = new ObjectInputStream(bis)) {
            neuralNetwork = (NeuralNetwork) in.readObject();
        } catch (IOException | ClassCastException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return neuralNetwork;
    }
}
