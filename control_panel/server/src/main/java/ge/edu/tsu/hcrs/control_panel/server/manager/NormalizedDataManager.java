package ge.edu.tsu.hcrs.control_panel.server.manager;

import ge.edu.tsu.hcrs.control_panel.model.network.NormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.CharSequence;
import ge.edu.tsu.hcrs.neural_network.neural.network.TrainingData;
import org.neuroph.core.data.DataSetRow;

import java.util.ArrayList;
import java.util.List;

public class NormalizedDataManager {

    public TrainingData getTrainingData(NormalizedData normalizedData, CharSequence charSequence) {
        Float[] data = normalizedData.getData();
        List<Float> input = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            input.add(data[i]);
        }
        if (normalizedData.getLetter() != null) {
            List<Float> output = new ArrayList<>();
            for (int i = 0; i < charSequence.getNumberOfChars(); i++) {
                output.add(i == normalizedData.getLetter() - charSequence.getFirstCharASCI() ? 1.0f : 0.0f);
            }
            return new TrainingData(input, output);
        }
        return new TrainingData(input);
    }

    public DataSetRow getDataSetRow(NormalizedData normalizedData, CharSequence charSequence) {
        Float[] data = normalizedData.getData();
        double[] input = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            input[i] = data[i];
        }
        double[] ans = new double[charSequence.getNumberOfChars()];
        if (normalizedData.getLetter() != null) {
            ans[normalizedData.getLetter() - charSequence.getFirstCharASCI()] = 1;
        }
        return new DataSetRow(input, ans);
    }
}
