package ge.edu.tsu.hcrs.image_processing.characterdetect;

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

    private static boolean[][] getColoredMatrixFromContour(Contour contour) {
        short minX = Short.MAX_VALUE;
        short minY = Short.MAX_VALUE;
        short maxX = -1;
        short maxY = -1;
        for (Point point : contour.getContourCoordinates()) {
            minX = (short)Math.min(minX, point.getX());
            minY = (short)Math.min(minY, point.getY());
            maxX = (short)Math.max(maxX, point.getX());
            maxY = (short)Math.max(maxY, point.getY());
        }
        boolean colored[][] = new boolean[maxX - minX + 1][maxY - minY + 1];
        for (Point point : contour.getContourCoordinates()) {
            colored[point.getX() - minX][point.getY() - minY] = true;
        }
        return colored;

    }
}
