package ge.edu.tsu.hrs.image_processing.opencv.operation;

import ge.edu.tsu.hrs.image_processing.util.OpenCVUtil;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;

public class LinesRemover2 {

	public static void main(String[] args) {
		String fileName = "C:\\dev\\HandwritingRecognitionSystem\\image_processing\\test_images\\lineremover\\1.jpg";
		opencv_core.IplImage src = opencv_imgcodecs.cvLoadImage(fileName, 0);
		opencv_core.IplImage dst;
		opencv_core.IplImage colorDst;
		opencv_core.CvMemStorage storage = opencv_core.cvCreateMemStorage(0);
		opencv_core.CvSeq lines = new opencv_core.CvSeq();

		if (src == null) {
			System.out.println("Couldn't load source image.");
			return;
		}

		dst = opencv_core.cvCreateImage(opencv_core.cvGetSize(src), src.depth(), 1);
		colorDst = opencv_core.cvCreateImage(opencv_core.cvGetSize(src), src.depth(), 3);

		opencv_imgproc.cvCanny(src, dst, 50, 200, 3);
		opencv_imgproc.cvCvtColor(dst, colorDst, opencv_imgproc.CV_GRAY2BGR);

		/*
		  * apply the probabilistic hough transform
          * which returns for each line deteced two points ((x1, y1); (x2,y2))
          * defining the detected segment
          */
		if (args.length == 2 && args[1].contentEquals("probabilistic")) {
			System.out.println("Using the Probabilistic Hough Transform");
			lines = opencv_imgproc.cvHoughLines2(dst, storage, opencv_imgproc.CV_HOUGH_PROBABILISTIC, 1, Math.PI / 180, 40);
			for (int i = 0; i <= lines.total(); i++) {
				// from JavaCPP, the equivalent of the C code:
				// CvPoint* line = (CvPoint*)cvGetSeqElem(lines,i);
				// CvPoint first=line[0], second=line[1]
				// is:
				// CvPoint first=line.position(0), secon=line.position(1);

				Pointer line = opencv_core.cvGetSeqElem(lines, i);
				opencv_core.CvPoint pt1 = new opencv_core.CvPoint(line).position(0);
				opencv_core.CvPoint pt2 = new opencv_core.CvPoint(line).position(1);

				System.out.println("Line spotted: ");
				System.out.println("\t pt1: " + pt1);
				System.out.println("\t pt2: " + pt2);
				opencv_imgproc.cvLine(colorDst, pt1, pt2, org.bytedeco.javacpp.helper.opencv_core.CV_RGB(255, 0, 0), 3, opencv_imgproc.CV_AA, 0); // draw the segment on the image
			}
		}
		/*
          * Apply the multiscale hough transform which returns for each line two float parameters (rho, theta)
          * rho: distance from the origin of the image to the line
          * theta: angle between the x-axis and the normal line of the detected line
          */
		else if (args.length == 2 && args[1].contentEquals("multiscale")) {
			System.out.println("Using the multiscale Hough Transform"); //
			lines = opencv_imgproc.cvHoughLines2(dst, storage, opencv_imgproc.CV_HOUGH_MULTI_SCALE, 1, Math.PI / 180, 40);
			for (int i = 0; i < lines.total(); i++) {
				opencv_core.CvPoint2D32f point = new opencv_core.CvPoint2D32f(opencv_core.cvGetSeqElem(lines, i));

				float rho = point.x();
				float theta = point.y();

				double a = Math.cos(theta), b = Math.sin(theta);
				double x0 = a * rho, y0 = b * rho;
				opencv_core.CvPoint pt1 = new opencv_core.CvPoint((int) Math.round(x0 + 1000 * (-b)), (int) Math.round(y0 + 1000 * (a))), pt2 = new opencv_core.CvPoint((int) Math.round(x0 - 1000 * (-b)), (int) Math.round(y0 - 1000 * (a)));
				System.out.println("Line spoted: ");
				System.out.println("\t rho= " + rho);
				System.out.println("\t theta= " + theta);
				opencv_imgproc.cvLine(colorDst, pt1, pt2, org.bytedeco.javacpp.helper.opencv_core.CV_RGB(255, 0, 0), 3, opencv_imgproc.CV_AA, 0);
			}
		}
		/*
          * Default: apply the standard hough transform. Outputs: same as the multiscale output.
          */
		else {
			System.out.println("Using the Standard Hough Transform");
			lines = opencv_imgproc.cvHoughLines2(dst, storage, opencv_imgproc.CV_HOUGH_STANDARD, 1, Math.PI / 180, 90);
			for (int i = 0; i < lines.total(); i++) {
				opencv_core.CvPoint2D32f point = new opencv_core.CvPoint2D32f(opencv_core.cvGetSeqElem(lines, i));

				float rho = point.x();
				float theta = point.y();

				double a = Math.cos(theta), b = Math.sin(theta);
				double x0 = a * rho, y0 = b * rho;
				opencv_core.CvPoint pt1 = new opencv_core.CvPoint((int) Math.round(x0 + 1000 * (-b)), (int) Math.round(y0 + 1000 * (a))), pt2 = new opencv_core.CvPoint((int) Math.round(x0 - 1000 * (-b)), (int) Math.round(y0 - 1000 * (a)));
				System.out.println("Line spotted: ");
				System.out.println("\t rho= " + rho);
				System.out.println("\t theta= " + theta);
				opencv_imgproc.cvLine(colorDst, pt1, pt2, org.bytedeco.javacpp.helper.opencv_core.CV_RGB(255, 0, 0), 3, opencv_imgproc.CV_AA, 0);
			}
		}

		OpenCVUtil.saveImage(colorDst, "C:\\dev\\HandwritingRecognitionSystem\\image_processing\\test_images\\lineremover\\rame.png");
	}
}
