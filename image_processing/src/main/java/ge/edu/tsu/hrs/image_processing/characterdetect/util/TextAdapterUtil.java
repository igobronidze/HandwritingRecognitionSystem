package ge.edu.tsu.hrs.image_processing.characterdetect.util;

import ge.edu.tsu.hrs.image_processing.characterdetect.model.TextAdapter;
import ge.edu.tsu.hrs.image_processing.characterdetect.model.TextRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TextAdapterUtil {

    public static boolean isSpace(TextAdapter textAdapter, int distance, float minAverageSymbolPerSpace, float maxAverageSymbolPerSpace) {
        if (textAdapter.getMinDistanceForSpace() == -1) {
            textAdapter.setMinDistanceForSpace(getMinDistanceForSpace(textAdapter, minAverageSymbolPerSpace, maxAverageSymbolPerSpace));
        }
        return textAdapter.getMinDistanceForSpace() <= distance;
    }

    private static int getMinDistanceForSpace(TextAdapter textAdapter, float minAverageSymbolPerSpace, float maxAverageSymbolPerSpace) {
        Map<Integer, Integer> distanceBetweenContours = textAdapter.getDistanceBetweenContours();
        List<DistanceBetweenContours> distanceBetweenContoursList = new ArrayList<>();
        for (Integer distance : distanceBetweenContours.keySet()) {
            distanceBetweenContoursList.add(new DistanceBetweenContours(distance, distanceBetweenContours.get(distance)));
        }
        Collections.sort(distanceBetweenContoursList);
        int maxIndex;
        int max;
        Set<Integer> used = new HashSet<>();
        int count = countCharactersFromTextAdapter(textAdapter);
        while (true) {
            max = -1;
            maxIndex = 1;
            for (int i = 1; i < distanceBetweenContoursList.size(); i++) {
                if (!used.contains(i) && distanceBetweenContoursList.get(i).distance - distanceBetweenContoursList.get(i - 1).distance >= max) {
                    maxIndex = i;
                    max = distanceBetweenContoursList.get(i).distance - distanceBetweenContoursList.get(i - 1).distance;
                }
            }
            if (max == -1) {
                break;
            }
            used.add(maxIndex);
            int spaces = 0;
            for (int i = maxIndex; i < distanceBetweenContoursList.size(); i++) {
                spaces += distanceBetweenContoursList.get(i).amount;
            }
            if ((float)count / spaces >= minAverageSymbolPerSpace && (float)count / spaces <= maxAverageSymbolPerSpace) {
                return distanceBetweenContoursList.get(maxIndex).distance;
            }
        }
        int spaces = count / ((int)(minAverageSymbolPerSpace + maxAverageSymbolPerSpace) / 2);
        for (int i = distanceBetweenContoursList.size() - 1; i >= 0; i--) {
            spaces -= distanceBetweenContoursList.get(i).amount;
            if (spaces <= 0) {
                return distanceBetweenContoursList.get(i).distance;
            }
        }
        return 10;
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
