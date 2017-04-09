package ge.edu.tsu.hrs.control_panel.server.processor.imageprocessing;

import ge.edu.tsu.hrs.image_processing.characterdetect.detector.TextCutterParams;
import ge.edu.tsu.hrs.image_processing.characterdetect.detector.TextCutter;
import ge.edu.tsu.hrs.image_processing.exception.TextAdapterSizeException;
import ge.edu.tsu.hrs.image_processing.opencv.operation.ImageResizer;
import ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.ImageResizerParams;
import ge.edu.tsu.hrs.image_processing.opencv.service.ImageCleaner;
import ge.edu.tsu.hrs.image_processing.opencv.service.condition.ImageCondition;
import ge.edu.tsu.hrs.image_processing.util.OpenCVUtil;
import org.bytedeco.javacpp.opencv_core;

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

    public BufferedImage cleanImage(BufferedImage srcImage, ImageCondition imageCondition) {
        return ImageCleaner.cleanImage(srcImage, imageCondition);
    }
}
