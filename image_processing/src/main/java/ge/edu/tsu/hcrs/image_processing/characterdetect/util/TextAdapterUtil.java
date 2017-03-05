package ge.edu.tsu.hcrs.image_processing.characterdetect.util;

import ge.edu.tsu.hcrs.image_processing.characterdetect.model.TextAdapter;
import ge.edu.tsu.hcrs.image_processing.characterdetect.model.TextRow;

public class TextAdapterUtil {

    public static int countCharacters(TextAdapter textAdapter) {
        int count = 0;
        for (TextRow textRow : textAdapter.getRows()) {
            count += textRow.getContours().size();
        }
        return count;
    }
}
