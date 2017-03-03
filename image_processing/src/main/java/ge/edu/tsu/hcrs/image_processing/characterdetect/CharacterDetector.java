package ge.edu.tsu.hcrs.image_processing.characterdetect;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class CharacterDetector {

    public static void detectCharacters(String srcImagePath, String resultImagesPath) {
        try {
            BufferedImage image = ImageIO.read(new File(srcImagePath));
            List<Contour> contours = ContoursDetector.detectContours(image);
            for (int i = 0; i < contours.size(); i++) {
                BufferedImage resultImage = ContourUtil.getBufferedImageFromContour(contours.get(i));
                ImageIO.write(resultImage, "jpg", new File(resultImagesPath + "/" + i + ".jpg"));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
