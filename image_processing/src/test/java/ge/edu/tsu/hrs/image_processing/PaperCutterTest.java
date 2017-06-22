package ge.edu.tsu.hrs.image_processing;

import ge.edu.tsu.hrs.image_processing.opencv.operation.PaperCutter;
import ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.cutpapper.CutPaperParams;
import org.junit.Ignore;
import org.junit.Test;

public class PaperCutterTest {

    private final String SRC_PATH = "test_images/imagecutter/receipt.jpg";

    private final String RESULT_PATH = "test_images/imagecutter/r_receipt.jpg";

    @Test
    @Ignore
    public void testCutPaperFromImage() {
        PaperCutter.cutPaperFromImage(SRC_PATH, RESULT_PATH, new CutPaperParams());
    }
}
