package ge.edu.tsu.hrs.image_processing.manually_tool;

import java.awt.image.BufferedImage;

public class ImageTransformer {

    public static Float[] getFloatArrayFromImage(BufferedImage image, int gridWidth, int gridHeight, int unusedRGB) {
        Float[] output = new Float[gridWidth * gridHeight];
        int partWidth = image.getWidth() / gridWidth;
        int partHeight = image.getHeight() / gridHeight;
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                boolean isEmpty = true;
                for (int ii = i * partHeight; ii < ((i == gridHeight - 1) ? imageHeight : (i + 1) * partHeight); ii++) {
                    for (int jj = j * partWidth; jj < ((j == gridWidth - 1) ? imageWidth : (j + 1) * partWidth); jj++) {
                        if (image.getRGB(jj, ii) != unusedRGB) {
                            isEmpty = false;
                        }
                    }
                }
                output[i * gridWidth + j] =(isEmpty ? 0.0f : 1.0f);
            }
        }
        return output;
    }
}
