package ge.edu.tsu.hcrs.image_processing.characterdetect.util;

import ge.edu.tsu.hcrs.image_processing.characterdetect.model.Contour;
import ge.edu.tsu.hcrs.image_processing.characterdetect.model.Point;
import ge.edu.tsu.hcrs.image_processing.characterdetect.model.TextAdapter;
import ge.edu.tsu.hcrs.image_processing.characterdetect.model.TextRow;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class ElementsAddUtil {

    public static void addPointAndUpdate(Contour contour, Point point) {
        contour.getContourCoordinates().add(point);
        contour.setTopPoint((short) Math.min(contour.getTopPoint(), point.getX()));
        contour.setRightPoint((short) Math.max(contour.getRightPoint(), point.getY()));
        contour.setBottomPoint((short) Math.max(contour.getBottomPoint(), point.getX()));
        contour.setLeftPoint((short) Math.min(contour.getLeftPoint(), point.getY()));
    }

    public static void addContourAndUpdate(TextRow textRow, Contour contour) {
        textRow.getContours().add(contour);
        textRow.setTopPoint((short) Math.min(textRow.getTopPoint(), contour.getTopPoint()));
        textRow.setRightPoint((short) Math.max(textRow.getRightPoint(), contour.getRightPoint()));
        textRow.setBottomPoint((short) Math.max(textRow.getBottomPoint(), contour.getBottomPoint()));
        textRow.setLeftPoint((short) Math.min(textRow.getLeftPoint(), contour.getLeftPoint()));
    }

    public static void addTextRowAndUpdate(TextAdapter textAdapter, TextRow textRow) {
        Queue<Contour> contours = textRow.getContours();
        Queue<Contour> newContours = new PriorityQueue<>(new Comparator<Contour>() {
            @Override
            public int compare(Contour o1, Contour o2) {
                return Short.compare(o1.getLeftPoint(), o2.getLeftPoint());
            }
        });
        if (contours.size() != 0) {
            Contour lastContour = contours.poll();
            newContours.add(lastContour);
            while (contours.size() != 0) {
                Contour contour = contours.poll();
                if (isUnitedContours(lastContour, contour)) {
                    lastContour.setUnitedContour(contour);
                } else {
                    newContours.add(contour);
                    lastContour = contour;
                }
            }
        }
        textRow.setContours(newContours);
        textAdapter.getRows().add(textRow);
    }

    private static boolean isUnitedContours(Contour contour1, Contour contour2) {
        return contour1.getRightPoint() >= contour2.getLeftPoint();
    }
}
