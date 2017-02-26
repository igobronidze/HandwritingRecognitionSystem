package ge.edu.tsu.hcrs.control_panel.server;

import ge.edu.tsu.hcrs.control_panel.model.network.CharSequence;
import ge.edu.tsu.hcrs.control_panel.server.processor.NormalizedDataProcessor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ServerTest {

    @Test
    public void testCountNormalizedDatas() {
        NormalizedDataProcessor normalizedDataProcessor = new NormalizedDataProcessor();
        List<String> generations = new ArrayList<>();
        generations.add("made_by_javafx_canvas_1");
        generations.add("made_by_javafx_canvas_2");
        System.out.println(normalizedDataProcessor.countNormalizedDatas(23, 29, new CharSequence('ა', 'ჰ'), generations));
    }
}
