package ge.edu.tsu.hrs.image_processing.characterdetect.util;

import ge.edu.tsu.hrs.image_processing.characterdetect.model.TextAdapter;
import ge.edu.tsu.hrs.image_processing.characterdetect.model.TextRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TextAdapterUtil {

    public static boolean isSpace(TextAdapter textAdapter, int distance, int averageSymbolPerSpace) {
        if (textAdapter.getMinDistanceForSpace() == -1) {
            textAdapter.setMinDistanceForSpace(getMinDistanceForSpace(textAdapter, averageSymbolPerSpace));
        }
        return textAdapter.getMinDistanceForSpace() <= distance;
    }

    private static int getMinDistanceForSpace(TextAdapter textAdapter, int averageSymbolPerSpace) {
        Map<Integer, Integer> distanceBetweenContours = textAdapter.getDistanceBetweenContours();
        List<DistanceBetweenContours> distanceBetweenContoursList = new ArrayList<>();
        for (Integer distance : distanceBetweenContours.keySet()) {
            distanceBetweenContoursList.add(new DistanceBetweenContours(distance, distanceBetweenContours.get(distance)));
        }
        Collections.sort(distanceBetweenContoursList);
        int spaces = countCharactersFromTextAdapter(textAdapter) / averageSymbolPerSpace;
        try {
            return distanceBetweenContoursList.get(distanceBetweenContoursList.size() - spaces).distance;
        } catch (IndexOutOfBoundsException ex) {
            return 1;
        }
    }

    private static int countCharactersFromTextAdapter(TextAdapter textAdapter) {
        int count = 0;
        for (TextRow textRow : textAdapter.getRows()) {
            count += textRow.getContours().size();
        }
        return count;
    }

    private static class DistanceBetweenContours implements Comparable<DistanceBetweenContours> {

        private int distance;

        private int amount;

        private DistanceBetweenContours(int distance, int amount) {
            this.distance = distance;
            this.amount = amount;
        }

        @Override
        public int compareTo(DistanceBetweenContours o) {
            return Integer.compare(this.distance, o.distance);
        }
    }
}
