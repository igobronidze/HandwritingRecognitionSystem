package ge.edu.tsu.hcrs.image_processing.characterdetect;

public class Runner {

    private static final String srcImagePath = "test_images/src_images/sylfaen_15.jpg";

    private static final String resultImagesPath = "test_images/result_images/sylfaen_15";

    public static void main(String[] args) {
        CharacterDetector.detectCharacters(srcImagePath, resultImagesPath);
    }
}
