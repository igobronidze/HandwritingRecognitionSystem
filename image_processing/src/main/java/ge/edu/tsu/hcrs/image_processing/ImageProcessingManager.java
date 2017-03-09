package ge.edu.tsu.hcrs.image_processing;

import java.awt.image.BufferedImage;

public interface ImageProcessingManager {

	BufferedImage resizeImage(BufferedImage srcImage, boolean scaleResizing, double x, double y);
}
