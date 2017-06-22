package ge.edu.tsu.hrs.control_panel.console.fx.ui.networkcontrol;

import ge.edu.tsu.hrs.control_panel.console.fx.util.Messages;
import ge.edu.tsu.hrs.control_panel.model.network.NetworkInfo;
import ge.edu.tsu.hrs.control_panel.model.network.NetworkTrainingStatus;
import ge.edu.tsu.hrs.control_panel.model.network.TestingInfo;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class NetworkInfoProperty {

	private SimpleIntegerProperty id;

	private SimpleStringProperty trainingDuration;

	private SimpleFloatProperty bestSquaredError;

	private SimpleFloatProperty bestPercentageOfIncorrect;

	private SimpleFloatProperty bestDiffBetweenAnsAndBest;

	private SimpleFloatProperty bestNormalizedGeneralError;

	private NetworkInfo networkInfo;

	public NetworkInfoProperty(NetworkInfo networkInfo) {
		this.id = new SimpleIntegerProperty(networkInfo.getId());
		if (networkInfo.getTrainingStatus() == NetworkTrainingStatus.TRAINING) {
			this.trainingDuration = new SimpleStringProperty("" + networkInfo.getCurrentIterations());
		} else {
			this.trainingDuration = new SimpleStringProperty(getElapsedTime(networkInfo.getTrainingDuration()));
		}
		List<TestingInfo> testingInfoList = networkInfo.getTestingInfoList();
		float bestSquaredError, betsPercentageOfIncorrect, bestDiffBetweenAnsAndBest, bestNormalizedGeneralError;
		if (testingInfoList.size() == 0) {
			bestSquaredError = -1;
			betsPercentageOfIncorrect = -1;
			bestDiffBetweenAnsAndBest = -1;
			bestNormalizedGeneralError = -1;
		} else {
			TestingInfo firstTestingInfo = testingInfoList.get(0);
			bestSquaredError = firstTestingInfo.getSquaredError();
			betsPercentageOfIncorrect = firstTestingInfo.getPercentageOfIncorrect();
			bestDiffBetweenAnsAndBest = firstTestingInfo.getDiffBetweenAnsAndBest();
			bestNormalizedGeneralError = firstTestingInfo.getNormalizedGeneralError();
			for (TestingInfo testingInfo : testingInfoList) {
				bestSquaredError = Math.min(bestSquaredError, testingInfo.getSquaredError());
				betsPercentageOfIncorrect = Math.min(betsPercentageOfIncorrect, testingInfo.getPercentageOfIncorrect());
				bestDiffBetweenAnsAndBest = Math.min(bestDiffBetweenAnsAndBest, testingInfo.getDiffBetweenAnsAndBest());
				bestNormalizedGeneralError = Math.min(bestNormalizedGeneralError, testingInfo.getNormalizedGeneralError());
			}
		}
		this.bestSquaredError = new SimpleFloatProperty(bestSquaredError);
		this.bestPercentageOfIncorrect = new SimpleFloatProperty(betsPercentageOfIncorrect);
		this.bestDiffBetweenAnsAndBest = new SimpleFloatProperty(bestDiffBetweenAnsAndBest);
		this.bestNormalizedGeneralError = new SimpleFloatProperty(bestNormalizedGeneralError);
		this.networkInfo = networkInfo;
	}

	public int getId() {
		return id.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public String getTrainingDuration() {
		return trainingDuration.get();
	}

	public float getBestSquaredError() {
		if (networkInfo.getTrainingStatus() == NetworkTrainingStatus.TRAINING) {
			return networkInfo.getCurrentSquaredError();
		} else {
			return bestSquaredError.get();
		}
	}

	public void setBestSquaredError(float bestSquaredError) {
		this.bestSquaredError.set(bestSquaredError);
	}

	public float getBestPercentageOfIncorrect() {
		return bestPercentageOfIncorrect.get();
	}

	public void setBestPercentageOfIncorrect(float bestPercentageOfIncorrect) {
		this.bestPercentageOfIncorrect.set(bestPercentageOfIncorrect);
	}

	public float getBestDiffBetweenAnsAndBest() {
		return bestDiffBetweenAnsAndBest.get();
	}

	public void setBestDiffBetweenAnsAndBest(float bestDiffBetweenAnsAndBest) {
		this.bestDiffBetweenAnsAndBest.set(bestDiffBetweenAnsAndBest);
	}

	public float getBestNormalizedGeneralError() {
		return bestNormalizedGeneralError.get();
	}

	public void setBestNormalizedGeneralError(float bestNormalizedGeneralError) {
		this.bestNormalizedGeneralError.set(bestNormalizedGeneralError);
	}

	public NetworkInfo getNetworkInfo() {
		return networkInfo;
	}

	public void setNetworkInfo(NetworkInfo networkInfo) {
		this.networkInfo = networkInfo;
	}

	private String getElapsedTime(long ms) {
		String result = "";
		ms /= 1000;
		if (ms / 3600 != 0) {
			result += ms / 3600 + Messages.get("hour") + " ";
		}
		if (ms % 3600 / 60 != 0) {
			result += ms % 3600 / 60 + Messages.get("minute") + " ";
		}
		if (ms % 60 != 0) {
			result += ms % 60 + Messages.get("second");
		}
		return result;
	}
}
