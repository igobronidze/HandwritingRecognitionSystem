package ge.edu.tsu.hcrs.control_panel.server.processor.normalizeddata.normalizationmethod;

import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizedData;
import ge.edu.tsu.hcrs.control_panel.server.processor.imageprocessing.ImageProcessingProcessor;

import java.awt.image.BufferedImage;

public class LinearResizeNormalization extends NormalizationMethod {

    private ImageProcessingProcessor imageProcessingProcessor = new ImageProcessingProcessor();

    @Override
    public NormalizedData getNormalizedDataFromImage(BufferedImage image, GroupedNormalizedData groupedNormalizedData, Character letter) {
        BufferedImage resizedImage = imageProcessingProcessor.resizeImage(image, false, groupedNormalizedData.getWidth(), groupedNormalizedData.getHeight());
        NormalizedData normalizedData = new NormalizedData();
        normalizedData.setLetter(letter);
        Float[] data = new Float[groupedNormalizedData.getHeight() * groupedNormalizedData.getWidth()];
        for (int i = 0; i < groupedNormalizedData.getHeight(); i++) {
            for (int j = 0; j < groupedNormalizedData.getWidth(); j++) {
                data[i * groupedNormalizedData.getWidth() + j] = (float)resizedImage.getRGB(j, i) / blackPixel;
            }
        }
        normalizedData.setData(data);
        return normalizedData;
    }
}
