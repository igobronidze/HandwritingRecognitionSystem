package ge.edu.tsu.hcrs.image_processing.opencv;

import ge.edu.tsu.hcrs.image_processing.opencv.parameter.characterdetector.SimpleCharacterDetectorParams;

public class Runner {

    private static final String srcImagePath = "test_images/src_images/sylfaen_30.jpg";

    private static final String resultImagesPath = "test_images/result_images/sylfaen_30";

    public static void main(String[] args) {
        CharactersDetector.detectCharacters(srcImagePath, resultImagesPath, new SimpleCharacterDetectorParams());
    }
}
