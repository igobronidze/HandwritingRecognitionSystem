package ge.edu.tsu.hrs.control_panel.service.imageprocessing;

import ge.edu.tsu.hrs.control_panel.model.imageprocessing.blurrin.BlurringParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.morphological.MorphologicalParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.TextCutterParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.threshold.ThresholdParameters;

import java.awt.image.BufferedImage;
import java.util.List;

public interface ImageProcessingService {

    BufferedImage cleanImage(BufferedImage srcImage, BlurringParameters blurringParameters, ThresholdParameters thresholdParameters, MorphologicalParameters morphologicalParameters);

    List<BufferedImage> getCutSymbols(BufferedImage srcImage, TextCutterParameters parameters, boolean forceNotJoining);

    List<String> processTextForImage(String text, boolean doubleQuoteAsTwoChar);

    void saveCutSymbols(List<BufferedImage> images, List<String> text, String directoryPath);
}
