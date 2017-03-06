package ge.edu.tsu.hcrs.control_panel.server.dao;

import ge.edu.tsu.hcrs.control_panel.model.network.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.NormalizedData;

import java.util.List;

public interface NormalizedDataDAO {

    void addNormalizedData(NormalizedData normalizedData);

    List<NormalizedData> getNormalizedDatas(Integer width, Integer height, String generation);

    int countNormalizedDatas(Integer width, Integer height, String generations);

    List<GroupedNormalizedData> getGroupedNormalizedDatas();
}
