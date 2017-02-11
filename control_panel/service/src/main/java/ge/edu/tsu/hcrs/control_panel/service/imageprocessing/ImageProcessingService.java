package ge.edu.tsu.hcrs.control_panel.service.imageprocessing;

import java.awt.image.BufferedImage;

public interface ImageProcessingService {

    BufferedImage cutCornerUnusedParts(BufferedImage image, int unusedRGB);

    Float[] getFloatArrayFromImage(BufferedImage image, int gridWidth, int gridHeight, int unusedRGB);
}
