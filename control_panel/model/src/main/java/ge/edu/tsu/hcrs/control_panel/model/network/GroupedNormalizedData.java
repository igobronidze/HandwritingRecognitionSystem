package ge.edu.tsu.hcrs.control_panel.model.network;

public class GroupedNormalizedData {

	private int width;

	private int height;

	private CharSequence charSequence;

	private String trainingSetGeneration;

	private int count;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public CharSequence getCharSequence() {
		return charSequence;
	}

	public void setCharSequence(CharSequence charSequence) {
		this.charSequence = charSequence;
	}

	public String getTrainingSetGeneration() {
		return trainingSetGeneration;
	}

	public void setTrainingSetGeneration(String trainingSetGeneration) {
		this.trainingSetGeneration = trainingSetGeneration;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
