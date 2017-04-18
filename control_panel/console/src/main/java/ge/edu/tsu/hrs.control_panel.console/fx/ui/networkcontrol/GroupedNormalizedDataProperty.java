package ge.edu.tsu.hrs.control_panel.console.fx.ui.networkcontrol;

import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class GroupedNormalizedDataProperty {

	private SimpleIntegerProperty id;

	private SimpleIntegerProperty width;

	private SimpleIntegerProperty height;

	private SimpleFloatProperty minValue;

	private SimpleFloatProperty maxValue;

	private SimpleStringProperty normalizationType;

	private SimpleStringProperty name;

	private SimpleIntegerProperty count;

	private SimpleBooleanProperty checked;

	public GroupedNormalizedDataProperty(GroupedNormalizedData groupedNormalizedData) {
		this.id = new SimpleIntegerProperty(groupedNormalizedData.getId());
		this.width = new SimpleIntegerProperty(groupedNormalizedData.getWidth());
		this.height = new SimpleIntegerProperty(groupedNormalizedData.getHeight());
		this.minValue = new SimpleFloatProperty(groupedNormalizedData.getMinValue());
		this.maxValue = new SimpleFloatProperty(groupedNormalizedData.getMaxValue());
		this.normalizationType = new SimpleStringProperty(groupedNormalizedData.getNormalizationType().name());
		this.name = new SimpleStringProperty(groupedNormalizedData.getName());
		this.count = new SimpleIntegerProperty(groupedNormalizedData.getCount());
		this.checked = new SimpleBooleanProperty(false);
	}

	public int getId() {
		return id.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public int getWidth() {
		return width.get();
	}

	public void setWidth(int width) {
		this.width.set(width);
	}

	public int getHeight() {
		return height.get();
	}

	public void setHeight(int height) {
		this.height.set(height);
	}

	public float getMinValue() {
		return minValue.get();
	}

	public void setMinValue(float minValue) {
		this.minValue.set(minValue);
	}

	public float getMaxValue() {
		return maxValue.get();
	}

	public void setMaxValue(float maxValue) {
		this.maxValue.set(maxValue);
	}

	public String getNormalizationType() {
		return normalizationType.get();
	}

	public void setNormalizationType(String normalizationType) {
		this.normalizationType.set(normalizationType);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public int getCount() {
		return count.get();
	}

	public void setCount(int count) {
		this.count.set(count);
	}

	public boolean getChecked() {
		return checked.get();
	}

	public SimpleBooleanProperty checkedProperty() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked.set(checked);
	}
}
