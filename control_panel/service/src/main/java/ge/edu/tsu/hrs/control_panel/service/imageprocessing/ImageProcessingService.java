package ge.edu.tsu.hrs.control_panel.service.imageprocessing;

import ge.edu.tsu.hrs.control_panel.model.imageprocessing.BlurringParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.MorphologicalParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.ThresholdParameters;

import java.awt.image.BufferedImage;

public interface ImageProcessingService {

    BufferedImage cleanImage(BufferedImage srcImage, BlurringParameters blurringParameters, ThresholdParameters thresholdParameters, MorphologicalParameters morphologicalParameters);
}
