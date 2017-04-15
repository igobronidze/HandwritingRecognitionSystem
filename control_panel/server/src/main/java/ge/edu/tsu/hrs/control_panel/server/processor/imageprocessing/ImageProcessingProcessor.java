package ge.edu.tsu.hrs.control_panel.server.processor.imageprocessing;

import ge.edu.tsu.hrs.control_panel.model.imageprocessing.BlurringParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.MorphologicalParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.ThresholdParameters;
import ge.edu.tsu.hrs.image_processing.characterdetect.detector.TextCutterParams;
import ge.edu.tsu.hrs.image_processing.characterdetect.detector.TextCutter;
import ge.edu.tsu.hrs.image_processing.exception.TextAdapterSizeException;
import ge.edu.tsu.hrs.image_processing.opencv.operation.BinaryConverter;
import ge.edu.tsu.hrs.image_processing.opencv.operation.ImageResizer;
import ge.edu.tsu.hrs.image_processing.opencv.operation.MorphologicalOperations;
import ge.edu.tsu.hrs.image_processing.opencv.operation.NoiseRemover;
import ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.ImageResizerParams;
import ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.blurring.BilateralFilterParams;
import ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.blurring.BlurParams;
import ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.blurring.GaussianBlurParams;
import ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.blurring.MedianBlurParams;
import ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.morphological.DilationParams;
import ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.morphological.ErosionParams;
import ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.threshold.AdaptiveThresholdParams;
import ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.threshold.OtsuBinarizationParams;
import ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.threshold.SimpleThresholdParams;
import ge.edu.tsu.hrs.image_processing.util.OpenCVUtil;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;

import java.awt.image.BufferedImage;

public class ImageProcessingProcessor {

    public void cutAndSaveSymbolsFromText(String srcImagePath, String srcTextPath, String resultImagesPath, TextCutterParams textCutterParams) {
        try {
            TextCutter.saveCutSymbols(srcImagePath, srcTextPath, resultImagesPath, textCutterParams);
        } catch (TextAdapterSizeException ex) {
            System.out.println("Expected Size - " + ex.getExpectedSize() + ", Result Size - " + ex.getResultSize());
        }
    }

    public BufferedImage resizeImage(BufferedImage srcImage, boolean scaleResizing, double x, double y) {
        opencv_core.Mat srcMat = OpenCVUtil.bufferedImageToMat(srcImage);
        ImageResizerParams params = new ImageResizerParams();
        if (scaleResizing) {
            params.setFx(x);
            params.setFy(y);
            params.setWidth(0);
            params.setHeight(0);
        } else {
            params.setFx(0);
            params.setFy(0);
            params.setWidth((int) x);
            params.setHeight((int) y);
        }
        opencv_core.Mat resultMat = ImageResizer.resize(srcMat, params);
        return OpenCVUtil.matToBufferedImage(resultMat);
    }

