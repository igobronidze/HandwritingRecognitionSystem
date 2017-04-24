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
import java.io.File;
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
                System.out.println("Save neural network in file system, path - " + path);
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
            System.out.println("Save network in file system with id - " + id);
        }, "Save network in file system thread", systemParameterProcessor.getLongParameterValue(stackSizeForNetworkStream));
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
        }
        if (saveInDatabase) {
            thread = new Thread(null, () -> {
                saveNetworkInDatabase(id, neuralNetwork);
                System.out.println("Save network in database with id - " + id);
            }, "Save network in database thread", systemParameterProcessor.getLongParameterValue(stackSizeForNetworkStream));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException ex) {
            }
        }
    }

    public static NeuralNetwork loadNeuralNetwork(int id, int extraId, boolean loadFromDatabase, String path) {
        final NeuralNetwork neuralNetwork = new NeuralNetwork();
        if (path != null && !path.isEmpty()) {
            Thread thread = new Thread(null, () -> {
                NeuralNetwork.copyNetwork(loadNetworkFromPath(path), neuralNetwork);
                if (neuralNetwork.getNeuralNetworkParameter() != null) {
                    System.out.println("Loaded network from path " + path);
                }
            }, "Load network from file system thread", systemParameterProcessor.getLongParameterValue(stackSizeForNetworkStream));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException ex) {
            }
        } else {
            Thread thread = new Thread(null, () -> {
                NeuralNetwork.copyNetwork(loadNetworkFromFileSystem(id, extraId), neuralNetwork);
                if (neuralNetwork.getNeuralNetworkParameter() != null) {
                    System.out.println("Loaded network from file system with id - " + id + ", extra id - " + extraId);
                }
            }, "Load network from file system thread", systemParameterProcessor.getLongParameterValue(stackSizeForNetworkStream));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException ex) {
            }
            if (neuralNetwork.getNeuralNetworkParameter() != null) {
                return neuralNetwork;
            }
            thread = new Thread(null, () -> {
                if (loadFromDatabase && extraId == 0) {
                    NeuralNetwork.copyNetwork(loadNetworkFromDatabase(id), neuralNetwork);
                    if (neuralNetwork.getNeuralNetworkParameter() != null) {
                        System.out.println("Loaded network from database with id - " + id);
                    }
                }
            }, "Load network from database thread", systemParameterProcessor.getLongParameterValue(stackSizeForNetworkStream));
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

    public static boolean deleteNetworkFromFile(int id) {
        File file = new File(hrsPathProcessor.getPath(HRSPath.NEURAL_NETWORKS_PATH) + id + ".nnet");
        return file.isFile() && file.delete();
    }

    public static boolean deleteChildNetworks(int id) {
        File file = new File(hrsPathProcessor.getPath(HRSPath.NEURAL_NETWORKS_PATH) + id);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null != files) {
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
        }
        return file.exists() && file.delete();
    }

    private static void saveNetworkInFileSystem(int id, NeuralNetwork neuralNetwork) {
        try {
            NeuralNetwork.save(hrsPathProcessor.getPath(HRSPath.NEURAL_NETWORKS_PATH) + id + ".nnet", neuralNetwork, false);
        } catch (NNException ex) {
            ex.printStackTrace();
        }
    }

    private static void saveNetworkWithPath(NeuralNetwork neuralNetwork, String path) {
        try {
            NeuralNetwork.save(path, neuralNetwork, false);
        } catch (NNException ex) {
            ex.printStackTrace();
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
            ex.printStackTrace();
        }
    }

    private static NeuralNetwork loadNetworkFromFileSystem(int id, int extraId) {
        try {
            if (extraId == 0) {
                return NeuralNetwork.load(hrsPathProcessor.getPath(HRSPath.NEURAL_NETWORKS_PATH) + id + ".nnet");
            } else {
                return NeuralNetwork.load(hrsPathProcessor.getPath(HRSPath.NEURAL_NETWORKS_PATH) + id + "/" + extraId + ".nnet");
            }
        } catch (NNException ex) {
            System.out.println("Can't load network from file system!");
        }
        return null;
    }

    private static NeuralNetwork loadNetworkFromPath(String path) {
        try {
            return NeuralNetwork.load(path);
        } catch (NNException ex) {
            ex.printStackTrace();
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
            ex.printStackTrace();
        }
        return neuralNetwork;
    }
}
