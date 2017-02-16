package ge.edu.tsu.hcrs.image_processing;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;

import java.io.File;

public class JavaCVMain {

	private static String imageName = "sss.jpg";

	private static String imagePath = "resources\\test_images\\" + imageName;

	private static double d = 15.0;

	public static void main(String[] args) {
		File file = new File(imagePath);
		System.out.println(file.getAbsolutePath());
		opencv_core.IplImage image = opencv_imgcodecs.cvLoadImage(file.getAbsolutePath());
		opencv_core.IplImage grayImage = opencv_core.cvCreateImage(opencv_core.cvGetSize(image), opencv_core.IPL_DEPTH_8U, 1);

		opencv_imgproc.cvCvtColor(image, grayImage, opencv_imgproc.CV_BGR2GRAY);

		OpenCVFrameConverter.ToMat converterToMat = new OpenCVFrameConverter.ToMat();
		Frame grayImageFrame = converterToMat.convert(grayImage);
		opencv_core.Mat grayImageMat = converterToMat.convertToMat(grayImageFrame);

		opencv_imgproc.GaussianBlur(grayImageMat, grayImageMat, new opencv_core.Size(5, 5), 0.0, 0.0, opencv_core.BORDER_DEFAULT);

		image = converterToMat.convertToIplImage(grayImageFrame);

		opencv_imgproc.cvErode(image, image);
		opencv_imgproc.cvDilate(image, image);

		opencv_imgproc.cvCanny(image, image, d, 200.0);

		saveImage(image);
	}

	private static final void saveImage(opencv_core.IplImage image) {
		File file = new File("resources\\result_images\\" + imageName + d + ".jpg");
		opencv_imgcodecs.cvSaveImage(file.getAbsolutePath() , image);
	}

}
