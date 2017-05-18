package ge.edu.tsu.hrs.words_processing.reader;

import ge.edu.tsu.hrs.words_processing.exception.ReaderException;
import ge.edu.tsu.hrs.words_processing.reader.parser.ParserPattern;
import ge.edu.tsu.hrs.words_processing.reader.parser.WordsParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PDFReader implements Reader {

	@Override
	public List<String> getAllWord(String path, ParserPattern parserPattern) throws ReaderException {
		try {
			String text = getTextFromPDF(path);
			return WordsParser.parse(text, parserPattern);
		} catch (IOException ex) {
			throw new ReaderException(ex);
		}
	}

	String getTextFromPDF(String path) throws IOException {
		File file = new File(path);
		PDDocument document = PDDocument.load(file);
		PDFTextStripper pdfTextStripper = new PDFTextStripper();
		String text = pdfTextStripper.getText(document);
		document.close();
		return text.trim();
	}
}
