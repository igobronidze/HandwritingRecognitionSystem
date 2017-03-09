package ge.edu.tsu.hcrs.control_panel.service.normalizeddata;

import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizationType;

import java.util.List;

public interface NormalizedDataService {

    List<GroupedNormalizedData> getGroupedNormalizedDatum(Integer height, Integer width, Float minValue, Float maxValue, NormalizationType normalizationType, String name);

    void addNormalizedDatum(GroupedNormalizedData groupedNormalizedData, List<String> directories);
}
