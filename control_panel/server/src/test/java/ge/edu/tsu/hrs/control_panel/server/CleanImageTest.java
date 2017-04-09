package ge.edu.tsu.hrs.control_panel.server;

import ge.edu.tsu.hrs.control_panel.server.processor.imageprocessing.ImageProcessingProcessor;
import ge.edu.tsu.hrs.image_processing.opencv.service.condition.ImageCondition;
import org.junit.Ignore;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CleanImageTest {

    private static final String srcPath = "D:\\Bachelor Project\\HandwrittenRecognitionSystem\\test_directory\\images\\2.jpg";

    private static final String resultPath = "D:\\Bachelor Project\\HandwrittenRecognitionSystem\\test_directory\\images\\result\\1_r.PNG";

    private ImageProcessingProcessor imageProcessingProcessor = new ImageProcessingProcessor();

    @Test
    @Ignore
    public void testCleanImage() {
        try {
            BufferedImage srcImage = ImageIO.read(new File(srcPath));
            BufferedImage resultImage = imageProcessingProcessor.cleanImage(srcImage, new ImageCondition());
            ImageIO.write(resultImage, "png", new File(resultPath));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
