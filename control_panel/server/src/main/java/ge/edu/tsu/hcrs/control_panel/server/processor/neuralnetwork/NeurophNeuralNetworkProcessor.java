package ge.edu.tsu.hcrs.control_panel.server.processor.neuralnetwork;

import ge.edu.tsu.hcrs.control_panel.model.network.NetworkInfo;
import ge.edu.tsu.hcrs.control_panel.model.network.NetworkResult;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.CharSequence;
import ge.edu.tsu.hcrs.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.NormalizedDataDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.NormalizedDataDAOImpl;
import ge.edu.tsu.hcrs.control_panel.server.processor.systemparameter.SystemParameterProcessor;
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
public class NeurophNeuralNetworkProcessor implements INeuralNetworkProcessor {

    private SystemParameterProcessor systemParameterProcessor = new SystemParameterProcessor();

    private NormalizedDataDAO normalizedDataDAO = new NormalizedDataDAOImpl();

    private Parameter neuralNetworkPathParameter = new Parameter("neuralNetworkPath", "D:\\sg\\handwriting_recognition\\network\\network.nnet");

    public NeurophNeuralNetworkProcessor() {
    }

    @Override
    public void trainNeural(NetworkInfo networkInfo) {
        int width = networkInfo.getWidth();
        int height = networkInfo.getHeight();
        CharSequence charSequence = networkInfo.getCharSequence();
        List<NormalizedData> normalizedDataList = normalizedDataDAO.getNormalizedDatum(networkInfo.getGroupedNormalizedDatum());
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
        MultiLayerPerceptron perceptron = null;
        try {
            perceptron = (MultiLayerPerceptron) NeuralNetwork.createFromFile(systemParameterProcessor.getStringParameterValue(neuralNetworkPathParameter));
        } catch (Exception ex) {
            perceptron = new MultiLayerPerceptron(layers, TransferFunctionType.SIGMOID);
        }
        perceptron.learn(trainingSet);
        perceptron.save(systemParameterProcessor.getStringParameterValue(neuralNetworkPathParameter));
    }

    @Override
    public NetworkResult getNetworkResult(NormalizedData normalizedData, String networkPath, CharSequence charSequence) {
        NeuralNetwork neuralNetwork = NeuralNetwork.createFromFile(systemParameterProcessor.getStringParameterValue(neuralNetworkPathParameter));
        DataSetRow dataSetRow = NetworkDataCreator.getDataSetRow(normalizedData, charSequence);
        neuralNetwork.setInput(dataSetRow.getInput());
        neuralNetwork.calculate();
        double[] networkOutput = neuralNetwork.getOutput();
        int ans = 0;
        List<Float> output = new ArrayList<>();
        for (int i = 1; i < charSequence.getNumberOfChars(); i++) {
            output.add((float)networkOutput[i]);
            if (networkOutput[i] > networkOutput[ans]) {
                ans = i;
            }
        }
        char c = charSequence.getIndexToCharMap().get(ans);
        return new NetworkResult(output, c);
    }

    @Override
    public float test(int width, int height, List<GroupedNormalizedData> groupedNormalizedDatum, String path, int networkId, CharSequence charSequence) {
        throw new NotImplementedException("Not yet :D");
    }
}
