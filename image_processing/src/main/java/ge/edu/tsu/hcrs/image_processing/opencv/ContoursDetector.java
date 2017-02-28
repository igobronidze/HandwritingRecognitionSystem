package ge.edu.tsu.hcrs.image_processing.opencv;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;

public class ContoursDetector {

	private static String resultImagePath = "test_images/binaryconverter/r_text";

	public static opencv_core.Mat findContours(opencv_core.Mat srcMat) {
		opencv_core.Mat grayMat = new opencv_core.Mat();
		opencv_core.Mat resultMat = new opencv_core.Mat();
		opencv_core.MatVector contours = new opencv_core.MatVector();
		opencv_core.Mat hierarchy = new opencv_core.Mat();
		opencv_imgproc.cvtColor(srcMat, grayMat, opencv_imgproc.CV_BGR2GRAY);
		opencv_imgproc.blur(grayMat, grayMat, new opencv_core.Size(3, 3));
		opencv_imgproc.Canny(grayMat, resultMat, 100, 200, 3, true);
		opencv_imgproc.findContours(resultMat, contours, hierarchy, opencv_imgproc.CV_RETR_TREE, opencv_imgproc.CV_CHAIN_APPROX_SIMPLE, new opencv_core.Point(0, 0));

		opencv_core.Mat drawing = new opencv_core.Mat(resultMat.size(), opencv_core.CV_8UC3);
//		opencv_core.Point point = new opencv_core.Point(drawing.cols() / 2, drawing.rows() / 2);
//		int radius = Math.min(drawing.cols(), drawing.rows());
//		opencv_imgproc.circle(drawing, point, radius, new opencv_core.Scalar(255));



		for (int i = 0; i < contours.size(); i++) {
//			opencv_core.Scalar color = new opencv_core.Scalar(0, 255, 0, 255);
//			opencv_imgproc.drawContours(drawing, contours, i, color, 2, 8, hierarchy, 0, new opencv_core.Point());



//			opencv_core.Mat imagePart = new opencv_core.Mat(srcMat.size(), srcMat.type());
//			srcMat.copyTo(imagePart, drawing);
//			opencv_imgcodecs.imwrite(resultImagePath + "_" + i + ".jpg", imagePart);
//			opencv_core.Rect rect = opencv_imgproc.boundingRect(contours.get(i));
//			opencv_core.Mat r = new opencv_core.Mat();
//			opencv_core.inRange(new opencv_core.Mat(rect), new opencv_core.Mat(new opencv_core.Scalar(0)), new opencv_core.Mat(new opencv_core.Scalar(50)), r);
//			opencv_imgcodecs.imwrite(resultImagePath + "_" + i + ".jpg", r);

			opencv_core.Mat mask = new opencv_core.Mat(resultMat.rows(), resultMat.cols(), opencv_core.CV_8UC1);
			opencv_imgproc.drawContours(mask, contours, i, new opencv_core.Scalar(1));
			opencv_core.Mat crop = new opencv_core.Mat(srcMat.rows(), srcMat.cols(), opencv_core.CV_8UC3);
			crop.setTo(new opencv_core.Mat(new opencv_core.Scalar(0, 255, 0, 0)));
			srcMat.copyTo(crop, mask);
			opencv_imgcodecs.imwrite(resultImagePath + "_" + i + ".jpg", mask);
		}
		return drawing;
	}
}
