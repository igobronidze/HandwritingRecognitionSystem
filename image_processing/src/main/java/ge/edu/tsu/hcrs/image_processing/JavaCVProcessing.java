package ge.edu.tsu.hcrs.image_processing;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;

import java.io.File;

public class JavaCVProcessing {

	public static opencv_core.IplImage loadImage(String absPath) {
		return opencv_imgcodecs.cvLoadImage(absPath);
	}

	public static void saveImage(opencv_core.IplImage image, String absPath) {
		opencv_imgcodecs.cvSaveImage(absPath , image);
	}

	public static opencv_core.IplImage downScaleImage(opencv_core.IplImage image, int percent) {
		opencv_core.IplImage destImage = opencv_core.cvCreateImage(opencv_core.cvSize(image.width() * percent / 100,
				image.height() * percent / 100), image.depth(), image.nChannels());
		opencv_imgproc.cvResize(image, destImage);
		return destImage;
	}

	public static opencv_core.IplImage applyCannySquareEdgeDetectionOnImage(opencv_core.IplImage image) {
		opencv_core.IplImage grayImage = opencv_core.cvCreateImage(opencv_core.cvGetSize(image), opencv_core.IPL_DEPTH_8U, 1);

		opencv_imgproc.cvCvtColor(image, grayImage, opencv_imgproc.CV_BGR2GRAY);

		OpenCVFrameConverter.ToMat converterToMat = new OpenCVFrameConverter.ToMat();
		Frame grayImageFrame = converterToMat.convert(grayImage);
		opencv_core.Mat grayImageMat = converterToMat.convertToMat(grayImageFrame);

		opencv_imgproc.GaussianBlur(grayImageMat, grayImageMat, new opencv_core.Size(5, 5), 0.0, 0.0, opencv_core.BORDER_DEFAULT);

		image = converterToMat.convertToIplImage(grayImageFrame);

		opencv_imgproc.cvErode(image, image);
		opencv_imgproc.cvDilate(image, image);

		opencv_imgproc.cvCanny(image, image, 75, 200.0);
		return image;
	}

	public static opencv_core.CvSeq findLargestSquareOnCannyDetectedImage(opencv_core.IplImage cannyEdgeDetectedImage) {

		opencv_core.IplImage foundedContoursImage = opencv_core.cvCloneImage(cannyEdgeDetectedImage);
		opencv_core.CvMemStorage memory = opencv_core.CvMemStorage.create();
		opencv_core.CvSeq contours = new opencv_core.CvSeq();
		opencv_imgproc.cvFindContours(foundedContoursImage, memory, contours, Loader.sizeof(opencv_core.CvContour.class), opencv_imgproc.CV_RETR_LIST,
				opencv_imgproc.CV_CHAIN_APPROX_SIMPLE, opencv_core.cvPoint(0,0));
		int maxWidth = 0;
		int maxHeight = 0;
		opencv_core.CvRect contour;
		opencv_core.CvSeq seqFounded = null;
		opencv_core.CvSeq nextSeq;
		for (nextSeq = contours; nextSeq != null; nextSeq = nextSeq.h_next()) {
			contour = opencv_imgproc.cvBoundingRect(nextSeq, 0);
			if ((contour.width() >= maxWidth) && (contour.height() >= maxHeight)) {
				maxWidth = contour.width();
				maxHeight = contour.height();
				seqFounded = nextSeq;
			}
		}
		opencv_core.CvSeq result = opencv_imgproc.cvApproxPoly(seqFounded, Loader.sizeof(opencv_core.CvContour.class), memory, opencv_imgproc.CV_POLY_APPROX_DP,
				opencv_imgproc.cvContourPerimeter(seqFounded) * 0.02, 0);
		for (int i = 0; i < result.total(); i++) {
			opencv_core.CvPoint v = new opencv_core.CvPoint(opencv_core.cvGetSeqElem(result, i));
			opencv_imgproc.cvDrawCircle(foundedContoursImage, v, 5, opencv_core.CvScalar.BLUE, 20, 8, 0);
		}
		return result;
	}

