package ge.edu.tsu.hcrs.image_processing.opencv;

import ge.edu.tsu.hcrs.image_processing.opencv.parameter.blurring.BilateralFilterParams;
import ge.edu.tsu.hcrs.image_processing.opencv.parameter.blurring.BlurParams;
import ge.edu.tsu.hcrs.image_processing.opencv.parameter.blurring.BlurringParams;
import ge.edu.tsu.hcrs.image_processing.opencv.parameter.blurring.GaussianBlurParams;
import ge.edu.tsu.hcrs.image_processing.opencv.parameter.blurring.MedianBlurParams;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;

public class NoiseRemover {

	public static opencv_core.Mat applyNoiseRemoval(opencv_core.Mat srcMat, BlurringParams blurringParams) {
		opencv_core.Mat resultMat = new opencv_core.Mat();
		if (blurringParams instanceof BlurParams) {
			BlurParams blurParams = (BlurParams) blurringParams;
			opencv_imgproc.blur(srcMat, resultMat, new opencv_core.Size(blurParams.getkSizeWidth(), blurParams.getkSizeHeight()));
		} else if (blurringParams instanceof GaussianBlurParams) {
			GaussianBlurParams gaussianBlurParams = (GaussianBlurParams) blurringParams;
			opencv_imgproc.GaussianBlur(srcMat, resultMat, new opencv_core.Size(gaussianBlurParams.getkSizeWidth(), gaussianBlurParams.getkSizeHeight()),
					gaussianBlurParams.getSigmaX(), gaussianBlurParams.getSigmaY(), gaussianBlurParams.getBorderType());
		} else if (blurringParams instanceof MedianBlurParams) {
			MedianBlurParams medianBlurParams = (MedianBlurParams) blurringParams;
			opencv_imgproc.medianBlur(srcMat, resultMat, medianBlurParams.getkSize());
		} else if (blurringParams instanceof BilateralFilterParams) {
			BilateralFilterParams bilateralFilterParams = (BilateralFilterParams) blurringParams;
			opencv_imgproc.bilateralFilter(srcMat, resultMat, bilateralFilterParams.getDiameter(), bilateralFilterParams.getSigmaColor(), bilateralFilterParams.getSigmaSpace());
		}
		return resultMat;
	}
}
