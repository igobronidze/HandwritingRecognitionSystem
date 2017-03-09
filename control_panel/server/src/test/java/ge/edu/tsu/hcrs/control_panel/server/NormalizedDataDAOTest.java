package ge.edu.tsu.hcrs.control_panel.server;

import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizationType;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizedData;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.GroupedNormalizedDataDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.GroupedNormalizedDataDAOImpl;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.NormalizedDataDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.NormalizedDataDAOImpl;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class NormalizedDataDAOTest {

    @Test
    @Ignore
    public void testAddOrGetGroupedNormalizedDataId() {
        GroupedNormalizedDataDAO groupedNormalizedDataDAO = new GroupedNormalizedDataDAOImpl();
        GroupedNormalizedData groupedNormalizedData = new GroupedNormalizedData();
        groupedNormalizedData.setHeight(23);
        groupedNormalizedData.setWidth(27);
        groupedNormalizedData.setMinValue(0);
        groupedNormalizedData.setMaxValue(1);
        groupedNormalizedData.setNormalizationType(NormalizationType.LINEAR_BY_AREA);
        groupedNormalizedData.setName("testName");
        groupedNormalizedData.setDuration(100);
        System.out.println(groupedNormalizedDataDAO.addOrGetGroupedNormalizedDataId(groupedNormalizedData));
        System.out.println(groupedNormalizedDataDAO.addOrGetGroupedNormalizedDataId(groupedNormalizedData));
        groupedNormalizedData.setNormalizationType(NormalizationType.DISCRETE_RESIZE);
        System.out.println(groupedNormalizedDataDAO.addOrGetGroupedNormalizedDataId(groupedNormalizedData));
    }

    @Test
    @Ignore
    public void testGetGroupedNormalizedDatum() {
        GroupedNormalizedDataDAO groupedNormalizedDataDAO = new GroupedNormalizedDataDAOImpl();
        System.out.println(groupedNormalizedDataDAO.getGroupedNormalizedDatum(null, null, null, null, null, null).size());
        System.out.println(groupedNormalizedDataDAO.getGroupedNormalizedDatum(null, null, null, null, NormalizationType.LINEAR_BY_AREA, null).size());
        System.out.println(groupedNormalizedDataDAO.getGroupedNormalizedDatum(23, 27, 0F, 1F, null, "test").size());
    }

    @Test
    @Ignore
    public void testAddNormalizedDatum() {
        NormalizedDataDAO normalizedDataDAO = new NormalizedDataDAOImpl();
        List<NormalizedData> normalizedDatum = new ArrayList<>();
        NormalizedData normalizedData = new NormalizedData();
        normalizedData.setLetter('ა');
        normalizedData.setData(new Float[2]);
        normalizedDatum.add(normalizedData);
        normalizedDatum.add(normalizedData);
        normalizedDatum.add(normalizedData);
        GroupedNormalizedData groupedNormalizedData = new GroupedNormalizedData();
        groupedNormalizedData.setId(8);
        groupedNormalizedData.setDuration(50);
        normalizedDataDAO.addNormalizedDatum(normalizedDatum, groupedNormalizedData);
    }

    @Test
    @Ignore
    public void testGetNormalizedDatum() {
        NormalizedDataDAO normalizedDataDAO = new NormalizedDataDAOImpl();
        List<GroupedNormalizedData> groupedNormalizedDatum = new ArrayList<>();
        GroupedNormalizedData groupedNormalizedData1 = new GroupedNormalizedData();
        groupedNormalizedData1.setId(8);
        groupedNormalizedDatum.add(groupedNormalizedData1);
        System.out.println(normalizedDataDAO.getNormalizedDatum(groupedNormalizedDatum).size());
        GroupedNormalizedData groupedNormalizedData2 = new GroupedNormalizedData();
        groupedNormalizedData2.setId(9);
        groupedNormalizedDatum.add(groupedNormalizedData2);
        System.out.println(normalizedDataDAO.getNormalizedDatum(groupedNormalizedDatum).size());
    }
}
