package ge.edu.tsu.hcrs.image_processing.characterdetect;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ContoursDetector {

    private static final int rgbChecker = 4500000;

    private static boolean[][] checked;

    private static boolean[][] colored;

    private static short width;

    private static short height;

    public static List<Contour> detectContours(BufferedImage image) {
        width = (short) image.getWidth();
        height = (short) image.getHeight();
        initCheckedMatrix();
        initColoredMatrix(image);
        List<Contour> contours = new ArrayList<>();
        for (short i = 0; i < height; i++) {
            for (short j = 0; j < width; j++) {
                if (checked[i][j]) {
                    continue;
                }
                if (!colored[i][j]) {
                    checked[i][j] = true;
                    continue;
                }
                contours.add(detectContour(new Point(i, j)));
            }
        }
        return contours;
    }

    private static Contour detectContour(Point point) {
        Contour contour = new Contour();
        Queue<Point> queue = new LinkedList<>();
        queue.add(point);
        while (!queue.isEmpty()) {
            Point curr = queue.remove();
            checked[curr.getX()][curr.getY()] = true;
            contour.getContourCoordinates().add(curr);
            queue.addAll(getConnectedPoints(curr.getX(), curr.getY()));
        }
        return contour;
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

    private static void initColoredMatrix(BufferedImage image) {
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
