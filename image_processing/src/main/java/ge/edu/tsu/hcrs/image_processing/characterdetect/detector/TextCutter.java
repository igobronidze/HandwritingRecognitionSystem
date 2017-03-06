package ge.edu.tsu.hcrs.image_processing.characterdetect.detector;

import ge.edu.tsu.hcrs.image_processing.characterdetect.model.Contour;
import ge.edu.tsu.hcrs.image_processing.characterdetect.model.TextAdapter;
import ge.edu.tsu.hcrs.image_processing.characterdetect.model.TextRow;
import ge.edu.tsu.hcrs.image_processing.characterdetect.util.ContourUtil;
import ge.edu.tsu.hcrs.image_processing.characterdetect.util.TextAdapterUtil;
import ge.edu.tsu.hcrs.image_processing.exception.TextAdapterSizeException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TextCutter {

    public static void saveCutCharacters(String srcImagePath, String srcTextPath, String resultImagesPath, ContoursDetectorParams contoursDetectorParams, boolean saveAnyway) throws TextAdapterSizeException {
        try {
            BufferedImage image = ImageIO.read(new File(srcImagePath));
            BufferedReader br = new BufferedReader(new FileReader(srcTextPath));
            String text = "";
            String line;
            while ((line = br.readLine()) != null) {
                text += line + System.lineSeparator();
            }
            TextAdapter textAdapter = ContoursDetector.detectContours(image, contoursDetectorParams);
            int resultCount = TextAdapterUtil.countCharacters(textAdapter);
            int expectedCount = 0;
            for (char c : text.toCharArray()) {
                if (!isUnnecessaryCharacter(c)) {
                    expectedCount++;
                }
            }
            if (resultCount != expectedCount) {
                if (saveAnyway) {
                    int i = 1;
                    for (TextRow textRow : textAdapter.getRows()) {
                        for (Contour contour : textRow.getContours()) {
                            BufferedImage resultImage = ContourUtil.getBufferedImageFromContour(contour);
                            ImageIO.write(resultImage, "jpg", new File(resultImagesPath + "/" + i + ".jpg"));
                            i++;
                        }
                    }
                }
                throw new TextAdapterSizeException(expectedCount, resultCount);
            } else {
                int i = 0;
                for (TextRow textRow : textAdapter.getRows()) {
                    for (Contour contour : textRow.getContours()) {
                        while (i < text.length() && isUnnecessaryCharacter(text.charAt(i))) {
                            i++;
                        }
                        BufferedImage resultImage = ContourUtil.getBufferedImageFromContour(contour);
                        ImageIO.write(resultImage, "jpg", new File(resultImagesPath + "/" + (i + 1) + "_" + getForbiddenCharsValue(text.charAt(i)) + ".jpg"));
                        i++;
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static boolean isUnnecessaryCharacter(char c) {
        return c == ' ' || c == '\n' || c == '\r';
    }

    private static String getForbiddenCharsValue(char c) {
        String s;
        switch (c) {
            case '?' :
                s = "question_mark";
                break;
            case '<' :
                s = "less_than";
                break;
            case '>' :
                s = "greater_than";
                break;
            case ':' :
                s = "colon";
                break;
            case '"' :
                s = "double_quote";
                break;
            case '/' :
                s = "forward_slash";
                break;
            case '\\' :
                s = "backslash";
                break;
            case '|' :
                s = "vertical_bar";
                break;
            case '*' :
                s = "asterisk";
                break;
            default:
                s = "" + c;
        }
        return s;
    }
}
