package ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.threshold;

import org.bytedeco.javacpp.opencv_imgproc;

public class AdaptiveThresholdParams implements ThresholdParams {

	// maxValue – Non-zero value assigned to the pixels for which the condition is satisfied. See the details below.
	private double maxValue = 255;

	// adaptiveMethod – Adaptive thresholding algorithm to use, ADAPTIVE_THRESH_MEAN_C=0 or ADAPTIVE_THRESH_GAUSSIAN_C=1.
	private int adaptiveMethod = opencv_imgproc.ADAPTIVE_THRESH_GAUSSIAN_C;

	// thresholdType – Thresholding type that must be either THRESH_BINARY=0 or THRESH_BINARY_INV=1.
	private int thresholdType = opencv_imgproc.THRESH_BINARY;

	// blockSize – Size of a pixel neighborhood that is used to calculate a threshold value for the pixel: 3, 5, 7, and so on.
	private int blockSize = 15;

	// C – Constant subtracted from the mean or weighted mean (see the details below). Normally, it is positive but may be zero or negative as well.
	private int c = 2;

	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	public int getAdaptiveMethod() {
		return adaptiveMethod;
	}

	public void setAdaptiveMethod(int adaptiveMethod) {
		this.adaptiveMethod = adaptiveMethod;
	}

	public int getThresholdType() {
		return thresholdType;
	}

	public void setThresholdType(int thresholdType) {
		this.thresholdType = thresholdType;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}
}
