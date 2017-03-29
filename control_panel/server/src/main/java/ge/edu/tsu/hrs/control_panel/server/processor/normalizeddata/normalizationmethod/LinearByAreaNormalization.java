package ge.edu.tsu.hrs.control_panel.server.processor.normalizeddata.normalizationmethod;

import ge.edu.tsu.hrs.control_panel.model.network.TrainingDataInfo;
import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.NormalizedData;
import ge.edu.tsu.hrs.image_processing.characterdetect.model.Contour;

import java.awt.image.BufferedImage;

public class LinearByAreaNormalization extends Normalization {

    @Override
    public NormalizedData getNormalizedDataFromImage(BufferedImage image, TrainingDataInfo trainingDataInfo, Character letter) {
        NormalizedData normalizedData = new NormalizedData();
        normalizedData.setLetter(letter);
        float[][] normalizedAreas = super.getNormalizedAreas(image, trainingDataInfo.getHeight(), trainingDataInfo.getWidth());
        Float[] data = new Float[trainingDataInfo.getHeight() * trainingDataInfo.getWidth()];
        for (int i = 0; i < normalizedAreas.length; i++) {
            for (int j = 0; j < normalizedAreas[i].length; j++) {
                data[i * normalizedAreas[i].length + j] = normalized(normalizedAreas[i][j], trainingDataInfo.getMinValue(), trainingDataInfo.getMaxValue());
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
                data[i * normalizedAreas[i].length + j] = normalized(normalizedAreas[i][j], trainingDataInfo.getMinValue(), trainingDataInfo.getMaxValue());
            }
        }
        normalizedData.setData(data);
        return normalizedData;
    }

    private float normalized(float value, float min, float max) {
        float diff = max - min;
        return diff * value + min;
    }
}
