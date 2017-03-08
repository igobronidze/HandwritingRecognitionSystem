package ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata;

import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizedData;

import java.util.List;

public interface NormalizedDataDAO {

    void addNormalizedDatum(List<NormalizedData> normalizedDatum, int groupedNormalizedDataId);

    List<NormalizedData> getNormalizedDatum(List<GroupedNormalizedData> groupedNormalizedDatum);
}
