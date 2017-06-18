package ge.edu.tsu.hrs.control_panel.server.processor.stringmatching;

import ge.edu.tsu.hrs.control_panel.model.book.Word;
import ge.edu.tsu.hrs.control_panel.server.caching.CachedWords;
import ge.edu.tsu.hrs.words_processing.matching.MatchingInput;
import ge.edu.tsu.hrs.words_processing.matching.MatchingResult;
import ge.edu.tsu.hrs.words_processing.matching.StringMatching;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StringMatchingProcessor {

    public String getNearestString(String text) {
        Set<Word> cachedWords = CachedWords.getWords();
        if (cachedWords == null) {
            CachedWords.loadData();
            cachedWords = CachedWords.getWords();
        }
        List<String> texts = new ArrayList<>();
        for (Word word : cachedWords) {
            texts.add(word.getWord());
        }
        MatchingInput input = new MatchingInput();
        input.setExemp(text);
        input.setTexts(texts);
        MatchingResult result = StringMatching.getNearestStrings(input);
        List<String> matches = result.getMatches();
        System.out.println("Found " + matches.size() + " match(es)");
        if (matches.isEmpty()) {
            return "";
        }
        return matches.get(0);
    }
}
