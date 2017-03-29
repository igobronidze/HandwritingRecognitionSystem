package ge.edu.tsu.hrs.image_processing;

import ge.edu.tsu.hrs.image_processing.opencv.PaperCutter;
import ge.edu.tsu.hrs.image_processing.opencv.parameter.cutpapper.CutPaperParams;
import org.junit.Ignore;
import org.junit.Test;

public class PaperCutterTest {

    private String srcPath = "test_images/imagecutter/receipt.jpg";

    private String resultPath = "test_images/imagecutter/r_receipt.jpg";

    @Test
    @Ignore
    public void testCutPaperFromImage() {
        PaperCutter.cutPaperFromImage(srcPath, resultPath, new CutPaperParams());
    }
}
