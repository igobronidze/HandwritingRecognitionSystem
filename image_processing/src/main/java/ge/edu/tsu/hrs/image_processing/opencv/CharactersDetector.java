package ge.edu.tsu.hrs.image_processing.opencv;

import ge.edu.tsu.hrs.image_processing.opencv.parameter.characterdetector.CharacterDetectorParams;
import ge.edu.tsu.hrs.image_processing.opencv.parameter.characterdetector.SimpleCharacterDetectorParams;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;

public class CharactersDetector {

    public static void detectCharacters(String srcImagePath, String resultImagesPath, CharacterDetectorParams characterDetectorParams) {
        opencv_core.Mat srcMat = opencv_imgcodecs.imread(srcImagePath);

        if (characterDetectorParams instanceof SimpleCharacterDetectorParams) {
            applySimpleCharacterDetector(srcMat, resultImagesPath, (SimpleCharacterDetectorParams)characterDetectorParams);
        }

    }

    private static void applySimpleCharacterDetector(opencv_core.Mat srcMat, String resultImagesPath, SimpleCharacterDetectorParams params) {
        opencv_core.Mat mat = new opencv_core.Mat();
        if (params.isConvertToGray()) {
            opencv_imgproc.cvtColor(srcMat, mat, opencv_imgproc.CV_RGB2GRAY);
        } else {
            mat = srcMat.clone();
        }
        opencv_imgproc.Canny(mat, mat, params.getCannyMinVal(), params.getCannyMaxValue());
        opencv_core.MatVector contours = new opencv_core.MatVector();
        opencv_imgproc.findContours(mat, contours, params.getFindContoursMode(), params.getFindContoursMethod());
        for (int i = 0; i < contours.size(); i++) {
            opencv_core.Mat mask = new opencv_core.Mat(mat.rows(), mat.cols(), opencv_core.CV_8UC3);
            opencv_imgproc.drawContours(mask, contours, i, new opencv_core.Scalar(255, 255, 255, 100), -1, 8, null, 0, null);
            opencv_core.Mat crop = new opencv_core.Mat(srcMat.rows(), srcMat.cols(), opencv_core.CV_8UC3);
            crop.setTo(new opencv_core.Mat(new opencv_core.Scalar(0, 0, 0, 100)));
            srcMat.copyTo(crop, mask);
            opencv_imgcodecs.imwrite(resultImagesPath + "/" + i + ".jpg", crop);
        }
    }
}
