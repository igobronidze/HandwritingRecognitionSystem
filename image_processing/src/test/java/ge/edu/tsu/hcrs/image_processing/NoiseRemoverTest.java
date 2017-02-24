package ge.edu.tsu.hcrs.image_processing;

import ge.edu.tsu.hcrs.image_processing.opencv.NoiseRemover;
import ge.edu.tsu.hcrs.image_processing.opencv.parameter.blurring.GaussianBlurParams;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.junit.BeforeClass;
import org.junit.Test;

public class NoiseRemoverTest {

	private static String srcImagePath;

	private static String resultImagePath;

	@BeforeClass
	public static void init() {
		srcImagePath = "text.jpg";
		resultImagePath = "r_text.jpg";
	}

	@Test
	public void applyNoiseRemovalTest() {
		opencv_core.Mat srcMat = opencv_imgcodecs.imread(srcImagePath);
		opencv_core.Mat resultMat = NoiseRemover.applyNoiseRemoval(srcMat, new GaussianBlurParams());
		for (int i = 0; i < 100; i++) {
			resultMat = NoiseRemover.applyNoiseRemoval(resultMat, new GaussianBlurParams());
			resultMat = NoiseRemover.applyNoiseRemoval(resultMat, new GaussianBlurParams());
			resultMat = NoiseRemover.applyNoiseRemoval(resultMat, new GaussianBlurParams());
		}
		opencv_imgcodecs.imwrite(resultImagePath, resultMat);
	}
}
