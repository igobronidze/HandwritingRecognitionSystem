package ge.edu.tsu.hrs.image_processing;

import ge.edu.tsu.hrs.image_processing.opencv.operation.LinesRemover;
import ge.edu.tsu.hrs.image_processing.util.OpenCVUtil;
import org.junit.Ignore;
import org.junit.Test;

public class LineRemoverTest {

    private final String SRC_PATH = "test_images/lineremover/1.jpg";

    private final String RESULT_PATH = "test_images/lineremover/2.jpg";

    @Test
    @Ignore
    public void removeLinesTest() {
        LinesRemover.removeLines(OpenCVUtil.getAbsPath(SRC_PATH), OpenCVUtil.getAbsPath(RESULT_PATH));
    }
}
