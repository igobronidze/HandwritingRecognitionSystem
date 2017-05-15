package ge.edu.tsu.hrs.words_processing;

import ge.edu.tsu.hrs.words_processing.pdf_reader.parser.GeorgianWordsParserPattern;
import ge.edu.tsu.hrs.words_processing.pdf_reader.parser.WordsParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class WordsParserTest {

	private String text;

	private List<String> worlds = new ArrayList<>();

	@Before
	public void init() {
		text = "ტესტ, ტექსტი\n რამე!... საინტერესო, ხოოო 27-ე\r";
		worlds.add("ტესტ");
		worlds.add("ტექსტი");
		worlds.add("რამე");
		worlds.add("საინტერესო");
		worlds.add("ხოოო");
	}

	@Test
	public void testParse() {
		Assert.assertEquals(worlds, WordsParser.parse(text, new GeorgianWordsParserPattern()));
	}
}
