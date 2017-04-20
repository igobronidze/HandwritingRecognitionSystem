package ge.edu.tsu.hrs.control_panel.console.fx.ui.networkcontrol;

import ge.edu.tsu.hrs.control_panel.model.network.TestingInfo;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class TestingInfoProperty {

	private SimpleIntegerProperty id;

	private SimpleIntegerProperty networkId;

	private SimpleIntegerProperty networkExtraId;

	private SimpleStringProperty groupedNormalizedDatum;

	private SimpleIntegerProperty numberOfTest;

	private SimpleFloatProperty squaredError;

	private SimpleFloatProperty percentageOfIncorrect;

	private SimpleFloatProperty diffBetweenAnsAndBest;

	private SimpleFloatProperty normalizedGeneralError;

	private SimpleLongProperty duration;

	public TestingInfoProperty(TestingInfo testingInfo) {
		this.id = new SimpleIntegerProperty(testingInfo.getId());
		this.networkId = new SimpleIntegerProperty(testingInfo.getNetworkId());
		this.networkExtraId = new SimpleIntegerProperty(testingInfo.getNetworkExtraId());
		String datum = "";
		if (!testingInfo.getGroupedNormalizedDatum().isEmpty()) {
			for (int i = 0; i < testingInfo.getGroupedNormalizedDatum().size() - 1; i++) {
				datum += testingInfo.getGroupedNormalizedDatum().get(i).getId() + ",";
			}
			datum += testingInfo.getGroupedNormalizedDatum().get(testingInfo.getGroupedNormalizedDatum().size() - 1).getId();
		}
		this.groupedNormalizedDatum = new SimpleStringProperty(datum);
		this.numberOfTest = new SimpleIntegerProperty(testingInfo.getNumberOfTest());
		this.squaredError = new SimpleFloatProperty(testingInfo.getSquaredError());
		this.percentageOfIncorrect = new SimpleFloatProperty(testingInfo.getPercentageOfIncorrect());
		this.diffBetweenAnsAndBest = new SimpleFloatProperty(testingInfo.getDiffBetweenAnsAndBest());
		this.normalizedGeneralError = new SimpleFloatProperty(testingInfo.getNormalizedGeneralError());
		this.duration = new SimpleLongProperty(testingInfo.getDuration());
	}

	public int getId() {
		return id.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public int getNetworkId() {
		return networkId.get();
	}

	public void setNetworkId(int networkId) {
		this.networkId.set(networkId);
	}

	public int getNetworkExtraId() {
		return networkExtraId.get();
	}

	public void setNetworkExtraId(int networkExtraId) {
		this.networkExtraId.set(networkExtraId);
	}

	public String getGroupedNormalizedDatum() {
		return groupedNormalizedDatum.get();
	}

	public void setGroupedNormalizedDatum(String groupedNormalizedDatum) {
		this.groupedNormalizedDatum.set(groupedNormalizedDatum);
	}

	public int getNumberOfTest() {
		return numberOfTest.get();
	}

	public void setNumberOfTest(int numberOfTest) {
		this.numberOfTest.set(numberOfTest);
	}

	public float getSquaredError() {
		return squaredError.get();
	}

	public void setSquaredError(float squaredError) {
		this.squaredError.set(squaredError);
	}

	public float getPercentageOfIncorrect() {
		return percentageOfIncorrect.get();
	}

	public void setPercentageOfIncorrect(float percentageOfIncorrect) {
		this.percentageOfIncorrect.set(percentageOfIncorrect);
	}

	public float getDiffBetweenAnsAndBest() {
		return diffBetweenAnsAndBest.get();
	}

	public void setDiffBetweenAnsAndBest(float diffBetweenAnsAndBest) {
		this.diffBetweenAnsAndBest.set(diffBetweenAnsAndBest);
	}

	public float getNormalizedGeneralError() {
		return normalizedGeneralError.get();
	}

	public void setNormalizedGeneralError(float normalizedGeneralError) {
		this.normalizedGeneralError.set(normalizedGeneralError);
	}

	public long getDuration() {
		return duration.get();
	}

	public void setDuration(long duration) {
		this.duration.set(duration);
	}
}
