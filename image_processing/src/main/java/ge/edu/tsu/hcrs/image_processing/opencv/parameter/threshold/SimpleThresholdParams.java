package ge.edu.tsu.hcrs.image_processing.opencv.parameter.threshold;

import org.bytedeco.javacpp.opencv_imgproc;

public class SimpleThresholdParams implements ThresholdParams {

	// thresh – threshold value.
	// თუ არ ვცდები მასზე ნაკლები იქცევა თეთრად, მეტი შავად(თეთრი და შავი მაგალითისთვის)
	private double thresh = 127;

	// maxval – maximum value to use with the THRESH_BINARY and THRESH_BINARY_INV thresholding types.
	private double maxValue = 255;

	// type – thresholding type (see the details below).
	// [THRESH_BINARY, THRESH_BINARY_INV, THRESH_TRUNC, THRESH_TOZERO, THRESH_TOZERO_INV]
	private int type = opencv_imgproc.THRESH_BINARY;

	public double getThresh() {
		return thresh;
	}

	public void setThresh(double thresh) {
		this.thresh = thresh;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
