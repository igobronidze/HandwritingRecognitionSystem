package ge.edu.tsu.hrs.control_panel.server;

import ge.edu.tsu.hrs.control_panel.server.processor.imageprocessing.ImageProcessingProcessor;
import ge.edu.tsu.hrs.image_processing.opencv.service.condition.BackgroundCondition;
import ge.edu.tsu.hrs.image_processing.opencv.service.condition.ImageCondition;
import ge.edu.tsu.hrs.image_processing.opencv.service.condition.NoiseCondition;
import org.junit.Ignore;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CleanImageTest {

    private static final String srcPath = "C:\\dev\\HandwritingRecognitionSystem\\test_directory\\images\\";

    private static final String resultPath = "C:\\dev\\HandwritingRecognitionSystem\\test_directory\\images\\result\\";

    private ImageProcessingProcessor imageProcessingProcessor = new ImageProcessingProcessor();

    @Test
    @Ignore
    public void testCleanImage() {
        try {
            for (int i = 11; i <= 11; i++) {
                BufferedImage srcImage = ImageIO.read(new File(srcPath + i + ".png"));
                ImageCondition imageCondition = new ImageCondition();
                imageCondition.setNoiseCondition(NoiseCondition.FEW_NOISE);
                imageCondition.setBackgroundCondition(BackgroundCondition.MONOTONOUS);
                BufferedImage resultImage = imageProcessingProcessor.cleanImage(srcImage, imageCondition);
                ImageIO.write(resultImage, "png", new File(resultPath + i + "_r_r.png"));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
