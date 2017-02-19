package ge.edu.tsu.hcrs.image_processing.opencv.parameter.cutpapper;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;

public class LargestFigureParams {

    // retrievalMode __ mode of the contour retrieval algorithm
    // RETR_EXTERNAL - retrieves only the extreme outer contours. It sets hierarchy[i][2]=hierarchy[i][3]=-1 for all the contours.
    // RETR_LIST - retrieves all of the contours without establishing any hierarchical relationships.
    // RETR_CCOMP - retrieves all of the contours and organizes them into a two-level hierarchy. At the top level, there
    //      are external boundaries of the components. At the second level, there are boundaries of the holes. If there is a
    //      nother contour inside a hole of a connected component, it is still put at the top level.
    // RETR_TREE - retrieves all of the contours and reconstructs a full hierarchy of nested contours.
    private int retrievalMode = opencv_imgproc.CV_RETR_LIST;

    // conAppMode __ the contour approximation algorithm
    // CHAIN_APPROX_NONE - stores absolutely all the contour points. That is, any 2 subsequent points (x1,y1) and (x2,y2)
    //      of the contour will be either horizontal, vertical or diagonal neighbors, that is, max(abs(x1-x2),abs(y2-y1))==1.
    // CHAIN_APPROX_SIMPLE - compresses horizontal, vertical, and diagonal segments and leaves only their end points.
    //      For example, an up-right rectangular contour is encoded with 4 points.
    private int conAppMode = opencv_imgproc.CV_CHAIN_APPROX_SIMPLE;

    // offset - დაწყების ადგილი
    private opencv_core.CvPoint point = opencv_core.cvPoint(0,0);

    // აპროქსიმაციის სიზუსტე
    private double approxAccuracy =  0.02;

    public int getRetrievalMode() {
        return retrievalMode;
    }

    public void setRetrievalMode(int retrievalMode) {
        this.retrievalMode = retrievalMode;
    }

    public int getConAppMode() {
        return conAppMode;
    }

    public void setConAppMode(int conAppMode) {
        this.conAppMode = conAppMode;
    }

    public opencv_core.CvPoint getPoint() {
        return point;
    }

    public void setPoint(opencv_core.CvPoint point) {
        this.point = point;
    }

    public double getApproxAccuracy() {
        return approxAccuracy;
    }

    public void setApproxAccuracy(double approxAccuracy) {
        this.approxAccuracy = approxAccuracy;
    }
}
