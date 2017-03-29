package ge.edu.tsu.hrs.image_processing.opencv.parameter;

import org.bytedeco.javacpp.opencv_imgproc;

public class ImageResizerParams {

	private int width;

	private int height;

	private double fx = 2;

	private double fy = 2;

	// interpolation method:
	//	INTER_NEAREST - a nearest-neighbor interpolation
	//	INTER_LINEAR - a bilinear interpolation (used by default)
	//	INTER_AREA - resampling using pixel area relation. It may be a preferred method for image decimation, as it gives moire’-free results. But when the image is zoomed, it is similar to the INTER_NEAREST method.
	//	INTER_CUBIC - a bicubic interpolation over 4x4 pixel neighborhood
	//	INTER_LANCZOS4 - a Lanczos interpolation over 8x8 pixel neighborhood
	private int interpolation = opencv_imgproc.INTER_NEAREST;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getFx() {
		return fx;
	}

	public void setFx(double fx) {
		this.fx = fx;
	}

	public double getFy() {
		return fy;
	}

	public void setFy(double fy) {
		this.fy = fy;
	}

	public int getInterpolation() {
		return interpolation;
	}

	public void setInterpolation(int interpolation) {
		this.interpolation = interpolation;
	}
}
