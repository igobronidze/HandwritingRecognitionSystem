package ge.edu.tsu.hrs.image_processing.opencv;

import ge.edu.tsu.hrs.image_processing.opencv.parameter.blurring.BilateralFilterParams;
import ge.edu.tsu.hrs.image_processing.opencv.parameter.blurring.BlurParams;
import ge.edu.tsu.hrs.image_processing.opencv.parameter.blurring.BlurringParams;
import ge.edu.tsu.hrs.image_processing.opencv.parameter.blurring.GaussianBlurParams;
import ge.edu.tsu.hrs.image_processing.opencv.parameter.blurring.MedianBlurParams;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;

public class NoiseRemover {

	public static opencv_core.Mat applyNoiseRemoval(opencv_core.Mat srcMat, BlurringParams blurringParams, int iteration) {
		opencv_core.Mat resultMat = new opencv_core.Mat();
//		opencv_imgproc.cvtColor(srcMat, srcMat, opencv_imgproc.CV_BGR2GRAY);
		if (blurringParams instanceof BlurParams) {
			BlurParams blurParams = (BlurParams) blurringParams;
			if (iteration > 0) {
				opencv_imgproc.blur(srcMat, resultMat, new opencv_core.Size(blurParams.getkSizeWidth(), blurParams.getkSizeHeight()));
			}
			for (int i = 1; i < iteration; i++) {
				opencv_imgproc.blur(resultMat, resultMat, new opencv_core.Size(blurParams.getkSizeWidth(), blurParams.getkSizeHeight()));
			}
		} else if (blurringParams instanceof GaussianBlurParams) {
			GaussianBlurParams gaussianBlurParams = (GaussianBlurParams) blurringParams;
			if (iteration > 0) {
				opencv_imgproc.GaussianBlur(srcMat, resultMat, new opencv_core.Size(gaussianBlurParams.getkSizeWidth(), gaussianBlurParams.getkSizeHeight()),
						gaussianBlurParams.getSigmaX(), gaussianBlurParams.getSigmaY(), gaussianBlurParams.getBorderType());
			}
			for (int i = 1; i < iteration; i++) {
				opencv_imgproc.GaussianBlur(resultMat, resultMat, new opencv_core.Size(gaussianBlurParams.getkSizeWidth(), gaussianBlurParams.getkSizeHeight()),
						gaussianBlurParams.getSigmaX(), gaussianBlurParams.getSigmaY(), gaussianBlurParams.getBorderType());
			}
		} else if (blurringParams instanceof MedianBlurParams) {
			MedianBlurParams medianBlurParams = (MedianBlurParams) blurringParams;
			if (iteration > 0) {
				opencv_imgproc.medianBlur(srcMat, resultMat, medianBlurParams.getkSize());
			}
			for (int i = 1; i < iteration; i++) {
				opencv_imgproc.medianBlur(resultMat, resultMat, medianBlurParams.getkSize());
			}
		} else if (blurringParams instanceof BilateralFilterParams) {
			BilateralFilterParams bilateralFilterParams = (BilateralFilterParams) blurringParams;
			if (iteration > 0) {
				opencv_imgproc.bilateralFilter(srcMat, resultMat, bilateralFilterParams.getDiameter(), bilateralFilterParams.getSigmaColor(), bilateralFilterParams.getSigmaSpace());
			}
			for (int i = 1; i < iteration; i++) {
				opencv_imgproc.bilateralFilter(resultMat, resultMat, bilateralFilterParams.getDiameter(), bilateralFilterParams.getSigmaColor(), bilateralFilterParams.getSigmaSpace());
			}
		}
		return resultMat;
	}
}
