package ge.edu.tsu.hrs.words_processing.pdf_reader.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordsParser {

	public static List<String> parse(String text, ParserPattern parserPattern) {
		Pattern pattern = Pattern.compile(parserPattern.getRegex());
		Matcher matcher = pattern.matcher(text);
		List<String> result = new ArrayList<>();
		while (matcher.find()) {
			result.add(matcher.group());
		}
		return result;
	}
}
