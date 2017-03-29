package ge.edu.tsu.hrs.image_processing.opencv;

import ge.edu.tsu.hrs.image_processing.opencv.parameter.threshold.AdaptiveThresholdParams;
import ge.edu.tsu.hrs.image_processing.opencv.parameter.threshold.OtsuBinarizationParams;
import ge.edu.tsu.hrs.image_processing.opencv.parameter.threshold.SimpleThresholdParams;
import ge.edu.tsu.hrs.image_processing.opencv.parameter.threshold.ThresholdParams;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;

public class BinaryConverter {

	public static opencv_core.Mat applyThreshold(opencv_core.Mat srcMat, ThresholdParams params) {
		opencv_core.Mat resultMat = new opencv_core.Mat();
		if (params instanceof SimpleThresholdParams) {
			SimpleThresholdParams simpleThresholdParams = (SimpleThresholdParams) params;
			opencv_imgproc.threshold(srcMat, resultMat, simpleThresholdParams.getThresh(), simpleThresholdParams.getMaxValue(), simpleThresholdParams.getType());
		} else if (params instanceof AdaptiveThresholdParams) {
			AdaptiveThresholdParams adaptiveThresholdParams = (AdaptiveThresholdParams) params;
			opencv_imgproc.adaptiveThreshold(srcMat, resultMat, adaptiveThresholdParams.getMaxValue(), adaptiveThresholdParams.getAdaptiveMethod(),
					adaptiveThresholdParams.getThresholdType(), adaptiveThresholdParams.getBlockSize(), adaptiveThresholdParams.getC());
		} else if (params instanceof OtsuBinarizationParams) {
			OtsuBinarizationParams otsuBinarizationParams = (OtsuBinarizationParams) params;
			opencv_imgproc.threshold(srcMat, resultMat, otsuBinarizationParams.getThresh(), otsuBinarizationParams.getMaxValue(), otsuBinarizationParams.getType());
		}
		return resultMat;
	}
}
