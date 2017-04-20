package ge.edu.tsu.hrs.control_panel.server.processor.imageprocessing;

import ge.edu.tsu.hrs.control_panel.model.imageprocessing.BlurringParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.MorphologicalParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.TextCutterParameters;
import ge.edu.tsu.hrs.control_panel.model.imageprocessing.ThresholdParameters;
import ge.edu.tsu.hrs.control_panel.server.util.CharacterUtil;
import ge.edu.tsu.hrs.image_processing.characterdetect.detector.ContoursDetector;
import ge.edu.tsu.hrs.image_processing.characterdetect.detector.TextCutterParams;
import ge.edu.tsu.hrs.image_processing.characterdetect.model.Contour;
import ge.edu.tsu.hrs.image_processing.characterdetect.model.TextAdapter;
import ge.edu.tsu.hrs.image_processing.characterdetect.model.TextRow;
import ge.edu.tsu.hrs.image_processing.characterdetect.util.ContourUtil;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageProcessingProcessor {

    public List<BufferedImage> getCutSymbols(BufferedImage srcImage, TextCutterParameters parameters) {
        TextCutterParams textCutterParams = new TextCutterParams();
        textCutterParams.setCheckedRGBMaxValue(parameters.getCheckedRGBMaxValue());
        textCutterParams.setCheckNeighborRGBMaxValue(parameters.getCheckNeighborRGBMaxValue());
        textCutterParams.setPercentageOfSameForJoining(parameters.getPercentageOfSameForJoining());
        textCutterParams.setPercentageOfSamesForOneRow(parameters.getPercentageOfSamesForOneRow());
        textCutterParams.setUseJoiningFunctional(parameters.isUseJoiningFunctional());
        TextAdapter textAdapter = ContoursDetector.detectContours(srcImage, textCutterParams);
        List<BufferedImage> images = new ArrayList<>();
        for (TextRow textRow : textAdapter.getRows()) {
            for (Contour contour : textRow.getContours()) {
                BufferedImage image = ContourUtil.getBufferedImageFromContour(contour);
                images.add(image);
            }
        }
        return images;
    }

    public List<String> processTextForImage(String text, boolean doubleQuoteAsTwoChar) {
        List<String> symbols = new ArrayList<>();
        for (char c : text.toCharArray()) {
            if (!isUnnecessaryCharacter(c)) {
                if (doubleQuoteAsTwoChar && (c == '"' || c == '“' || c == '”')) {
                    symbols.add("'");
                    symbols.add("'");
                } else {
                    symbols.add("" + c);
                }
            }
        }
        return symbols;
    }

    public void saveCutSymbols(List<BufferedImage> images, List<String> text, String directoryPath) {
        try {
            File directory = new File(directoryPath);
            int nextId = 1;
            for (File f : directory.listFiles()) {
                if (f.isFile()) {
                    try {
                        String fileNameWithoutExtension = f.getName().replaceFirst("[.][^.]+$", "");
                        String id = fileNameWithoutExtension.split("_")[0];
                        if (Integer.parseInt(id) >= nextId) {
                            nextId = Integer.parseInt(id) + 1;
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
            for (int i = 0; i < images.size(); i++) {
                char c = text.get(i).length() > 0 ? text.get(i).charAt(0) : ' ';
                ImageIO.write(images.get(i), "png", new File(directoryPath + "/" + (nextId) + "_" + CharacterUtil.getCharValueForFileName(c) + ".png"));
                nextId++;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static BufferedImage resizeImage(BufferedImage srcImage, boolean scaleResizing, double x, double y) {
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

    private static boolean isUnnecessaryCharacter(char c) {
        return c == ' ' || c == '\n' || c == '\r';
    }
}
