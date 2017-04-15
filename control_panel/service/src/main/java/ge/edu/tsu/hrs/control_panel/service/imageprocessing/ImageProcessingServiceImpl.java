package ge.edu.tsu.hrs.control_panel.service.imageprocessing;

import ge.edu.tsu.hrs.control_panel.model.imageprocessing.BlurringParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.MorphologicalParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.ThresholdParameters;
import ge.edu.tsu.hrs.control_panel.server.processor.imageprocessing.ImageProcessingProcessor;

import java.awt.image.BufferedImage;

public class ImageProcessingServiceImpl implements ImageProcessingService {

    private ImageProcessingProcessor imageProcessingProcessor = new ImageProcessingProcessor();

    public BufferedImage cleanImage(BufferedImage srcImage, BlurringParameters blurringParameters, ThresholdParameters thresholdParameters, MorphologicalParameters morphologicalParameters) {
        return imageProcessingProcessor.cleanImage(srcImage, blurringParameters, thresholdParameters, morphologicalParameters);
    }
}
