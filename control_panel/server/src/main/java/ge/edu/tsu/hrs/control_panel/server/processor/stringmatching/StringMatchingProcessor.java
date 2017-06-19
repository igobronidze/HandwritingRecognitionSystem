package ge.edu.tsu.hrs.control_panel.server.processor.stringmatching;

import ge.edu.tsu.hrs.control_panel.model.book.Word;
import ge.edu.tsu.hrs.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.hrs.control_panel.server.caching.CachedWords;
import ge.edu.tsu.hrs.control_panel.server.processor.systemparameter.SystemParameterProcessor;
import ge.edu.tsu.hrs.words_processing.matching.MatchingInput;
import ge.edu.tsu.hrs.words_processing.matching.MatchingResult;
import ge.edu.tsu.hrs.words_processing.matching.StringMatching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StringMatchingProcessor {

    private final SystemParameterProcessor systemParameterProcessor = new SystemParameterProcessor();

    private Parameter stringMatchingInsertRate = new Parameter("stringMatchingInsertRate", "1.0");

    private Parameter stringMatchingDeleteRate = new Parameter("stringMatchingDeleteRate", "1.0");

    private Parameter stringMatchingChangeRate = new Parameter("stringMatchingChangeRate", "1.0");

    public String getNearestString(String text, List<List<Float>> activations, Map<Integer, Character> indexToCharMap) {
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
        input.setInsertRate(systemParameterProcessor.getFloatParameterValue(stringMatchingInsertRate));
        input.setDeleteRate(systemParameterProcessor.getFloatParameterValue(stringMatchingDeleteRate));
        input.setChangeRate(systemParameterProcessor.getFloatParameterValue(stringMatchingChangeRate));
        input.setChangePossibilities(getPossibilities(activations, indexToCharMap));
        MatchingResult result = StringMatching.getNearestStrings(input);
        List<String> matches = result.getMatches();
        System.out.println("Found " + matches.size() + " match(es)");
        if (matches.isEmpty()) {
            return "";
        }
        return matches.get(0);
    }

    private List<Map<Character, Float>> getPossibilities(List<List<Float>> activations, Map<Integer, Character> indexToCharMap) {
        List<Map<Character, Float>> possibilities = new ArrayList<>();
        for (List<Float> activation : activations) {
            Map<Character, Float> possibility = new HashMap<>();
            for (int i = 0; i < activation.size(); i++) {
                possibility.put(indexToCharMap.get(i), 1.0F - activation.get(i));
            }
            possibilities.add(possibility);
        }
        return possibilities;
    }
}
