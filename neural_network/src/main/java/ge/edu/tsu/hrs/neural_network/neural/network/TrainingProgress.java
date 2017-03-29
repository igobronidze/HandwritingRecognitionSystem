package ge.edu.tsu.hrs.neural_network.neural.network;

public class TrainingProgress {

	private long updatePerIteration;

	private float currentSquaredError;

	private long currentIterations;

	private long currentDuration;

	public TrainingProgress() {
	}

	public TrainingProgress(long updatePerIteration, float currentSquaredError, long currentIterations, long currentDuration) {
		this.updatePerIteration = updatePerIteration;
		this.currentSquaredError = currentSquaredError;
		this.currentIterations = currentIterations;
		this.currentDuration = currentDuration;
	}

	public long getUpdatePerIteration() {
		return updatePerIteration;
	}

	public void setUpdatePerIteration(long updatePerIteration) {
		this.updatePerIteration = updatePerIteration;
	}

	public float getCurrentSquaredError() {
		return currentSquaredError;
	}

	public void setCurrentSquaredError(float currentSquaredError) {
		this.currentSquaredError = currentSquaredError;
	}

	public long getCurrentIterations() {
		return currentIterations;
	}

	public void setCurrentIterations(long currentIterations) {
		this.currentIterations = currentIterations;
	}

	public long getCurrentDuration() {
		return currentDuration;
	}

	public void setCurrentDuration(long currentDuration) {
		this.currentDuration = currentDuration;
	}
}
