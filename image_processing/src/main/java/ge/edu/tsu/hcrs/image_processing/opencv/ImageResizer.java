package ge.edu.tsu.hcrs.image_processing.opencv;

import ge.edu.tsu.hcrs.image_processing.opencv.parameter.ImageResizerParams;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;

public class ImageResizer {

	public static opencv_core.Mat resize(opencv_core.Mat srcMat, ImageResizerParams params) {
		opencv_core.Mat resultMat = new opencv_core.Mat();
		opencv_imgproc.resize(srcMat, resultMat, new opencv_core.Size(params.getWidht(), params.getHeight()), params.getFx(), params.getFy(), params.getInterpolation());
		return resultMat;
	}
}
