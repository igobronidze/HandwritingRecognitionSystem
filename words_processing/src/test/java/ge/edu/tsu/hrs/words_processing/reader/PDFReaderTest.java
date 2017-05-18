package ge.edu.tsu.hrs.words_processing.reader;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class PDFReaderTest {

	@Test
	public void testGetTextFromPDF() {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("test.pdf").getFile());
		try {
			PDFReader reader = new PDFReader();
			String text = reader.getTextFromPDF(file.getAbsolutePath());
			Assert.assertEquals("This is a test PDF document. \r\nIf you can read this, you have Adobe Acrobat Reader installed on your computer.", text);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
