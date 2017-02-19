package ge.edu.tsu.hcrs.image_processing.opencv.parameter.cutpapper;

public class CutPaperParams {

    private CannyEdgeDetectionParams cannyEdgeDetectionParams = new CannyEdgeDetectionParams();

    private LargestFigureParams largestFigureParams = new LargestFigureParams();

    private PerspectiveTransformParams perspectiveTransformParams = new PerspectiveTransformParams();

    private int downPercent = 100;

    public CannyEdgeDetectionParams getCannyEdgeDetectionParams() {
        return cannyEdgeDetectionParams;
    }

    public void setCannyEdgeDetectionParams(CannyEdgeDetectionParams cannyEdgeDetectionParams) {
        this.cannyEdgeDetectionParams = cannyEdgeDetectionParams;
    }

    public LargestFigureParams getLargestFigureParams() {
        return largestFigureParams;
    }

    public void setLargestFigureParams(LargestFigureParams largestFigureParams) {
        this.largestFigureParams = largestFigureParams;
    }

    public PerspectiveTransformParams getPerspectiveTransformParams() {
        return perspectiveTransformParams;
    }

    public void setPerspectiveTransformParams(PerspectiveTransformParams perspectiveTransformParams) {
        this.perspectiveTransformParams = perspectiveTransformParams;
    }

    public int getDownPercent() {
        return downPercent;
    }

    public void setDownPercent(int downPercent) {
        this.downPercent = downPercent;
    }
}
