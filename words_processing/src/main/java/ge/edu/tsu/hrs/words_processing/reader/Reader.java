package ge.edu.tsu.hrs.words_processing.reader;

import ge.edu.tsu.hrs.words_processing.exception.ReaderException;
import ge.edu.tsu.hrs.words_processing.reader.parser.ParserPattern;

import java.util.List;

public interface Reader {

	List<String> getAllWord(String path, ParserPattern parserPattern) throws ReaderException;
}