    public BufferedImage cleanImage(BufferedImage srcImage, BlurringParameters blurringParameters, ThresholdParameters thresholdParameters, MorphologicalParameters morphologicalParameters) {
        opencv_core.Mat mat = OpenCVUtil.bufferedImageToMat(srcImage);
        if (mat.type() != opencv_core.CV_8UC1) {
            opencv_imgproc.cvtColor(mat, mat, opencv_imgproc.CV_RGB2GRAY);
        }
        switch (blurringParameters.getType()) {
            case BILATERAL_FILTER:
                BilateralFilterParams bilateralFilterParams = new BilateralFilterParams();
                bilateralFilterParams.setDiameter(blurringParameters.getDiameter());
                bilateralFilterParams.setSigmaColor(blurringParameters.getSigmaColor());
                bilateralFilterParams.setSigmaSpace(blurringParameters.getSigmaSpace());
                mat = NoiseRemover.applyNoiseRemoval(mat, bilateralFilterParams, blurringParameters.getAmount());
                break;
            case BLUR:
                BlurParams blurParams = new BlurParams();
                blurParams.setkSizeHeight(blurringParameters.getkSizeHeight());
                blurParams.setkSizeWidth(blurringParameters.getkSizeWidth());
                mat = NoiseRemover.applyNoiseRemoval(mat, blurParams, blurringParameters.getAmount());
                break;
            case GAUSSIAN_BLUR:
                GaussianBlurParams gaussianBlurParams = new GaussianBlurParams();
                gaussianBlurParams.setkSizeWidth(blurringParameters.getkSizeWidth());
                gaussianBlurParams.setkSizeHeight(blurringParameters.getkSizeHeight());
                gaussianBlurParams.setBorderType(blurringParameters.getBorderType());
                gaussianBlurParams.setSigmaX(blurringParameters.getSigmaX());
                gaussianBlurParams.setSigmaY(blurringParameters.getSigmaY());
                mat = NoiseRemover.applyNoiseRemoval(mat, gaussianBlurParams, blurringParameters.getAmount());
                break;
            case MEDIAN_BLUR:
                MedianBlurParams medianBlurParams = new MedianBlurParams();
                medianBlurParams.setkSize(blurringParameters.getkSize());
                mat = NoiseRemover.applyNoiseRemoval(mat, medianBlurParams, blurringParameters.getAmount());
                break;
            case NO_BLURRING:
                default:
                    break;
        }
        switch (thresholdParameters.getThresholdMethodType()) {
            case ADAPTIVE_THRESHOLD:
                AdaptiveThresholdParams adaptiveThresholdParams = new AdaptiveThresholdParams();
                adaptiveThresholdParams.setAdaptiveMethod(thresholdParameters.getAdaptiveMethod());
                adaptiveThresholdParams.setThresholdType(thresholdParameters.getThresholdType());
                adaptiveThresholdParams.setBlockSize(thresholdParameters.getBlockSize());
                adaptiveThresholdParams.setMaxValue(thresholdParameters.getMaxValue());
                adaptiveThresholdParams.setC(thresholdParameters.getC());
                mat = BinaryConverter.applyThreshold(mat, adaptiveThresholdParams);
                break;
            case OTSU_BINARIZATION:
                OtsuBinarizationParams otsuBinarizationParams = new OtsuBinarizationParams();
                otsuBinarizationParams.setMaxValue(thresholdParameters.getMaxValue());
                otsuBinarizationParams.setThresh(thresholdParameters.getThresh());
                otsuBinarizationParams.setType(thresholdParameters.getType());
                mat = BinaryConverter.applyThreshold(mat, otsuBinarizationParams);
                break;
            case SIMPLE_THRESHOLD:
                SimpleThresholdParams simpleThresholdParams = new SimpleThresholdParams();
                simpleThresholdParams.setMaxValue(thresholdParameters.getMaxValue());
                simpleThresholdParams.setThresh(thresholdParameters.getThresh());
                simpleThresholdParams.setType(thresholdParameters.getType());
                mat = BinaryConverter.applyThreshold(mat, simpleThresholdParams);
                break;
            case NO_THRESHOLD:
                default:
                    break;
        }
        DilationParams dilationParams = new DilationParams();
        dilationParams.setDilation_elem(morphologicalParameters.getDilationElem());
        dilationParams.setDilation_size(morphologicalParameters.getDilationSize());
        ErosionParams erosionParams = new ErosionParams();
        erosionParams.setErosion_elem(morphologicalParameters.getErosionElem());
        erosionParams.setErosion_size(morphologicalParameters.getErosionSize());
        switch (morphologicalParameters.getMorphologicalType()) {
            case DILATION_EROSION:
                mat = MorphologicalOperations.applyDilation(mat, dilationParams, false, morphologicalParameters.getDilationAmount());
                mat = MorphologicalOperations.applyErosion(mat, erosionParams, false, morphologicalParameters.getErosionAmount());
                break;
            case EROSION_DILATION:
                mat = MorphologicalOperations.applyErosion(mat, erosionParams, false, morphologicalParameters.getErosionAmount());
                mat = MorphologicalOperations.applyDilation(mat, dilationParams, false, morphologicalParameters.getDilationAmount());
                break;
            case NO_OPERATION:
                default:
                    break;
        }
        return OpenCVUtil.matToBufferedImage(mat);
    }

}
