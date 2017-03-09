package ge.edu.tsu.hcrs.control_panel.server.processor;

import ge.edu.tsu.hcrs.image_processing.ImageProcessingManager;
import ge.edu.tsu.hcrs.image_processing.ImageProcessingManagerImpl;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Runner {

	public static void main(String[] args) throws IOException {
		ImageProcessingManager imageProcessingManager = new ImageProcessingManagerImpl();
		BufferedImage srcImage = ImageIO.read(new File("C:\\dev\\HandwritingRecognitionSystem\\test_images\\src_images\\asi.JPG"));
		BufferedImage bufferedImage = imageProcessingManager.resizeImage(srcImage, true, 2, 2);
		ImageIO.write(bufferedImage, "jpg", new File("C:\\dev\\HandwritingRecognitionSystem\\test_images\\src_images\\asi1.JPG"));
	}
}
