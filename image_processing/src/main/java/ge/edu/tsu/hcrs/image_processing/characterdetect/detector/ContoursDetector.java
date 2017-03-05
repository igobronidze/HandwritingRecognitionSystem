package ge.edu.tsu.hcrs.image_processing.characterdetect.detector;

import ge.edu.tsu.hcrs.image_processing.characterdetect.model.Contour;
import ge.edu.tsu.hcrs.image_processing.characterdetect.model.Point;
import ge.edu.tsu.hcrs.image_processing.characterdetect.model.TextAdapter;
import ge.edu.tsu.hcrs.image_processing.characterdetect.model.TextRow;
import ge.edu.tsu.hcrs.image_processing.characterdetect.util.ElementsAddUtil;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ContoursDetector {

    private static boolean[][] checked;

    private static boolean[][] colored;

    private static short width;

    private static short height;

    public static TextAdapter detectContours(BufferedImage image, ContoursDetectorParams params) {
        width = (short) image.getWidth();
        height = (short) image.getHeight();
        initCheckedMatrix();
        initColoredMatrix(image, params.getRgbChecker());
        TextAdapter textAdapter = new TextAdapter();
        TextRow lastTextRow = new TextRow();
        for (short i = 0; i < height; i++) {
            for (short j = 0; j < width; j++) {
                if (checked[i][j]) {
                    continue;
                }
                if (!colored[i][j]) {
                    checked[i][j] = true;
                    continue;
                }
                lastTextRow = addContour(textAdapter, new Point(i, j), lastTextRow);
            }
        }
        ElementsAddUtil.addTextRowAndUpdate(textAdapter, lastTextRow);
        return textAdapter;
    }

    private static TextRow addContour(TextAdapter textAdapter, Point point, TextRow lastTextRow) {
        Contour contour = new Contour();
        Queue<Point> queue = new LinkedList<>();
        queue.add(point);
        while (!queue.isEmpty()) {
            Point curr = queue.remove();
            checked[curr.getX()][curr.getY()] = true;
            ElementsAddUtil.addPointAndUpdate(contour, curr);
            queue.addAll(getConnectedPoints(curr.getX(), curr.getY()));
        }
        if (isInSameTextRow(lastTextRow, contour)) {
            ElementsAddUtil.addContourAndUpdate(lastTextRow, contour);
        } else {
            ElementsAddUtil.addTextRowAndUpdate(textAdapter, lastTextRow);
            lastTextRow = new TextRow();
            ElementsAddUtil.addContourAndUpdate(lastTextRow, contour);
        }
        return lastTextRow;
    }

    private static boolean isInSameTextRow(TextRow textRow, Contour contour) {
        if (textRow.getContours().size() == 0) {
            return true;
        }
        if (textRow.getBottomPoint() >= contour.getTopPoint()) {
            return true;
        } else {
            return false;
        }
    }

    private static List<Point> getConnectedPoints(short i, short j) {
        List<Point> connected = new ArrayList<>();
        for (short deltaI = -1; deltaI <= 1; deltaI++) {
            for (short deltaJ = -1; deltaJ <= 1; deltaJ++) {
                if (i + deltaI >=0 && i + deltaI < height && j + deltaJ >=0 && j + deltaJ < width) {
                    if (colored[i + deltaI][j + deltaJ] && !checked[i + deltaI][j + deltaJ]) {
                        connected.add(new Point((short)(i + deltaI), (short)(j + deltaJ)));
                        checked[i + deltaI][j + deltaJ] = true;
                    }
                }
            }
        }
        return connected;
    }

    private static void initColoredMatrix(BufferedImage image, int rgbChecker) {
        int width = image.getWidth();
        int height = image.getHeight();
        colored = new boolean[height][width];
        for (short i = 0; i < height; i++) {
            for (short j = 0; j < width; j++) {
                colored[i][j] = (Math.abs(image.getRGB(j, i) ) > rgbChecker);
            }
        }
    }

    private static void initCheckedMatrix() {
        checked = new boolean[height][width];
    }
}
