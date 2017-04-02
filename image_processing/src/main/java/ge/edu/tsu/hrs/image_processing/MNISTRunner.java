package ge.edu.tsu.hrs.image_processing;

import ge.edu.tsu.hrs.image_processing.opencv.BinaryConverter;
import ge.edu.tsu.hrs.image_processing.opencv.parameter.threshold.SimpleThresholdParams;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;

import java.io.File;

public class MNISTRunner {

    private static final String trainingSrcDirectoryPath = "D:\\hrs\\HRSImageData\\original_images\\mnist\\training\\";

    private static final String testingSrcDirectoryPath = "D:\\hrs\\HRSImageData\\original_images\\mnist\\testing\\";

    private static final String trainingResultDirectoryPath = "D:\\hrs\\HRSImageData\\cut_symbols\\mnist\\training\\";

    private static final String testingResultDirectoryPath = "D:\\hrs\\HRSImageData\\cut_symbols\\mnist\\testing\\";

    public static void main(String[] args) {
        File testingRoot = new File(testingSrcDirectoryPath);
        for (File directory : testingRoot.listFiles()) {
            applyThresholdForDirectory(directory.getAbsolutePath(), testingResultDirectoryPath + directory.getName());
        }
        File trainingRoot = new File(trainingSrcDirectoryPath);
        for (File directory : trainingRoot.listFiles()) {
            applyThresholdForDirectory(directory.getAbsolutePath(), trainingResultDirectoryPath + directory.getName());
        }
    }

    private static void applyThresholdForDirectory(String srcPath, String resultPath) {
        File resultFile = new File(resultPath);
        resultFile.mkdir();
        File directory = new File(srcPath);
        for (File file : directory.listFiles()) {
            opencv_core.Mat resultMat;
            opencv_core.Mat srcMat = opencv_imgcodecs.imread(file.getAbsolutePath(), opencv_core.CV_8UC1);
            SimpleThresholdParams thresholdParams = new SimpleThresholdParams();
            thresholdParams.setType(opencv_imgproc.THRESH_BINARY_INV);
            resultMat = BinaryConverter.applyThreshold(srcMat, thresholdParams);
            opencv_imgcodecs.imwrite(resultPath + "\\" + file.getName().substring(0, file.getName().length() - 4) + "_" + directory.getName() + ".png", resultMat);
        }
    }
}
