package ge.edu.tsu.hcrs.control_panel.server.processor.imageprocessing;

import ge.edu.tsu.hcrs.image_processing.characterdetect.detector.ContoursDetectorParams;
import ge.edu.tsu.hcrs.image_processing.characterdetect.detector.TextCutter;
import ge.edu.tsu.hcrs.image_processing.exception.TextAdapterSizeException;
import ge.edu.tsu.hcrs.image_processing.opencv.ImageResizer;
import ge.edu.tsu.hcrs.image_processing.opencv.parameter.ImageResizerParams;
import ge.edu.tsu.hcrs.image_processing.util.OpenCVUtil;
import org.bytedeco.javacpp.opencv_core;

import java.awt.image.BufferedImage;

public class ImageProcessingProcessor {

    public void cutAndSaveCharactersFromText(String srcImagePath, String srcTextPath, String resultImagesPath, ContoursDetectorParams contoursDetectorParams, boolean saveAnyway) {
        try {
            TextCutter.saveCutCharacters(srcImagePath, srcTextPath, resultImagesPath, contoursDetectorParams, saveAnyway);
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
            params.setWidht(0);
            params.setHeight(0);
        } else {
            params.setFx(0);
            params.setFy(0);
            params.setWidht((int) x);
            params.setHeight((int) y);
        }
        opencv_core.Mat resultMat = ImageResizer.resize(srcMat, params);
        return OpenCVUtil.matToBufferedImage(resultMat);
    }
}
