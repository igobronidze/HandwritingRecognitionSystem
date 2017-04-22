package ge.edu.tsu.hrs.image_processing.characterdetect.detector;

import ge.edu.tsu.hrs.image_processing.characterdetect.model.Contour;
import ge.edu.tsu.hrs.image_processing.characterdetect.model.Point;
import ge.edu.tsu.hrs.image_processing.characterdetect.model.TextAdapter;
import ge.edu.tsu.hrs.image_processing.characterdetect.model.TextRow;
import ge.edu.tsu.hrs.image_processing.characterdetect.util.ElementsAddUtil;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class ContoursDetector {

    private static boolean[][] checked;

    private static int[][] imagePixels;

    private static short width;

    private static short height;

    public static TextAdapter detectContours(BufferedImage image, TextCutterParams params) {
        width = (short) image.getWidth();
        height = (short) image.getHeight();
        initCheckedMatrix();
        initColoredMatrix(image);
        TextAdapter textAdapter = new TextAdapter();
        TextRow lastTextRow = new TextRow();
        for (short i = 0; i < height; i++) {
            for (short j = 0; j < width; j++) {
                if (checked[i][j]) {
                    continue;
                }
                if (imagePixels[i][j] > params.getCheckedRGBMaxValue()) {
                    checked[i][j] = true;
                    continue;
                }
                if (imagePixels[i][j] > params.getCheckNeighborRGBMaxValue()) {
                    continue;
                }
                lastTextRow = addContour(textAdapter, lastTextRow, new Point(i, j, imagePixels[i][j]), params);
            }
        }
        if (params.isUseJoiningFunctional()) {
            applyJoining(lastTextRow, params);
        }
        ElementsAddUtil.addTextRowAndUpdate(textAdapter, lastTextRow);
        return textAdapter;
    }

    private static TextRow addContour(TextAdapter textAdapter, TextRow lastTextRow, Point point, TextCutterParams params) {
        Contour contour = new Contour();
        Queue<Point> queue = new LinkedList<>();
        queue.add(point);
        checked[point.getX()][point.getY()] = true;
        while (!queue.isEmpty()) {
            Point curr = queue.remove();
            ElementsAddUtil.addPointAndUpdate(contour, curr);
            if (curr.getColor() <= params.getCheckNeighborRGBMaxValue()) {
                queue.addAll(getConnectedPoints(curr.getX(), curr.getY(), params));
            }
        }
        int area = (contour.getBottomPoint() - contour.getTopPoint() + 1) * (contour.getRightPoint() - contour.getLeftPoint() + 1);
        if (area < params.getNoiseArea()) {
            return lastTextRow;
        }
        if (isInSameTextRow(lastTextRow, contour, params.getPercentageOfSamesForOneRow())) {
            ElementsAddUtil.addContourAndUpdate(lastTextRow, contour);
        } else {
            if (params.isUseJoiningFunctional()) {
                applyJoining(lastTextRow, params);
            }
            ElementsAddUtil.addTextRowAndUpdate(textAdapter, lastTextRow);
            lastTextRow = new TextRow();
            ElementsAddUtil.addContourAndUpdate(lastTextRow, contour);
        }
        return lastTextRow;
    }

    private static void applyJoining(TextRow textRow, TextCutterParams params) {
        Queue<Contour> contours = textRow.getContours();
        Queue<Contour> newContours = new PriorityQueue<>(new Comparator<Contour>() {
            @Override
            public int compare(Contour o1, Contour o2) {
                return Short.compare(o1.getLeftPoint(), o2.getLeftPoint());
            }
        });
        if (contours.size() != 0) {
            Contour lContour = contours.poll();
            newContours.add(lContour);
            while (contours.size() != 0) {
                Contour contour = contours.poll();
                if (isUnitedContours(lContour, contour, params.getPercentageOfSameForJoining())) {
                    lContour.setUnitedContour(contour);
                    // may update contour's edges, but it's occur some problems
                } else {
                    newContours.add(contour);
                }
                lContour = contour;
            }
        }
        textRow.setContours(newContours);
    }

    private static boolean isUnitedContours(Contour contour1, Contour contour2, int numberOfSameForJoining) {
        int samePixels = Math.min(contour1.getRightPoint(), contour2.getRightPoint()) - Math.max(contour1.getLeftPoint(), contour2.getLeftPoint()) + 1;
        return samePixels > 0 && ((float) samePixels / (contour1.getRightPoint() - contour1.getLeftPoint() + 1) * 100 >= numberOfSameForJoining ||
                (float) samePixels / (contour2.getRightPoint() - contour2.getLeftPoint() + 1) * 100 >= numberOfSameForJoining);
    }

    private static boolean isInSameTextRow(TextRow textRow, Contour contour, int percentage) {
        return textRow.getContours().size() == 0 || (float)(contour.getBottomPoint() - contour.getTopPoint() + 1) * percentage / 100 <= (textRow.getBottomPoint() - contour.getTopPoint() + 1);
    }

    private static List<Point> getConnectedPoints(short i, short j, TextCutterParams params) {
        List<Point> connected = new ArrayList<>();
        for (short deltaI = -1; deltaI <= 1; deltaI++) {
            for (short deltaJ = -1; deltaJ <= 1; deltaJ++) {
                if (i + deltaI >=0 && i + deltaI < height && j + deltaJ >=0 && j + deltaJ < width) {
                    if (imagePixels[i + deltaI][j + deltaJ] <= params.getCheckedRGBMaxValue() && !checked[i + deltaI][j + deltaJ]) {
                        connected.add(new Point((short)(i + deltaI), (short)(j + deltaJ), imagePixels[i + deltaI][j + deltaJ]));
                        checked[i + deltaI][j + deltaJ] = true;
                    }
                }
            }
        }
        return connected;
    }

    private static void initColoredMatrix(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        imagePixels = new int[height][width];
        for (short i = 0; i < height; i++) {
            for (short j = 0; j < width; j++) {
                imagePixels[i][j] = image.getRGB(j, i);
            }
        }
    }

    private static void initCheckedMatrix() {
        checked = new boolean[height][width];
    }
}
