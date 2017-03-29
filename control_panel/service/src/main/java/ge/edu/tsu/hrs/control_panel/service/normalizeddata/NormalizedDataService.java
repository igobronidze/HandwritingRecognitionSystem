package ge.edu.tsu.hrs.control_panel.service.normalizeddata;

import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.NormalizedData;

import java.util.List;

public interface NormalizedDataService {

    List<NormalizedData> getNormalizedDatum(List<GroupedNormalizedData> groupedNormalizedDatum);

    void addNormalizedDatum(GroupedNormalizedData groupedNormalizedData, List<String> files);
}
