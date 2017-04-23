package ge.edu.tsu.hrs.control_panel.server.processor.neuralnetwork;

import ge.edu.tsu.hrs.control_panel.model.exception.ControlPanelException;
import ge.edu.tsu.hrs.control_panel.model.network.NetworkInfo;
import ge.edu.tsu.hrs.control_panel.model.network.NetworkResult;
import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.NormalizedData;
import ge.edu.tsu.hrs.control_panel.model.network.CharSequence;
import ge.edu.tsu.hrs.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.hrs.control_panel.server.dao.networkinfo.NetworkInfoDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.networkinfo.NetworkInfoDAOImpl;
import ge.edu.tsu.hrs.control_panel.server.dao.normalizeddata.NormalizedDataDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.normalizeddata.NormalizedDataDAOImpl;
import ge.edu.tsu.hrs.control_panel.server.processor.systemparameter.SystemParameterProcessor;
import ge.edu.tsu.hrs.control_panel.server.util.CharSequenceInitializer;
import org.apache.commons.lang3.NotImplementedException;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Deprecated
public class NeurophNeuralNetworkProcessor {

    private SystemParameterProcessor systemParameterProcessor = new SystemParameterProcessor();

    private NormalizedDataDAO normalizedDataDAO = new NormalizedDataDAOImpl();

    private NetworkInfoDAO networkInfoDAO = new NetworkInfoDAOImpl();

    private Parameter neuralNetworkPathParameter = new Parameter("neuralNetworkPath", "D:\\sg\\handwriting_recognition\\network\\network.nnet");

    public NeurophNeuralNetworkProcessor() {
    }

//    @Override
    public void trainNeural(NetworkInfo networkInfo, List<GroupedNormalizedData> groupedNormalizedDatum, boolean saveInDataBase) {
        int width = -1;
        int height = -1;
        CharSequence charSequence = networkInfo.getCharSequence();
        List<NormalizedData> normalizedDataList = normalizedDataDAO.getNormalizedDatum(groupedNormalizedDatum);
        List<Integer> layers = new ArrayList<>();
        layers.add(width * height);
        for (int x : networkInfo.getHiddenLayer()) {
            layers.add(x);
        }
        layers.add(charSequence.getNumberOfChars());
        DataSet trainingSet = new DataSet(width * height, charSequence.getNumberOfChars());
        List<Integer> randomList = new ArrayList<>();
        for (int i = 0; i < normalizedDataList.size(); i++) {
            randomList.add(i);
        }
        Collections.shuffle(randomList);
        int min = Math.min((int)networkInfo.getNumberOfTrainingDataInOneIteration(), normalizedDataList.size());
        for (int i = 0; i < min; i++) {
            trainingSet.addRow(NetworkDataCreator.getDataSetRow(normalizedDataList.get(randomList.get(i)), charSequence));
        }
        MultiLayerPerceptron perceptron;
        try {
            perceptron = (MultiLayerPerceptron) NeuralNetwork.createFromFile(systemParameterProcessor.getStringParameterValue(neuralNetworkPathParameter));
        } catch (Exception ex) {
            perceptron = new MultiLayerPerceptron(layers, TransferFunctionType.SIGMOID);
        }
        perceptron.learn(trainingSet);
        perceptron.save(systemParameterProcessor.getStringParameterValue(neuralNetworkPathParameter));
    }

//    @Override
    public NetworkResult getNetworkResult(NormalizedData normalizedData, int networkId) {
        try {
            NeuralNetwork neuralNetwork = NeuralNetwork.createFromFile(systemParameterProcessor.getStringParameterValue(neuralNetworkPathParameter));
            CharSequence charSequence = networkInfoDAO.getCharSequenceById(networkId);
            CharSequenceInitializer.initializeCharSequence(charSequence);
            DataSetRow dataSetRow = NetworkDataCreator.getDataSetRow(normalizedData, charSequence);
            neuralNetwork.setInput(dataSetRow.getInput());
            neuralNetwork.calculate();
            double[] networkOutput = neuralNetwork.getOutput();
            int ans = 0;
            List<Float> output = new ArrayList<>();
            for (int i = 1; i < charSequence.getNumberOfChars(); i++) {
                output.add((float) networkOutput[i]);
                if (networkOutput[i] > networkOutput[ans]) {
                    ans = i;
                }
            }
            char c = charSequence.getIndexToCharMap().get(ans);
            NetworkResult networkResult = new NetworkResult();
            networkResult.setOutputActivation(output);
            networkResult.setAnswer(c);
            networkResult.setCharSequence(charSequence);
            return networkResult;
        } catch (ControlPanelException ex) {
            ex.printStackTrace();
        }
        return null;
    }

//    @Override
    public float testNeural(List<GroupedNormalizedData> groupedNormalizedDatum, int networkId) {
        throw new NotImplementedException("Not yet :D");
    }
}
