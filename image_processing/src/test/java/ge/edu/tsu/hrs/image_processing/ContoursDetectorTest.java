package ge.edu.tsu.hrs.image_processing;

import ge.edu.tsu.hrs.image_processing.opencv.operation.ContoursDetector;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.junit.Ignore;
import org.junit.Test;

public class ContoursDetectorTest {

	private String srcImagePath = "test_images/binaryconverter/r_text_otsu.jpg";
	private String resultImagePath = "test_images/binaryconverter/tt.jpg";

	@Test
	@Ignore
	public void findContoursTest() {
		opencv_core.Mat resultMat;
		opencv_core.Mat srcMat = opencv_imgcodecs.imread(srcImagePath);
		resultMat = ContoursDetector.findContours(srcMat);
		opencv_imgcodecs.imwrite(resultImagePath, resultMat);
	}
}
