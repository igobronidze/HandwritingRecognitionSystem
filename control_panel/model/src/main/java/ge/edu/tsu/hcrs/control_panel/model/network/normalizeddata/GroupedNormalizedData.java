package ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata;

import java.util.List;

public class GroupedNormalizedData {

    private int id;

	private int width;

	private int height;

	private float minValue;

	private float maxValue;

	private NormalizationType normalizationType;

	private String name;

	private int count;

	private List<NormalizedData> normalizedDatum;
}
