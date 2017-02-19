package ge.edu.tsu.hcrs.image_processing.opencv.parameter.cutpapper;

import ge.edu.tsu.hcrs.image_processing.opencv.parameter.blurring.Blurring;
import ge.edu.tsu.hcrs.image_processing.opencv.parameter.blurring.MedianBlur;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;

public class CannyEdgeDetectionParams {

    // depth: pixel depth in bits: IPL_DEPTH_8U, IPL_DEPTH_8S, IPL_DEPTH_16U,
    // IPL_DEPTH_16S, IPL_DEPTH_32S, IPL_DEPTH_32F, IPL_DEPTH_64F
    private int grayImageDepth = opencv_core.IPL_DEPTH_8U;

    // channels: Number of channels per pixel. Can be 1, 2, 3 or 4. The channels
    // are interleaved. The usual data layout of a color image is b0 g0 r0 b1 g1 r1 ...
    private int grayImageChannels = 1;

    // Color conversion operation that can be specifed using CV_src_color_space2dst_color_space constants
    private int colorConversion = opencv_imgproc.CV_RGB2GRAY;

    private Blurring blurring = new MedianBlur();

    private boolean applyErode = true;

    private boolean applyDilate = true;

    private double threshold1 = 75.0;

    private double threshold2 = 200.0;

    private int kSize = 3;

    public int getGrayImageDepth() {
        return grayImageDepth;
    }

    public void setGrayImageDepth(int grayImageDepth) {
        this.grayImageDepth = grayImageDepth;
    }

    public int getGrayImageChannels() {
        return grayImageChannels;
    }

    public void setGrayImageChannels(int grayImageChannels) {
        this.grayImageChannels = grayImageChannels;
    }

    public int getColorConversion() {
        return colorConversion;
    }

    public void setColorConversion(int colorConversion) {
        this.colorConversion = colorConversion;
    }

    public Blurring getBlurring() {
        return blurring;
    }

    public void setBlurring(Blurring blurring) {
        this.blurring = blurring;
    }

    public boolean isApplyErode() {
        return applyErode;
    }

    public void setApplyErode(boolean applyErode) {
        this.applyErode = applyErode;
    }

    public boolean isApplyDilate() {
        return applyDilate;
    }

    public void setApplyDilate(boolean applyDilate) {
        this.applyDilate = applyDilate;
    }

    public double getThreshold1() {
        return threshold1;
    }

    public void setThreshold1(double threshold1) {
        this.threshold1 = threshold1;
    }

    public double getThreshold2() {
        return threshold2;
    }

    public void setThreshold2(double threshold2) {
        this.threshold2 = threshold2;
    }

    public int getkSize() {
        return kSize;
    }

    public void setkSize(int kSize) {
        this.kSize = kSize;
    }
}
