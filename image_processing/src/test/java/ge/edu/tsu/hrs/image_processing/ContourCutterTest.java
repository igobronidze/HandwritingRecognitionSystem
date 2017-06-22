package ge.edu.tsu.hrs.image_processing;

import ge.edu.tsu.hrs.image_processing.opencv.operation.ContourCutter;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.junit.Ignore;
import org.junit.Test;

public class ContourCutterTest {

	private final String SRC_IMAGE_PATH = "test_images/binaryconverter/apple.jpg";
	private final String RESULT_IMAGE_PATH = "test_images/binaryconverter/r_apple1.jpg";

	@Test
	@Ignore
	public void cutContourTest() {
		opencv_core.Mat resultMat;
		opencv_core.Mat srcMat = opencv_imgcodecs.imread(SRC_IMAGE_PATH);
		resultMat = ContourCutter.cutContour(srcMat);
		opencv_imgcodecs.imwrite(RESULT_IMAGE_PATH, resultMat);
	}
}
