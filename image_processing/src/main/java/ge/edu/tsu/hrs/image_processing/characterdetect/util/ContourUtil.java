package ge.edu.tsu.hrs.image_processing.characterdetect.util;

import ge.edu.tsu.hrs.image_processing.characterdetect.model.Contour;
import ge.edu.tsu.hrs.image_processing.characterdetect.model.Point;

import java.awt.image.BufferedImage;

public class ContourUtil {

    private static final int whiteRGB = -1;

    public static BufferedImage getBufferedImageFromContour(Contour contour) {
        int colored[][] = getColoredMatrixFromContour(contour);
        BufferedImage image = new BufferedImage(colored[0].length, colored.length, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < colored.length; i++ ) {
            for (int j = 0; j < colored[i].length; j++) {
                image.setRGB(j, i, colored[i][j] == 0 ? whiteRGB : colored[i][j]);
            }
        }
        return image;
    }

    public static int[][] getColoredMatrixFromContour(Contour contour) {
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
        int colored[][] = new int[bottomPoint - topPoint + 1][rightPoint - leftPoint + 1];
        for (Point point : contour.getContourCoordinates()) {
            colored[point.getX() - topPoint][point.getY() - leftPoint] = point.getColor();
        }
        unitedContour = contour.getUnitedContour();
        while (unitedContour != null) {
            for (Point point : unitedContour.getContourCoordinates()) {
                colored[point.getX() - topPoint][point.getY() - leftPoint] = point.getColor();
            }
            unitedContour = unitedContour.getUnitedContour();
        }
        return colored;
    }
}
