package ge.edu.tsu.hcrs.control_panel.server.processor.normalizeddata.normalizationmethod;

import ge.edu.tsu.hcrs.control_panel.model.network.TrainingDataInfo;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.hcrs.control_panel.server.processor.systemparameter.SystemParameterProcessor;
import ge.edu.tsu.hcrs.image_processing.characterdetect.model.Contour;

import java.awt.image.BufferedImage;

public class DiscreteByAreaNormalization extends Normalization {

    private final SystemParameterProcessor systemParameterProcessor = new SystemParameterProcessor();

    private final float coeficient = systemParameterProcessor.getFloatParameterValue(new Parameter("discreteByAreaNormalizationCoeficient", "0.5"));

    @Override
    public NormalizedData getNormalizedDataFromImage(BufferedImage image, TrainingDataInfo trainingDataInfo, Character letter) {
        NormalizedData normalizedData = new NormalizedData();
        normalizedData.setLetter(letter);
        float[][] normalizedAreas = super.getNormalizedAreas(image, trainingDataInfo.getHeight(), trainingDataInfo.getWidth());
        Float[] data = new Float[trainingDataInfo.getHeight() * trainingDataInfo.getWidth()];
        for (int i = 0; i < normalizedAreas.length; i++) {
            for (int j = 0; j < normalizedAreas[i].length; j++) {
                data[i * normalizedAreas[i].length + j] = (normalizedAreas[i][j] >= coeficient) ? trainingDataInfo.getMaxValue() : trainingDataInfo.getMinValue();
            }
        }
        normalizedData.setData(data);
        return normalizedData;
    }

    @Override
    public NormalizedData getNormalizedDataFromContour(Contour contour, TrainingDataInfo trainingDataInfo) {
        NormalizedData normalizedData = new NormalizedData();
        float[][] normalizedAreas = super.getNormalizedAreas(contour, trainingDataInfo.getHeight(), trainingDataInfo.getWidth());
        Float[] data = new Float[trainingDataInfo.getHeight() * trainingDataInfo.getWidth()];
        for (int i = 0; i < normalizedAreas.length; i++) {
            for (int j = 0; j < normalizedAreas[i].length; j++) {
                data[i * normalizedAreas[i].length + j] = (normalizedAreas[i][j] >= coeficient) ? trainingDataInfo.getMaxValue() : trainingDataInfo.getMinValue();
            }
        }
        normalizedData.setData(data);
        return normalizedData;
    }
}
