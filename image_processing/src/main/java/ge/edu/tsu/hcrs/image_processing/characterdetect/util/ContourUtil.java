package ge.edu.tsu.hcrs.image_processing.characterdetect.util;

import ge.edu.tsu.hcrs.image_processing.characterdetect.model.Contour;
import ge.edu.tsu.hcrs.image_processing.characterdetect.model.Point;

import java.awt.image.BufferedImage;

public class ContourUtil {

    private static final int blackRGB = -16777216;

    private static final int whiteRGB = -1;

    public static BufferedImage getBufferedImageFromContour(Contour contour) {
        boolean colored[][] = getColoredMatrixFromContour(contour);
        BufferedImage image = new BufferedImage(colored[0].length, colored.length, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < colored.length; i++ ) {
            for (int j = 0; j < colored[i].length; j++) {
                if (colored[i][j]) {
                    image.setRGB(j, i, blackRGB);
                } else {
                    image.setRGB(j, i, whiteRGB);
                }
            }
        }
        return image;
    }

    public static boolean[][] getColoredMatrixFromContour(Contour contour) {
        short topPoint = contour.getTopPoint();
        short rightPoint = contour.getRightPoint();
        short bottomPoint = contour.getBottomPoint();
        short leftPoint = contour.getLeftPoint();
        Contour unitedContour = contour.getUnitedContour();
        while (unitedContour != null) {
            topPoint = (short) Math.min(topPoint, unitedContour.getTopPoint());
            rightPoint = (short) Math.max(rightPoint, unitedContour.getRightPoint());
            bottomPoint = (short) Math.max(bottomPoint, unitedContour.getBottomPoint());
            leftPoint = (short) Math.min(leftPoint, unitedContour.getLeftPoint());
            unitedContour = unitedContour.getUnitedContour();
        }
        boolean colored[][] = new boolean[bottomPoint - topPoint + 1][rightPoint - leftPoint + 1];
        for (Point point : contour.getContourCoordinates()) {
            colored[point.getX() - topPoint][point.getY() - leftPoint] = true;
        }
        unitedContour = contour.getUnitedContour();
        while (unitedContour != null) {
            for (Point point : unitedContour.getContourCoordinates()) {
                colored[point.getX() - topPoint][point.getY() - leftPoint] = true;
            }
            unitedContour = unitedContour.getUnitedContour();
        }
        return colored;

    }
}
