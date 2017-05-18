package ge.edu.tsu.hrs.words_processing.reader;

import ge.edu.tsu.hrs.words_processing.exception.ReaderException;
import ge.edu.tsu.hrs.words_processing.reader.parser.ParserPattern;
import ge.edu.tsu.hrs.words_processing.reader.parser.WordsParser;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class DOCXReader implements Reader {

	@Override
	public List<String> getAllWord(String path, ParserPattern parserPattern) throws ReaderException {
		try {
			String text = getTextFromDOCX(path);
			return WordsParser.parse(text, parserPattern);
		} catch (IOException ex) {
			throw new ReaderException(ex);
		}
	}

	String getTextFromDOCX(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		XWPFDocument document = new XWPFDocument(fis);
		XWPFWordExtractor extractor = new XWPFWordExtractor(document);
		String text = extractor.getText();
		fis.close();
		return text.trim();
	}
}
