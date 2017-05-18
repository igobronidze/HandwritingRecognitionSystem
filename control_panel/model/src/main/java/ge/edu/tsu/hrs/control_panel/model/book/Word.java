package ge.edu.tsu.hrs.control_panel.model.book;

public class Word {

	private String word;

	public Word(String word) {
		this.word = word;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Word)) return false;

		Word word1 = (Word) o;

		return getWord() != null ? getWord().equals(word1.getWord()) : word1.getWord() == null;

	}

	@Override
	public int hashCode() {
		return getWord() != null ? getWord().hashCode() : 0;
	}
}
