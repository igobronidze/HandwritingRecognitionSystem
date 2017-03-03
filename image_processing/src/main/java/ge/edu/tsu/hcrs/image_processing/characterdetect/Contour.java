package ge.edu.tsu.hcrs.image_processing.characterdetect;

import java.util.ArrayList;
import java.util.List;

public class Contour {

    private List<Point> contourCoordinates = new ArrayList<>();

    public List<Point> getContourCoordinates() {
        return contourCoordinates;
    }

    public void setContourCoordinates(List<Point> contourCoordinates) {
        this.contourCoordinates = contourCoordinates;
    }
}
