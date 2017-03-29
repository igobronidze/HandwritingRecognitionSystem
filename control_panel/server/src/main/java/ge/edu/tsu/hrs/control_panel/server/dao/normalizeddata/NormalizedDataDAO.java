package ge.edu.tsu.hrs.control_panel.server.dao.normalizeddata;

import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.NormalizedData;

import java.util.List;

public interface NormalizedDataDAO {

    void addNormalizedDatum(List<NormalizedData> normalizedDatum, GroupedNormalizedData groupedNormalizedData);

    List<NormalizedData> getNormalizedDatum(List<GroupedNormalizedData> groupedNormalizedDatum);
}
