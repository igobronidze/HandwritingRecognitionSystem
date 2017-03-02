package ge.edu.tsu.hcrs.image_processing;

import ge.edu.tsu.hcrs.image_processing.opencv.ContourCutter;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.junit.Test;

public class ContourCutterTest {

	private String srcImagePath = "test_images/binaryconverter/apple.jpg";
	private String resultImagePath = "test_images/binaryconverter/r_apple1.jpg";

	@Test
	public void cutContourTest() {
		opencv_core.Mat resultMat;
		opencv_core.Mat srcMat = opencv_imgcodecs.imread(srcImagePath);
		resultMat = ContourCutter.cutContour(srcMat);
		opencv_imgcodecs.imwrite(resultImagePath, resultMat);
	}
}
