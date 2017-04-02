package ge.edu.tsu.hrs.image_processing;

import ge.edu.tsu.hrs.image_processing.opencv.BinaryConverter;
import ge.edu.tsu.hrs.image_processing.opencv.MorphologicalOperations;
import ge.edu.tsu.hrs.image_processing.opencv.NoiseRemover;
import ge.edu.tsu.hrs.image_processing.opencv.parameter.blurring.GaussianBlurParams;
import ge.edu.tsu.hrs.image_processing.opencv.parameter.morphological.DilationParams;
import ge.edu.tsu.hrs.image_processing.opencv.parameter.morphological.ErosionParams;
import ge.edu.tsu.hrs.image_processing.opencv.parameter.threshold.AdaptiveThresholdParams;
import ge.edu.tsu.hrs.image_processing.opencv.parameter.threshold.OtsuBinarizationParams;
import ge.edu.tsu.hrs.image_processing.opencv.parameter.threshold.SimpleThresholdParams;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;

public class ThresholdRunner {

    private static final String srcPath = "D:\\Bachelor Project\\HandwrittenRecognitionSystem\\test_directory\\src_images\\3.jpg";

    private static final String resultPath = "D:\\Bachelor Project\\HandwrittenRecognitionSystem\\test_directory\\src_images\\4.PNG";

    public static void main(String[] args) {
        opencv_core.Mat resultMat;
        opencv_core.Mat srcMat = opencv_imgcodecs.imread(srcPath, opencv_core.CV_8UC1);
        opencv_core.Mat mat = NoiseRemover.applyNoiseRemoval(srcMat, new GaussianBlurParams(), 6);
        mat = MorphologicalOperations.applyErosion(mat, new ErosionParams(), true, 1);
        mat = MorphologicalOperations.applyDilation(mat, new DilationParams(), true, 1);
//        SimpleThresholdParams thresholdParams = new SimpleThresholdParams();
//        thresholdParams.setThresh(180);
        mat = BinaryConverter.applyThreshold(mat, new AdaptiveThresholdParams());
        opencv_imgcodecs.imwrite(resultPath, mat);
    }
}
