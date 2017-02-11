package ge.edu.tsu.hcrs.control_panel.service.imageprocessing;

import ge.edu.tsu.hcrs.image_processing.ImageCutter;
import ge.edu.tsu.hcrs.image_processing.ImageTransformer;

import java.awt.image.BufferedImage;

public class ImageProcessingServiceImpl implements ImageProcessingService {

    @Override
    public BufferedImage cutCornerUnusedParts(BufferedImage image, int unusedRGB) {
        return ImageCutter.cutCornerUnusedParts(image, unusedRGB);
    }

    @Override
    public Float[] getFloatArrayFromImage(BufferedImage image, int gridWidth, int gridHeight, int unusedRGB) {
        return ImageTransformer.getFloatArrayFromImage(image, gridWidth, gridHeight, unusedRGB);
    }
}
