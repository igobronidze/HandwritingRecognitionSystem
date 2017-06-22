package ge.edu.tsu.hrs.image_processing;

import ge.edu.tsu.hrs.image_processing.opencv.operation.ContoursDetector;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.junit.Ignore;
import org.junit.Test;

public class ContoursDetectorTest {

	private final String SRC_IMAGE_PATH = "test_images/binaryconverter/r_text_otsu.jpg";
	private final String RESULT_IMAGE_PATH = "test_images/binaryconverter/tt.jpg";

	@Test
	@Ignore
	public void findContoursTest() {
		opencv_core.Mat resultMat;
		opencv_core.Mat srcMat = opencv_imgcodecs.imread(SRC_IMAGE_PATH);
		resultMat = ContoursDetector.findContours(srcMat);
		opencv_imgcodecs.imwrite(RESULT_IMAGE_PATH, resultMat);
	}
}
