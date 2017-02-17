package ge.edu.tsu.hcrs.image_processing;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;

import java.io.File;

public class Runner {

	private static final String imageName = "receipt.jpg";

	private static final String imagePath = "resources\\test_images\\" + imageName;

	private static final int percent = 50;

	public static void main(String[] args) {
		File file = new File(imagePath);
		opencv_core.IplImage realImage =JavaCVProcessing.loadImage(file.getAbsolutePath());
		opencv_core.IplImage image1 = JavaCVProcessing.downScaleImage(realImage, percent);
		opencv_core.IplImage image2 = JavaCVProcessing.applyCannySquareEdgeDetectionOnImage(image1);
		opencv_core.CvSeq cvSeq = JavaCVProcessing.findLargestSquareOnCannyDetectedImage(image2);
		opencv_core.IplImage image3 = JavaCVProcessing.applyPerspectiveTransformThresholdOnOriginalImage(realImage, cvSeq, percent);
		opencv_core.IplImage image4 = JavaCVProcessing.cleanImageSmoothingForOCR(image3);
		File file1 = new File("resources\\result_images\\" + "rame" + ".jpg");
		opencv_imgcodecs.cvSaveImage(file1.getAbsolutePath() , image4);
	}
}
