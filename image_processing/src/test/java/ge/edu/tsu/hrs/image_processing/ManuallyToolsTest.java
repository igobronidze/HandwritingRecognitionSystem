package ge.edu.tsu.hrs.image_processing;

import ge.edu.tsu.hrs.image_processing.manually_tool.ImageCutter;
import ge.edu.tsu.hrs.image_processing.manually_tool.ImageTransformer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ManuallyToolsTest {

    private BufferedImage image;

    private int unusedRGB;

    @Before
    @Ignore
    public void init() {
        try {
            image = ImageIO.read(new File("D:\\sg\\handwriting_recognition\\images\\created_images\\rame\\k_85122541.png"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        unusedRGB = -1;
    }

    @Test
    @Ignore
    public void testCutCornerUnusedParts() {
        BufferedImage newImage = ImageCutter.cutCornerUnusedParts(image, unusedRGB);
        try {
            ImageIO.write(newImage, "png", new File("D:\\sg\\handwriting_recognition\\images\\created_images\\rame\\test.png"));
        } catch (IOException ex) {

        }
        Float[] output = ImageTransformer.getFloatArrayFromImage(newImage, 15, 15, -1);
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print((output[i * 15 + j] == 0.0f ? 0 : 1) + " ");
            }
            System.out.println();
        }
    }
}
