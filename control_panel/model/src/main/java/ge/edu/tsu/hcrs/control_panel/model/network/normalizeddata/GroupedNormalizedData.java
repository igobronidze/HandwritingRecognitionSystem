package ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata;

public class GroupedNormalizedData {

    private int id;

	private int height;

	private int width;

	private float minValue;

	private float maxValue;

	private NormalizationType normalizationType;

	private String name;

	private int count;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
