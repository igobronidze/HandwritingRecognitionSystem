package ge.edu.tsu.hrs.image_processing.opencv.operation;

import ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.morphological.DilationParams;
import ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.morphological.ErosionParams;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;

public class MorphologicalOperations {

    public static opencv_core.Mat applyErosion(opencv_core.Mat srcMat, ErosionParams params, boolean reverse, int iteration) {
        opencv_core.Mat resultMat = new opencv_core.Mat();
        if (reverse) {
            opencv_core.bitwise_not(srcMat, resultMat);
        } else {
            resultMat = srcMat;
        }
        for (int i = 0; i < iteration; i++) {
            opencv_imgproc.erode(resultMat, resultMat, params.getElement());
        }
        if (reverse) {
            opencv_core.bitwise_not(resultMat, resultMat);
        }
        return resultMat;
    }

    public static opencv_core.Mat applyDilation(opencv_core.Mat srcMat, DilationParams params, boolean reverse, int iteration) {
        opencv_core.Mat resultMat = new opencv_core.Mat();
        if (reverse) {
            opencv_core.bitwise_not(srcMat, resultMat);
        } else {
            resultMat = srcMat;
        }
        for (int i = 0; i < iteration; i++) {
            opencv_imgproc.dilate(resultMat, resultMat, params.getElement());
        }
        if (reverse) {
            opencv_core.bitwise_not(resultMat, resultMat);
        }
        return resultMat;
    }
}
