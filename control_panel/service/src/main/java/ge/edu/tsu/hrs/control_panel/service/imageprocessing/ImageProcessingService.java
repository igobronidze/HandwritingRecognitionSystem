package ge.edu.tsu.hrs.control_panel.service.imageprocessing;

import ge.edu.tsu.hrs.control_panel.model.imageprocessing.BlurringParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.MorphologicalParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.TextCutterParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.ThresholdParameters;

import java.awt.image.BufferedImage;
import java.util.List;

public interface ImageProcessingService {

    BufferedImage cleanImage(BufferedImage srcImage, BlurringParameters blurringParameters, ThresholdParameters thresholdParameters, MorphologicalParameters morphologicalParameters);

    List<BufferedImage> getCutSymbols(BufferedImage srcImage, TextCutterParameters parameters);

    List<String> processTextForImage(String text, boolean doubleQuoteAsTwoChar);

    void saveCutSymbols(List<BufferedImage> images, List<String> text, String directoryPath);
}
