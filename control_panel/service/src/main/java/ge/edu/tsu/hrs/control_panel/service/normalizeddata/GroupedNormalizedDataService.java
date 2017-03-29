package ge.edu.tsu.hrs.control_panel.service.normalizeddata;

import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.NormalizationType;

import java.util.List;

public interface GroupedNormalizedDataService {

    List<GroupedNormalizedData> getGroupedNormalizedDatum(Integer id, Integer height, Integer width, Float minValue, Float maxValue, NormalizationType normalizationType, String name);

    GroupedNormalizedData getGroupedNormalizedData(Integer id);
}
