package ge.edu.tsu.hcrs.image_processing.characterdetect.model;

import java.util.ArrayList;
import java.util.List;

public class Contour {

    private short topPoint = Short.MAX_VALUE;

    private short rightPoint = -1;

    private short bottomPoint = -1;

    private short leftPoint = Short.MAX_VALUE;

    private List<Point> contourCoordinates = new ArrayList<>();

    private Contour unitedContour;

    public short getTopPoint() {
        return topPoint;
    }

    public void setTopPoint(short topPoint) {
        this.topPoint = topPoint;
    }

    public short getRightPoint() {
        return rightPoint;
    }

    public void setRightPoint(short rightPoint) {
        this.rightPoint = rightPoint;
    }

    public short getBottomPoint() {
        return bottomPoint;
    }

    public void setBottomPoint(short bottomPoint) {
        this.bottomPoint = bottomPoint;
    }

    public short getLeftPoint() {
        return leftPoint;
    }

    public void setLeftPoint(short leftPoint) {
        this.leftPoint = leftPoint;
    }

    public List<Point> getContourCoordinates() {
        return contourCoordinates;
    }

    public void setContourCoordinates(List<Point> contourCoordinates) {
        this.contourCoordinates = contourCoordinates;
    }

    public Contour getUnitedContour() {
        return unitedContour;
    }

    public void setUnitedContour(Contour unitedContour) {
        this.unitedContour = unitedContour;
    }
}
