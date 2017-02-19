package ge.edu.tsu.hcrs.image_processing.opencv.parameter.cutpapper;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;

public class PerspectiveTransformParams {

    // type (MAT_DEPTH) Type of the matrix elements.
    private int homographyMatType = opencv_core.CV_32FC1;

    // flags – combination of interpolation methods (INTER_LINEAR or INTER_NEAREST) and the optional flag WARP_INVERSE_MAP
    private int flags = opencv_imgproc.CV_INTER_LINEAR;

    private opencv_core.CvScalar cvScalar = opencv_core.CvScalar.ZERO;

    public int getHomographyMatType() {
        return homographyMatType;
    }

    public void setHomographyMatType(int homographyMatType) {
        this.homographyMatType = homographyMatType;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public opencv_core.CvScalar getCvScalar() {
        return cvScalar;
    }

    public void setCvScalar(opencv_core.CvScalar cvScalar) {
        this.cvScalar = cvScalar;
    }
}
