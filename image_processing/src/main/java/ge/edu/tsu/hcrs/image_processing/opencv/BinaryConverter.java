package ge.edu.tsu.hcrs.image_processing.opencv;

import ge.edu.tsu.hcrs.image_processing.opencv.parameter.threshold.AdaptiveThresholdParams;
import ge.edu.tsu.hcrs.image_processing.opencv.parameter.threshold.SimpleThresholdParams;
import ge.edu.tsu.hcrs.image_processing.opencv.parameter.threshold.ThresholdParams;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;

public class BinaryConverter {

	public static opencv_core.Mat applyThreshold(opencv_core.Mat srcMat, ThresholdParams params) {
		opencv_core.Mat resultMat = new opencv_core.Mat();
		// TODO[IG] ეს რა არის გასარკვევია
		double retval = 0.0;
		if (params instanceof SimpleThresholdParams) {
			SimpleThresholdParams simpleThresholdParams = (SimpleThresholdParams) params;
			retval = opencv_imgproc.threshold(srcMat, resultMat, simpleThresholdParams.getThresh(), simpleThresholdParams.getMaxValue(), simpleThresholdParams.getType());
		} else if (params instanceof AdaptiveThresholdParams) {
			AdaptiveThresholdParams adaptiveThresholdParams = (AdaptiveThresholdParams) params;
			retval = opencv_imgproc.adaptiveThreshold(srcMat, resultMat);
		}
	}
}
