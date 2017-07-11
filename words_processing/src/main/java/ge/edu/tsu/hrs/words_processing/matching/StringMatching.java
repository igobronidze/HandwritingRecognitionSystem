package ge.edu.tsu.hrs.words_processing.matching;

import java.util.ArrayList;
import java.util.List;

public class StringMatching {

    public static MatchingResult getNearestStrings(MatchingInput input) {
        long currMS = System.currentTimeMillis();
        List<String> resultString = new ArrayList<>();
        float min = Float.MAX_VALUE;
        for (String text : input.getTexts().get(input.getExemp().length())) {
            float distance = LevenshteinDistance.countDistance(input.getExemp(), text, input);
            if (distance < min) {
                min = distance;
                resultString = new ArrayList<>();
                resultString.add(text);
            } else if (distance == min) {
                resultString.add(text);
            }
            if (distance == 0) {
                break;
            }
        }
        MatchingResult result = new MatchingResult();
        result.setDistance(min);
        result.setMatches(resultString);
        result.setDuration(System.currentTimeMillis() - currMS);
        return result;
    }
}
