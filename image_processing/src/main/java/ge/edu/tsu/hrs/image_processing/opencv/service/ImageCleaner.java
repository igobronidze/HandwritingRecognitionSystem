package ge.edu.tsu.hrs.image_processing.opencv.service;

import ge.edu.tsu.hrs.image_processing.opencv.operation.BinaryConverter;
import ge.edu.tsu.hrs.image_processing.opencv.operation.MorphologicalOperations;
import ge.edu.tsu.hrs.image_processing.opencv.operation.NoiseRemover;
import ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.blurring.GaussianBlurParams;
import ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.morphological.DilationParams;
import ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.morphological.ErosionParams;
import ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.threshold.OtsuBinarizationParams;
import ge.edu.tsu.hrs.image_processing.opencv.service.condition.ImageCondition;
import ge.edu.tsu.hrs.image_processing.util.OpenCVUtil;
import org.bytedeco.javacpp.opencv_core;

import java.awt.image.BufferedImage;

public class ImageCleaner {

    public static BufferedImage cleanImage(BufferedImage srcImage, ImageCondition imageCondition) {
        opencv_core.Mat srcMat = OpenCVUtil.bufferedImageToMat(srcImage);
        System.out.println(srcMat.type());
        opencv_core.Mat mat = new opencv_core.Mat();
        srcMat.convertTo(mat, opencv_core.CV_8UC1);
        System.out.println(mat.type());
        mat = NoiseRemover.applyNoiseRemoval(mat, new GaussianBlurParams(), 2);
        mat = BinaryConverter.applyThreshold(mat, new OtsuBinarizationParams());
        mat = MorphologicalOperations.applyErosion(mat, new ErosionParams(), true, 1);
        mat = MorphologicalOperations.applyDilation(mat, new DilationParams(), true, 1);
        return OpenCVUtil.matToBufferedImage(mat);
    }
}
