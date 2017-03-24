package ge.edu.tsu.hcrs.control_panel.server.processor.normalizeddata.normalizationmethod;

import ge.edu.tsu.hcrs.control_panel.model.network.TrainingDataInfo;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.hcrs.control_panel.server.processor.imageprocessing.ImageProcessingProcessor;
import ge.edu.tsu.hcrs.control_panel.server.processor.systemparameter.SystemParameterProcessor;
import ge.edu.tsu.hcrs.image_processing.characterdetect.model.Contour;
import ge.edu.tsu.hcrs.image_processing.characterdetect.util.ContourUtil;

import java.awt.image.BufferedImage;

public class DiscreteResizeNormalization extends Normalization {

    private final ImageProcessingProcessor imageProcessingProcessor = new ImageProcessingProcessor();

    private final SystemParameterProcessor systemParameterProcessor = new SystemParameterProcessor();

    private final float coeficient = systemParameterProcessor.getFloatParameterValue(new Parameter("discreteResizeNormalizationCoeficient", "0.5"));

    @Override
    public NormalizedData getNormalizedDataFromImage(BufferedImage image, TrainingDataInfo trainingDataInfo, Character letter) {
        BufferedImage resizedImage = imageProcessingProcessor.resizeImage(image, false, trainingDataInfo.getWidth(), trainingDataInfo.getHeight());
        NormalizedData normalizedData = new NormalizedData();
        normalizedData.setLetter(letter);
        Float[] data = new Float[trainingDataInfo.getHeight() * trainingDataInfo.getWidth()];
        for (int i = 0; i < trainingDataInfo.getHeight(); i++) {
            for (int j = 0; j < trainingDataInfo.getWidth(); j++) {
                data[i * trainingDataInfo.getWidth() + j] = ((float)resizedImage.getRGB(j, i) / blackPixel >= coeficient) ? trainingDataInfo.getMaxValue() : trainingDataInfo.getMinValue();
            }
        }
        normalizedData.setData(data);
        return normalizedData;
    }

    @Override
    public NormalizedData getNormalizedDataFromContour(Contour contour, TrainingDataInfo trainingDataInfo) {
        BufferedImage image = ContourUtil.getBufferedImageFromContour(contour);
        BufferedImage resizedImage = imageProcessingProcessor.resizeImage(image, false, trainingDataInfo.getWidth(), trainingDataInfo.getHeight());
        NormalizedData normalizedData = new NormalizedData();
        Float[] data = new Float[trainingDataInfo.getHeight() * trainingDataInfo.getWidth()];
        for (int i = 0; i < trainingDataInfo.getHeight(); i++) {
            for (int j = 0; j < trainingDataInfo.getWidth(); j++) {
                data[i * trainingDataInfo.getWidth() + j] = ((float)resizedImage.getRGB(j, i) / blackPixel >= coeficient) ? trainingDataInfo.getMaxValue() : trainingDataInfo.getMinValue();
            }
        }
        normalizedData.setData(data);
        return normalizedData;
    }
}
