package ge.edu.tsu.hcrs.image_processing.characterdetect;

import ge.edu.tsu.hcrs.image_processing.characterdetect.detector.ContoursDetectorParams;
import ge.edu.tsu.hcrs.image_processing.characterdetect.detector.TextCutter;
import ge.edu.tsu.hcrs.image_processing.exception.TextAdapterSizeException;

public class Runner {

    private static final String srcImagePath = "test_images/src_images/sylfaen_30.jpg";

    private static final String srcTextPath = "test_images/src_images/sylfaen_30.txt";

    private static final String resultImagesPath = "test_images/result_images/sylfaen_30";

    public static void main(String[] args) {
        try {
            TextCutter.saveCutCharacters(srcImagePath, srcTextPath, resultImagesPath, new ContoursDetectorParams(), false);
        } catch (TextAdapterSizeException ex) {
            System.out.println("Expected Size - " + ex.getExpectedSize() + ", Result Size - " + ex.getResultSize());
        }
    }
}
