package ge.edu.tsu.hrs.image_processing.opencv;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;

public class LinesRemover {

	public static void removeLines(String srcImagePath, String resultImagePath) {
		opencv_core.Mat srcMat = loadImageMat(srcImagePath);
		opencv_core.Mat grayMat = getGrayMat(srcMat);
		opencv_core.Mat transformedMat = transformToBinary(grayMat);
		opencv_core.Mat horizontalMat = getHorizontalMat(transformedMat);
		opencv_core.Mat verticalMat = getVerticalMat(transformedMat);
		opencv_core.Mat resultMat = lastStage(verticalMat);
		saveImageByMat(resultImagePath, resultMat);
	}

	private static opencv_core.Mat lastStage(opencv_core.Mat srcMat) {
		opencv_core.Mat newMat = new opencv_core.Mat();
		opencv_core.bitwise_not(srcMat, newMat);
		opencv_core.Mat edges = new opencv_core.Mat();
		opencv_imgproc.adaptiveThreshold(newMat, edges, 255, opencv_imgproc.CV_ADAPTIVE_THRESH_MEAN_C, opencv_imgproc.THRESH_BINARY, 3, -2);
		opencv_core.Mat kernel = new opencv_core.Mat(2, 2, opencv_core.CV_8UC1);
		opencv_imgproc.dilate(edges, edges, kernel);
		opencv_core.Mat smooth = new opencv_core.Mat();
		newMat.copyTo(smooth);
		opencv_imgproc.blur(smooth, smooth, new opencv_core.Size(2, 2));
		smooth.copyTo(newMat, edges);
		return newMat;
	}

	private static opencv_core.Mat getVerticalMat(opencv_core.Mat srcMat) {
		opencv_core.Mat verticalMat = srcMat.clone();
		int verticalSize = verticalMat.rows() / 30;
		opencv_core.Mat verticalStructure = opencv_imgproc.getStructuringElement(opencv_imgproc.MORPH_RECT, new opencv_core.Size(1, verticalSize));
		opencv_imgproc.erode(verticalMat, verticalMat, verticalStructure);
		opencv_imgproc.dilate(verticalMat, verticalMat, verticalStructure);
		return verticalMat;
	}

	private static opencv_core.Mat getHorizontalMat(opencv_core.Mat srcMat) {
		opencv_core.Mat horizontalMat = srcMat.clone();
		int horizontalSize = horizontalMat.cols() / 30;
		opencv_core.Mat horizontalStructure = opencv_imgproc.getStructuringElement(opencv_imgproc.MORPH_RECT, new opencv_core.Size(horizontalSize,1));
		opencv_imgproc.erode(horizontalMat, horizontalMat, horizontalStructure);
		opencv_imgproc.dilate(horizontalMat, horizontalMat, horizontalStructure);
		return horizontalMat;
	}

	private static opencv_core.Mat transformToBinary(opencv_core.Mat srcMat) {
		opencv_core.Mat bw = new opencv_core.Mat();
		opencv_core.Mat bitwiseNotMat = new opencv_core.Mat();
		opencv_core.bitwise_not(srcMat, bitwiseNotMat);
		opencv_imgproc.adaptiveThreshold(bitwiseNotMat, bw, 255, opencv_imgproc.CV_ADAPTIVE_THRESH_MEAN_C, opencv_imgproc.THRESH_BINARY, 15, -2);
		return bw;
	}

	private static opencv_core.Mat getGrayMat(opencv_core.Mat srcMat) {
		opencv_core.Mat grayMat = new opencv_core.Mat();
		opencv_imgproc.cvtColor(srcMat, grayMat, opencv_imgproc.CV_BGR2GRAY);
		return grayMat;
	}

	private static opencv_core.Mat loadImageMat(String absPath) {
		return opencv_imgcodecs.imread(absPath);
	}

	private static void saveImageByMat(String absPath, opencv_core.Mat mat) {
		opencv_imgcodecs.imwrite(absPath, mat);
	}
}
