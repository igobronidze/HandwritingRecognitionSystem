package ge.edu.tsu.hrs.control_panel.service.imageprocessing;

import ge.edu.tsu.hrs.control_panel.model.imageprocessing.blurrin.BlurringParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.morphological.MorphologicalParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.TextCutterParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.threshold.ThresholdParameters;
import ge.edu.tsu.hrs.control_panel.server.processor.imageprocessing.ImageProcessingProcessor;

import java.awt.image.BufferedImage;
import java.util.List;

public class ImageProcessingServiceImpl implements ImageProcessingService {

    private ImageProcessingProcessor imageProcessingProcessor = new ImageProcessingProcessor();

    public BufferedImage cleanImage(BufferedImage srcImage, BlurringParameters blurringParameters, ThresholdParameters thresholdParameters, MorphologicalParameters morphologicalParameters) {
        return imageProcessingProcessor.cleanImage(srcImage, blurringParameters, thresholdParameters, morphologicalParameters);
    }

    public List<BufferedImage> getCutSymbols(BufferedImage srcImage, TextCutterParameters parameters, boolean forceNotJoining, Float extraColorsPart) {
        return imageProcessingProcessor.getCutSymbols(srcImage, parameters, forceNotJoining, extraColorsPart);
    }

    public List<String> processTextForImage(String text, boolean doubleQuoteAsTwoChar) {
        return imageProcessingProcessor.processTextForImage(text, doubleQuoteAsTwoChar);
    }

    public void saveCutSymbols(List<BufferedImage> images, List<String> text, String directoryPath) {
        imageProcessingProcessor.saveCutSymbols(images, text, directoryPath);
    }
}
