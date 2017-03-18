package ge.edu.tsu.hcrs.control_panel.server.processor.normalizeddata.normalizationmethod;

import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.hcrs.control_panel.server.processor.imageprocessing.ImageProcessingProcessor;
import ge.edu.tsu.hcrs.control_panel.server.processor.systemparameter.SystemParameterProcessor;

import java.awt.image.BufferedImage;

public class DiscreteResizeNormalization extends NormalizationMethod {

    private ImageProcessingProcessor imageProcessingProcessor = new ImageProcessingProcessor();

    private SystemParameterProcessor systemParameterProcessor = new SystemParameterProcessor();

    private float coeficient = systemParameterProcessor.getFloatParameterValue(new Parameter("discreteResizeNormalizationCoeficient", "0.5"));

    @Override
    public NormalizedData getNormalizedDataFromImage(BufferedImage image, GroupedNormalizedData groupedNormalizedData, Character letter) {
        BufferedImage resizedImage = imageProcessingProcessor.resizeImage(image, false, groupedNormalizedData.getWidth(), groupedNormalizedData.getHeight());
        NormalizedData normalizedData = new NormalizedData();
        normalizedData.setLetter(letter);
        Float[] data = new Float[groupedNormalizedData.getHeight() * groupedNormalizedData.getWidth()];
        for (int i = 0; i < groupedNormalizedData.getHeight(); i++) {
            for (int j = 0; j < groupedNormalizedData.getWidth(); j++) {
                data[i * groupedNormalizedData.getWidth() + j] = ((float)resizedImage.getRGB(j, i) / blackPixel >= coeficient) ? groupedNormalizedData.getMaxValue() : groupedNormalizedData.getMinValue();
            }
        }
        normalizedData.setData(data);
        return normalizedData;
    }
}
