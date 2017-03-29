package ge.edu.tsu.hrs.image_processing.opencv.parameter.characterdetector;

import org.bytedeco.javacpp.opencv_imgproc;

public class SimpleCharacterDetectorParams implements CharacterDetectorParams {

    private boolean convertToGray = true;

    // minVal and maxVal. Any edges with intensity gradient more than maxVal are sure to be edges and those below minVal are sure to be non-edges
    private int cannyMinVal = 100;

    // minVal and maxVal. Any edges with intensity gradient more than maxVal are sure to be edges and those below minVal are sure to be non-edges
    private int cannyMaxValue = 200;

    // mode – Contour retrieval mode
    // CV_RETR_EXTERNAL retrieves only the extreme outer contours. It sets hierarchy[i][2]=hierarchy[i][3]=-1 for all the contours.
    // CV_RETR_LIST retrieves all of the contours without establishing any hierarchical relationships.
    // CV_RETR_CCOMP retrieves all of the contours and organizes them into a two-level hierarchy. At the top level, there are external boundaries of the components. At the second level, there are boundaries of the holes. If there is another contour inside a hole of a connected component, it is still put at the top level.
    // CV_RETR_TREE retrieves all of the contours and reconstructs a full hierarchy of nested contours. This full hierarchy is built and shown in the OpenCV contours.c demo.
    private int findContoursMode = opencv_imgproc.CV_RETR_EXTERNAL;

    // method – Contour approximation method
    // CV_CHAIN_APPROX_NONE stores absolutely all the contour points. That is, any 2 subsequent points (x1,y1) and (x2,y2) of the contour will be either horizontal, vertical or diagonal neighbors, that is, max(abs(x1-x2),abs(y2-y1))==1.
    // CV_CHAIN_APPROX_SIMPLE compresses horizontal, vertical, and diagonal segments and leaves only their end points. For example, an up-right rectangular contour is encoded with 4 points.
    // CV_CHAIN_APPROX_TC89_L1,CV_CHAIN_APPROX_TC89_KCOS applies one of the flavors of the Teh-Chin chain approximation algorithm.
    private int findContoursMethod = opencv_imgproc.CV_CHAIN_APPROX_NONE;

    public boolean isConvertToGray() {
        return convertToGray;
    }

    public void setConvertToGray(boolean convertToGray) {
        this.convertToGray = convertToGray;
    }

    public int getCannyMinVal() {
        return cannyMinVal;
    }

    public void setCannyMinVal(int cannyMinVal) {
        this.cannyMinVal = cannyMinVal;
    }

    public int getCannyMaxValue() {
        return cannyMaxValue;
    }

    public void setCannyMaxValue(int cannyMaxValue) {
        this.cannyMaxValue = cannyMaxValue;
    }

    public int getFindContoursMode() {
        return findContoursMode;
    }

    public void setFindContoursMode(int findContoursMode) {
        this.findContoursMode = findContoursMode;
    }

    public int getFindContoursMethod() {
        return findContoursMethod;
    }

    public void setFindContoursMethod(int findContoursMethod) {
        this.findContoursMethod = findContoursMethod;
    }
}
