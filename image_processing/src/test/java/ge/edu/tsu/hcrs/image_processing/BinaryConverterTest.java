package ge.edu.tsu.hcrs.image_processing;

import ge.edu.tsu.hcrs.image_processing.opencv.BinaryConverter;
import ge.edu.tsu.hcrs.image_processing.opencv.MorphologicalOperations;
import ge.edu.tsu.hcrs.image_processing.opencv.NoiseRemover;
import ge.edu.tsu.hcrs.image_processing.opencv.parameter.blurring.GaussianBlurParams;
import ge.edu.tsu.hcrs.image_processing.opencv.parameter.morphological.DilationParams;
import ge.edu.tsu.hcrs.image_processing.opencv.parameter.morphological.ErosionParams;
import ge.edu.tsu.hcrs.image_processing.opencv.parameter.threshold.AdaptiveThresholdParams;
import ge.edu.tsu.hcrs.image_processing.opencv.parameter.threshold.OtsuBinarizationParams;
import ge.edu.tsu.hcrs.image_processing.opencv.parameter.threshold.SimpleThresholdParams;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;
import org.junit.BeforeClass;
import org.junit.Test;

public class BinaryConverterTest {

	private static String srcImagePath;

	private static String simpleThresholdPath;

	private static String adaptiveThresholdPath;

	private static String otsuPath;

	@BeforeClass
	public static void init() {
		srcImagePath = "test_images/binaryconverter/text.jpg";
		simpleThresholdPath = "test_images/binaryconverter/r_text_simple.jpg";
		adaptiveThresholdPath = "test_images/binaryconverter/r_text_adaptive.jpg";
		otsuPath = "test_images/binaryconverter/r_text_otsu.jpg";
	}

	@Test
	public void applyNoiseRemovalTest() {
		opencv_core.Mat resultMat;
		opencv_core.Mat srcMat = opencv_imgcodecs.imread(srcImagePath, opencv_core.CV_8UC1);
		opencv_core.Mat mat = NoiseRemover.applyNoiseRemoval(srcMat, new GaussianBlurParams(), 3);
		resultMat = BinaryConverter.applyThreshold(mat, new SimpleThresholdParams());
		opencv_imgcodecs.imwrite(simpleThresholdPath, resultMat);
		resultMat = BinaryConverter.applyThreshold(mat, new AdaptiveThresholdParams());
		opencv_imgcodecs.imwrite(adaptiveThresholdPath, resultMat);
		resultMat = BinaryConverter.applyThreshold(mat, new OtsuBinarizationParams());
		resultMat = MorphologicalOperations.applyErosion(resultMat, new ErosionParams(), true, 1);
		resultMat = MorphologicalOperations.applyDilation(resultMat, new DilationParams(), true, 1);
		opencv_imgcodecs.imwrite(otsuPath, resultMat);
	}
}
