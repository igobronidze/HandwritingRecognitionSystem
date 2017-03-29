package ge.edu.tsu.hrs.control_panel.model.network;

import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.NormalizationType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TrainingDataInfo implements Serializable {

	public static final long serialVersionUID = 1352562342L;

	private int id;

	private transient List<Integer> groupedNormalizedDatumIds = new ArrayList<>();

	private int height;

	private int width;

	private float minValue;

	private float maxValue;

	private NormalizationType normalizationType;

	private int count;

	public TrainingDataInfo() {
	}

	public TrainingDataInfo(int id, List<Integer> groupedNormalizedDatumIds, int height, int width, float minValue, float maxValue, NormalizationType normalizationType, int count) {
		this.id = id;
		this.groupedNormalizedDatumIds = groupedNormalizedDatumIds;
		this.height = height;
		this.width = width;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.normalizationType = normalizationType;
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Integer> getGroupedNormalizedDatumIds() {
		return groupedNormalizedDatumIds;
	}

	public void setGroupedNormalizedDatumIds(List<Integer> groupedNormalizedDatumIds) {
		this.groupedNormalizedDatumIds = groupedNormalizedDatumIds;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public float getMinValue() {
		return minValue;
	}

	public void setMinValue(float minValue) {
		this.minValue = minValue;
	}

	public float getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}

	public NormalizationType getNormalizationType() {
		return normalizationType;
	}

	public void setNormalizationType(NormalizationType normalizationType) {
		this.normalizationType = normalizationType;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
