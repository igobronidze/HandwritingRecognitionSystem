package ge.edu.tsu.hrs.image_processing.opencv.operation;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;

public class ContourCutter {

	public static opencv_core.Mat cutContour(opencv_core.Mat img0) {
		opencv_core.Mat img1 = new opencv_core.Mat();
		opencv_imgproc.cvtColor(img0, img1, opencv_imgproc.CV_RGB2GRAY);
		opencv_imgproc.Canny(img1, img1, 100, 200);
		opencv_core.MatVector contours = new opencv_core.MatVector();
		opencv_imgproc.findContours(img1, contours, opencv_imgproc.CV_RETR_EXTERNAL, opencv_imgproc.CV_CHAIN_APPROX_NONE);
		opencv_core.Mat mask = new opencv_core.Mat(img1.rows(), img1.cols(), opencv_core.CV_8UC3);
		opencv_imgproc.drawContours(mask, contours, -1, new opencv_core.Scalar(0, 128, 0, 100), -1, 8, null, 0, null);
		opencv_core.Mat crop = new opencv_core.Mat(img0.rows(), img0.cols(), opencv_core.CV_8UC3);
		crop.setTo(new opencv_core.Mat(new opencv_core.Scalar(0, 255, 0, 100)));
		img0.copyTo(crop, mask);
		opencv_core.normalize(mask.clone(), mask, 0.0, 255.0, opencv_core.CV_MINMAX, opencv_core.CV_8UC1, new opencv_core.Mat());
		return crop;
	}
}
