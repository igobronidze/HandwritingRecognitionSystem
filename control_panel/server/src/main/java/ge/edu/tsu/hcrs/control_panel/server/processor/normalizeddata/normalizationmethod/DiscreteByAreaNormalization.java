package ge.edu.tsu.hcrs.control_panel.server.processor.normalizeddata.normalizationmethod;

import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizedData;

import java.awt.image.BufferedImage;

public class DiscreteByAreaNormalization extends NormalizationMethod {

    private float coeficient = 0.5F;

    @Override
    public NormalizedData getNormalizedDataFromImage(BufferedImage image, GroupedNormalizedData groupedNormalizedData, Character letter) {
        NormalizedData normalizedData = new NormalizedData();
        normalizedData.setLetter(letter);
        float[][] normalizedAreas = super.getNormalizedAreas(image, groupedNormalizedData.getHeight(), groupedNormalizedData.getWidth());
        Float[] data = new Float[groupedNormalizedData.getHeight() * groupedNormalizedData.getWidth()];
        for (int i = 0; i < normalizedAreas.length; i++) {
            for (int j = 0; j < normalizedAreas[i].length; j++) {
                data[i * normalizedAreas[i].length + j] = (normalizedAreas[i][j] >= coeficient) ? groupedNormalizedData.getMaxValue() : groupedNormalizedData.getMinValue();
            }
        }
        normalizedData.setData(data);
        return normalizedData;
    }
}
