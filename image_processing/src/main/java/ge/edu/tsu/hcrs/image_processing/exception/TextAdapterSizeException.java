package ge.edu.tsu.hcrs.image_processing.exception;

public class TextAdapterSizeException extends IPException {

    private int expectedSize;

    private int resultSize;

    public TextAdapterSizeException() {
    }

    public TextAdapterSizeException(String msg) {
        super(msg);
    }

    public TextAdapterSizeException(int expectedSize, int resultSize) {
        this.expectedSize = expectedSize;
        this.resultSize = resultSize;
    }

    public int getExpectedSize() {
        return expectedSize;
    }

    public void setExpectedSize(int expectedSize) {
        this.expectedSize = expectedSize;
    }

    public int getResultSize() {
        return resultSize;
    }

    public void setResultSize(int resultSize) {
        this.resultSize = resultSize;
    }
}
