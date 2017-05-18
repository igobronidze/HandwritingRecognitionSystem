package ge.edu.tsu.hrs.words_processing.exception;

public class ReaderException extends Exception {

	private String messageKey;

	public ReaderException() {}

	public ReaderException(String messageKey) {
		this.messageKey = messageKey;
	}

	public ReaderException(Exception ex) {
		super(ex);
	}

	public ReaderException(String messageKey, Exception ex) {
		super(ex);
		this.messageKey = messageKey;
	}

	public String getMessageKey() {
		return messageKey;
	}
}
