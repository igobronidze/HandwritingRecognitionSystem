package ge.edu.tsu.hcrs.control_panel.service.normalizeddata;

import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizedData;

import java.util.List;

public interface NormalizedDataService {

    void addNormalizedData(NormalizedData normalizedData);

    List<NormalizedData> getNormalizedDatas(Integer width, Integer height, String generation);

    int countNormalizedDatas(Integer width, Integer height, List<String> generations);

    List<GroupedNormalizedData> getGroupedNormalizedDatas();
}
