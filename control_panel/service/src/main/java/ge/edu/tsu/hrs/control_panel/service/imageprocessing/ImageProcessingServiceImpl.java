package ge.edu.tsu.hrs.control_panel.service.imageprocessing;

import ge.edu.tsu.hrs.control_panel.model.imageprocessing.BlurringParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.MorphologicalParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.TextCutterParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.ThresholdParameters;
import ge.edu.tsu.hrs.control_panel.server.processor.imageprocessing.ImageProcessingProcessor;

import java.awt.image.BufferedImage;
import java.util.List;

public class ImageProcessingServiceImpl implements ImageProcessingService {

    private ImageProcessingProcessor imageProcessingProcessor = new ImageProcessingProcessor();

    public BufferedImage cleanImage(BufferedImage srcImage, BlurringParameters blurringParameters, ThresholdParameters thresholdParameters, MorphologicalParameters morphologicalParameters) {
        return imageProcessingProcessor.cleanImage(srcImage, blurringParameters, thresholdParameters, morphologicalParameters);
    }

    public List<BufferedImage> getCutSymbols(BufferedImage srcImage, TextCutterParameters parameters) {
        return imageProcessingProcessor.getCutSymbols(srcImage, parameters);
    }

    public String processTextForImage(String text, boolean doubleQuoteAsTwoChar) {
        return imageProcessingProcessor.processTextForImage(text, doubleQuoteAsTwoChar);
    }

    public void saveCutSymbols(List<BufferedImage> images, String text, String directoryPath) {
        imageProcessingProcessor.saveCutSymbols(images, text, directoryPath);
    }
}
