package ge.edu.tsu.hrs.words_processing.exception;

public class PDFReaderException extends Exception {

	private String messageKey;

	public PDFReaderException() {}

	public PDFReaderException(String messageKey) {
		this.messageKey = messageKey;
	}

	public PDFReaderException(Exception ex) {
		super(ex);
	}

	public PDFReaderException(String messageKey, Exception ex) {
		super(ex);
		this.messageKey = messageKey;
	}

	public String getMessageKey() {
		return messageKey;
	}
}
