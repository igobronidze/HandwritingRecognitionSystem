package ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata;

import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizationType;

import java.util.List;

public interface GroupedNormalizedDataDAO {

    Integer addOrGetGroupedNormalizedDataId(GroupedNormalizedData groupedNormalizedData);

    List<GroupedNormalizedData> getGroupedNormalizedDatum(Integer height, Integer width, Float minValue, Float maxValue, NormalizationType normalizationType, String name);
}
