package ge.edu.tsu.hcrs.image_processing;

import ge.edu.tsu.hcrs.image_processing.opencv.LinesRemover;
import ge.edu.tsu.hcrs.image_processing.util.OpenCVUtil;
import org.junit.Ignore;
import org.junit.Test;

public class LineRemoverTest {

    private String srcPath = "test_images/lineremover/music.jpg";

    private String resultPath = "test_images/lineremover/r_music.jpg";

    @Test
    @Ignore
    public void removeLinesTest() {
        LinesRemover.removeLines(OpenCVUtil.getAbsPath(srcPath), OpenCVUtil.getAbsPath(resultPath));
    }
}