	public static opencv_core.IplImage applyPerspectiveTransformThresholdOnOriginalImage(opencv_core.IplImage srcImage, opencv_core.CvSeq contour, int percentage) {
		opencv_core.IplImage warpImage = opencv_core.cvCloneImage(srcImage);
		for (int i = 0; i < contour.total(); i++) {
			opencv_core.CvPoint point = new opencv_core.CvPoint(opencv_core.cvGetSeqElem(contour, i));
			point.x((point.x() * 100) / percentage);
			point.y((point.y() * 100) / percentage);
		}
		opencv_core.CvPoint topRightPoint = new opencv_core.CvPoint(opencv_core.cvGetSeqElem(contour, 0));
		opencv_core.CvPoint topLeftPoint = new opencv_core.CvPoint(opencv_core.cvGetSeqElem(contour, 1));
		opencv_core.CvPoint bottomLeftPoint = new opencv_core.CvPoint(opencv_core.cvGetSeqElem(contour, 2));
		opencv_core.CvPoint bottomRightPoint = new opencv_core.CvPoint(opencv_core.cvGetSeqElem(contour, 3));
		int resultWidth = topRightPoint.x() - topLeftPoint.x();
		int bottomWidth = bottomRightPoint.x() - bottomLeftPoint.x();
		if (bottomWidth > resultWidth) {
			resultWidth = bottomWidth;
		}
		int resultHeight = bottomLeftPoint.y() - topLeftPoint.y();
		int bottomHeight = bottomRightPoint.y() - topRightPoint.y();
		if (bottomHeight > resultHeight) {
			resultHeight = bottomHeight;
		}
		float[] sourcePoints = {topLeftPoint.x(), topLeftPoint.y(), topRightPoint.x(), topRightPoint.y(),
						bottomLeftPoint.x(), bottomLeftPoint.y(), bottomRightPoint.x(), bottomRightPoint.y()};
		float[] destinationPoint = {0, 0, resultWidth, 0, 0, resultHeight, resultWidth, resultHeight};
		opencv_core.CvMat homography = opencv_core.cvCreateMat(3, 3, opencv_core.CV_32FC1);
		opencv_imgproc.cvGetPerspectiveTransform(sourcePoints, destinationPoint, homography);
		opencv_core.IplImage destImage = opencv_core.cvCloneImage(warpImage);
		opencv_imgproc.cvWarpPerspective(warpImage, destImage, homography, opencv_imgproc.CV_INTER_LINEAR, opencv_core.CvScalar.ZERO);
		return cropImage(destImage, 0, 0, resultWidth, resultHeight);
	}

	private static opencv_core.IplImage cropImage(opencv_core.IplImage srcImage, int fromX, int fromY, int toWidth, int toHeight) {
		opencv_core.cvSetImageROI(srcImage, opencv_core.cvRect(fromX, fromY, toWidth, toHeight));
		opencv_core.IplImage destImage = opencv_core.cvCloneImage(srcImage);
		opencv_core.cvCopy(srcImage, destImage);
		return destImage;
	}

	public static opencv_core.IplImage cleanImageSmoothingForOCR(opencv_core.IplImage srcImage) {
		opencv_core.IplImage destImage = opencv_core.cvCreateImage(opencv_core.cvGetSize(srcImage), opencv_core.IPL_DEPTH_8U, 1);
		opencv_imgproc.cvCvtColor(srcImage, destImage, opencv_imgproc.CV_BGR2GRAY);
		opencv_imgproc.cvSmooth(destImage, destImage, opencv_imgproc.CV_MEDIAN, 3, 0, 0, 0);
		opencv_imgproc.cvThreshold(destImage, destImage, 0, 255, opencv_imgproc.CV_THRESH_OTSU);
		return destImage;
	}
}
