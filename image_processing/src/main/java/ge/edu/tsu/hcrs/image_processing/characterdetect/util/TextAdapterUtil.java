package ge.edu.tsu.hcrs.image_processing.characterdetect.util;

import ge.edu.tsu.hcrs.image_processing.characterdetect.model.TextAdapter;
import ge.edu.tsu.hcrs.image_processing.characterdetect.model.TextRow;

public class TextAdapterUtil {

    public static int countCharactersFromTextAdapter(TextAdapter textAdapter) {
        int count = 0;
        for (TextRow textRow : textAdapter.getRows()) {
            count += textRow.getContours().size();
        }
        return count;
    }

    public static int countCharactersFromText(String text) {
        int count = 0;
        for (char c : text.toCharArray()) {
            if (!isUnnecessaryCharacter(c)) {
                count++;
            }
            if (isDoubleCharacter(c)) {
                count++;
            }
        }
        return count;
    }

    public static boolean isUnnecessaryCharacter(char c) {
        return c == ' ' || c == '\n' || c == '\r';
    }

    private static boolean isDoubleCharacter(char c) {
        if (c == '"') {
            return true;
        }
        return false;
    }
}
