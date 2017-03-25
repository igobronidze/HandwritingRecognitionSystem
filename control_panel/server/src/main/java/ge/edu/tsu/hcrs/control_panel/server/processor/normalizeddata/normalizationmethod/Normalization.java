package ge.edu.tsu.hcrs.control_panel.server.processor.normalizeddata.normalizationmethod;

import ge.edu.tsu.hcrs.control_panel.model.network.TrainingDataInfo;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizationType;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizedData;
import ge.edu.tsu.hcrs.image_processing.characterdetect.model.Contour;
import ge.edu.tsu.hcrs.image_processing.characterdetect.model.Point;

import java.awt.image.BufferedImage;

public abstract class Normalization {

    static final int blackPixel = -16777216;

    public static Normalization getInstance(NormalizationType normalizationType) {
        switch (normalizationType) {
            case DISCRETE_BY_AREA:
                return new DiscreteByAreaNormalization();
            case DISCRETE_RESIZE:
                return new DiscreteResizeNormalization();
            case LINEAR_BY_AREA:
                return new LinearByAreaNormalization();
            case LINEAR_RESIZE:
                default:
                return new LinearResizeNormalization();
        }
    }

    public abstract NormalizedData getNormalizedDataFromImage(BufferedImage image, TrainingDataInfo trainingDataInfo, Character letter);

    public abstract NormalizedData getNormalizedDataFromContour(Contour contour, TrainingDataInfo trainingDataInfo);

    float[][] getNormalizedAreas(Contour contour, int height, int width) {
        return getNormalizedAreas(getAreasFromContour(contour), height, width, contour.getBottomPoint() - contour.getTopPoint() + 1, contour.getRightPoint() - contour.getLeftPoint() + 1);
    }

    float[][] getNormalizedAreas(BufferedImage image, int height, int width) {
        return getNormalizedAreas(getAreasFromBufferedImage(image), height, width, image.getHeight(), image.getWidth());
    }

    private float[][] getNormalizedAreas(float areas[][], int height, int width, int realHeight, int realWidth) {
        float normalizedAreas[][] = new float[height][width];
        float deltaI = (float) realHeight / height;
        float deltaJ = (float) realWidth / width;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                float topI = i * deltaI;
                float bottomI = (i + 1) * deltaI;
                float leftJ = j * deltaJ;
                float rightJ = (j + 1) * deltaJ;
                topI = (int) topI == topI ? topI + 0.000001F : topI;
                bottomI = (int) bottomI == bottomI ? bottomI - 0.000001F : bottomI;
                leftJ = (int) leftJ == leftJ ? leftJ + 0.00001F : leftJ;
                rightJ = (int) rightJ == rightJ ? rightJ - 0.000001F : rightJ;
                rightJ = (int) rightJ == realWidth ? realWidth - 0.0001F : rightJ;
                bottomI = (int) bottomI == realHeight ? realHeight - 0.0001F : bottomI;
                float area = 0F;
                for (int ii = (int)topI + 1; ii < (int)bottomI; ii++) {
                    for (int jj = (int)leftJ + 1; jj < (int)rightJ; jj++) {
                        area += areas[ii][jj];
                    }
                }
                if ((int)topI == (int)bottomI && (int)leftJ == (int)rightJ) {
                    area += (bottomI - topI) * (rightJ - leftJ) * areas[(int)topI][(int)leftJ];
                } else if ((int)topI == (int)bottomI) {
                    for (int jj = (int)leftJ + 1; jj < (int)rightJ; jj++) {
                        area += (bottomI - topI) * areas[(int)topI][jj];
                    }
                    area += (bottomI - topI) * ((int)leftJ + 1 - leftJ) * areas[(int)topI][(int)leftJ];
                    area += (bottomI - topI) * (rightJ - (int) rightJ) * areas[(int) topI][(int) rightJ];
                } else if ((int)leftJ == (int)rightJ) {
                    for (int ii = (int)topI + 1; ii < (int)bottomI; ii++) {
                        area += (rightJ - leftJ) * areas[ii][(int)leftJ];
                    }
                    area += ((int)topI + 1 - topI) * (rightJ - leftJ) * areas[(int)topI][(int)leftJ];
                    area += (bottomI - (int) bottomI) * (rightJ - leftJ) * areas[(int) bottomI][(int) leftJ];
                } else {
                    for (int ii = (int)topI + 1; ii < (int)bottomI; ii++) {
                        area += ((int) leftJ + 1 - leftJ) * areas[ii][(int)leftJ];
                        area += (rightJ - (int)rightJ) * areas[ii][(int)rightJ];
                    }
                    for (int jj = (int)leftJ + 1; jj < (int)rightJ; jj++) {
                        area += ((int)topI + 1 - topI) * areas[(int)topI][jj];
                        area += (bottomI - (int)bottomI) * areas[(int)bottomI][jj];
                    }
                    area += ((int)topI + 1 - topI) * ((int)leftJ + 1 - leftJ) * areas[(int)topI][(int)leftJ];
                    area += (bottomI - (int) bottomI) * ((int) leftJ + 1 - leftJ) * areas[(int) bottomI][(int) leftJ];
                    area += ((int) topI + 1 - topI) * (rightJ - (int) rightJ) * areas[(int) topI][(int) rightJ];
                    area += (bottomI - (int) bottomI) * (rightJ - (int) rightJ) * areas[(int) bottomI][(int) rightJ];
                }
                normalizedAreas[i][j] = area / (deltaI * deltaJ);
            }
        }
        return normalizedAreas;
    }

    private float[][] getAreasFromBufferedImage(BufferedImage image) {
        float[][] areas = new float[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                areas[i][j] = (float)((int)(((float)image.getRGB(j, i) / blackPixel) * 10000)) / 10000;
            }
        }
        return areas;
    }

    private float[][] getAreasFromContour(Contour contour) {
        int height = contour.getBottomPoint() - contour.getTopPoint() + 1;
        int width = contour.getRightPoint() - contour.getLeftPoint() + 1;
        float[][] areas = new float[height][width];
        for (Point point : contour.getContourCoordinates()) {
            areas[point.getX()][point.getY()] = (float)((int)(((float)point.getColor() / blackPixel) * 10000)) / 10000;
        }
        return areas;
    }
}
