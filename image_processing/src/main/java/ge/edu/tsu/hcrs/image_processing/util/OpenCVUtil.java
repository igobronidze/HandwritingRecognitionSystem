package ge.edu.tsu.hcrs.image_processing.util;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;

import java.awt.image.BufferedImage;
import java.io.File;

public class OpenCVUtil {

    public static String getAbsPath(String relPath) {
        File file = new File(relPath);
        return file.getAbsolutePath();
    }

    public static void saveImage(opencv_core.IplImage image, String absPath) {
        opencv_imgcodecs.cvSaveImage(absPath , image);
    }

    public static opencv_core.IplImage loadImage(String absPath) {
        return opencv_imgcodecs.cvLoadImage(absPath);
    }

    public static opencv_core.IplImage toIplImage(BufferedImage bufImage) {
        OpenCVFrameConverter.ToIplImage iplConverter = new OpenCVFrameConverter.ToIplImage();
        Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
        opencv_core.IplImage iplImage = iplConverter.convert(java2dConverter.convert(bufImage));
        return iplImage;
    }

    public static BufferedImage IplImageToBufferedImage(opencv_core.IplImage src) {
        OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
        Java2DFrameConverter paintConverter = new Java2DFrameConverter();
        Frame frame = grabberConverter.convert(src);
        return paintConverter.getBufferedImage(frame,1);
    }
}
