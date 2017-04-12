package ge.edu.tsu.hrs.image_processing.opencv.service;

import ge.edu.tsu.hrs.image_processing.opencv.operation.BinaryConverter;
import ge.edu.tsu.hrs.image_processing.opencv.operation.MorphologicalOperations;
import ge.edu.tsu.hrs.image_processing.opencv.operation.NoiseRemover;
import ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.blurring.GaussianBlurParams;
import ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.morphological.DilationParams;
import ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.morphological.ErosionParams;
import ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.threshold.AdaptiveThresholdParams;
import ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.threshold.SimpleThresholdParams;
import ge.edu.tsu.hrs.image_processing.opencv.service.condition.BackgroundCondition;
import ge.edu.tsu.hrs.image_processing.opencv.service.condition.ImageCondition;
import ge.edu.tsu.hrs.image_processing.opencv.service.condition.NoiseCondition;
import ge.edu.tsu.hrs.image_processing.opencv.service.condition.Stridency;
import ge.edu.tsu.hrs.image_processing.util.OpenCVUtil;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;

import java.awt.image.BufferedImage;

public class ImageCleaner {

	public static BufferedImage cleanImage(BufferedImage srcImage, ImageCondition imageCondition) {
		opencv_core.Mat mat = OpenCVUtil.bufferedImageToMat(srcImage);
		if (mat.type() != opencv_core.CV_8UC1) {
			opencv_imgproc.cvtColor(mat, mat, opencv_imgproc.CV_RGB2GRAY);
		}
		if (imageCondition.getBackgroundCondition() == BackgroundCondition.MONOTONOUS) {
			mat = removeNoise(mat, imageCondition.getNoiseCondition());
			mat = BinaryConverter.applyThreshold(mat, new AdaptiveThresholdParams());
		} else if (imageCondition.getBackgroundCondition() == BackgroundCondition.NOT_MONOTONOUS) {
			mat = removeNoise(mat, imageCondition.getNoiseCondition());
			mat = BinaryConverter.applyThreshold(mat, new SimpleThresholdParams());
			return OpenCVUtil.matToBufferedImage(mat);
		}
		if (imageCondition.getStridency() == Stridency.DIM) {
			mat = MorphologicalOperations.applyErosion(mat, new ErosionParams(), true, 1);
			mat = MorphologicalOperations.applyDilation(mat, new DilationParams(), true, 1);
		}
		return OpenCVUtil.matToBufferedImage(mat);
	}

	private static opencv_core.Mat removeNoise(opencv_core.Mat mat, NoiseCondition noiseCondition) {
		switch (noiseCondition) {
			case NO_NOISE:
				return mat;
			case FEW_NOISE:
				return NoiseRemover.applyNoiseRemoval(mat, new GaussianBlurParams(), 1);
			case SOME_NOISE:
				return NoiseRemover.applyNoiseRemoval(mat, new GaussianBlurParams(), 3);
			case LOT_NOISE:
				return NoiseRemover.applyNoiseRemoval(mat, new GaussianBlurParams(), 5);
			case UNKNOWN:
			default:
				return mat;
		}
	}
}
