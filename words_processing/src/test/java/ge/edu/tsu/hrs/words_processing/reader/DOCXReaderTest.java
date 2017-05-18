package ge.edu.tsu.hrs.words_processing.reader;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class DOCXReaderTest {

	@Test
	public void testGetTextFromDOC() {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("test.docx").getFile());
		try {
			DOCXReader reader = new DOCXReader();
			String text = reader.getTextFromDOCX(file.getAbsolutePath());
			Assert.assertEquals("Test\nOK.", text);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
