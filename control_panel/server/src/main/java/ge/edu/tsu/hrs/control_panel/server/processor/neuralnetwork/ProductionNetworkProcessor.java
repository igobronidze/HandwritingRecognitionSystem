package ge.edu.tsu.hrs.control_panel.server.processor.neuralnetwork;

import ge.edu.tsu.hrs.control_panel.model.network.CharSequence;
import ge.edu.tsu.hrs.control_panel.model.network.TrainingDataInfo;
import ge.edu.tsu.hrs.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.hrs.control_panel.server.dao.networkinfo.NetworkInfoDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.networkinfo.NetworkInfoDAOImpl;
import ge.edu.tsu.hrs.control_panel.server.dao.trainingdatainfo.TrainingDataInfoDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.trainingdatainfo.TrainingDataInfoDAOImpl;
import ge.edu.tsu.hrs.control_panel.server.processor.systemparameter.SystemParameterProcessor;
import ge.edu.tsu.hrs.neural_network.exception.NNException;
import ge.edu.tsu.hrs.neural_network.neural.network.NeuralNetwork;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ProductionNetworkProcessor {

    private final SystemParameterProcessor systemParameterProcessor = new SystemParameterProcessor();

    private final TrainingDataInfoDAO trainingDataInfoDAO = new TrainingDataInfoDAOImpl();

    private static final NetworkInfoDAO networkInfoDAO = new NetworkInfoDAOImpl();

    private final Parameter productionNetworkPath = new Parameter("productionNetworkPath", "network_workspace/production.nnet");

    private final Parameter productionTrainingDataInfoPath = new Parameter("productionTrainingDataInfoPath", "network_workspace/production.tdi");

    private final Parameter productionCharSequencePath = new Parameter("productionCharSequencePath", "network_workspace/production.chs");

    public void updateProductionNetwork(int networkId) {
        try {
            NeuralNetwork neuralNetwork = NeuralNetworkHelper.loadNeuralNetwork(networkId);
            NeuralNetwork.save(systemParameterProcessor.getStringParameterValue(productionNetworkPath), neuralNetwork);
            TrainingDataInfo trainingDataInfo = trainingDataInfoDAO.getTrainingDataInfo(networkId);
            serializeTrainingDataInfo(trainingDataInfo, systemParameterProcessor.getStringParameterValue(productionTrainingDataInfoPath));
            CharSequence charSequence = networkInfoDAO.getCharSequenceById(networkId);
            serializeCharSequence(charSequence, systemParameterProcessor.getStringParameterValue(productionCharSequencePath));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("can't update production network");
        }
    }

    public NeuralNetwork getProductionNeuralNetwork() {
        try {
            return NeuralNetwork.load(systemParameterProcessor.getStringParameterValue(productionNetworkPath));
        } catch (NNException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public TrainingDataInfo getProductionTrainingDataInfo() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(systemParameterProcessor.getStringParameterValue(productionTrainingDataInfoPath)))) {
            return (TrainingDataInfo) in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public CharSequence getProductionCharSequence() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(systemParameterProcessor.getStringParameterValue(productionCharSequencePath)))){
            return (CharSequence) in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    private void serializeTrainingDataInfo(TrainingDataInfo trainingDataInfo, String path) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
        out.writeObject(trainingDataInfo);
        out.close();
    }

    private void serializeCharSequence(CharSequence charSequence, String path) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
        out.writeObject(charSequence);
        out.close();
    }
}
