package ge.edu.tsu.hrs.words_processing.reader.parser;

public class GeorgianWordsParserPattern implements ParserPattern {

	@Override
	public String getRegex() {
		return "([ა-ჰ]{2,})";
	}
}
