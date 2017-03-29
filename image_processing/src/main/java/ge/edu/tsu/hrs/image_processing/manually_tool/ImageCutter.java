package ge.edu.tsu.hrs.image_processing.manually_tool;

import java.awt.image.BufferedImage;

public class ImageCutter {

    public static BufferedImage cutCornerUnusedParts(BufferedImage image, int unusedRGB) {
        int width = image.getWidth();
        int height = image.getHeight();
        int x = width, y = height, w = 0, h = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (image.getRGB(i, j) != unusedRGB) {
                    x = Math.min(x, i);
                    y = Math.min(y, j);
                    w = Math.max(w, i);
                    h = Math.max(h, j);
                }
            }
        }
        return image.getSubimage(x, y, w - x, h - y);
    }
}
