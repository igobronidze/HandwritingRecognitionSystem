package ge.edu.tsu.hcrs.control_panel.server.manager;

import ge.edu.tsu.hcrs.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.hcrs.control_panel.server.caching.CachedSystemParameter;

import java.util.ArrayList;
import java.util.List;

public class SystemParameterProcessor {

    public String getParameterValue(Parameter parameter) {
        return CachedSystemParameter.getParameterValue(parameter);
    }

    public Integer getIntegerParameterValue(Parameter parameter) {
        return Integer.parseInt(getParameterValue(parameter));
    }

    public Float getFloatParameterValue(Parameter parameter) {
        return Float.parseFloat(getParameterValue(parameter));
    }

    public Long getLongParameterValue(Parameter parameter) {
        return Long.parseLong(getParameterValue(parameter));
    }

    public List<Integer> getIntegerListParameterValue(Parameter parameter) {
        String text = getParameterValue(parameter);
        if (text == null || text.isEmpty()) {
            return new ArrayList<>();
        }
        String parts[] = text.split(",");
        List<Integer> result = new ArrayList<>();
        if (parts.length == 0) {
            result.add(Integer.parseInt(text));
        }
        for (String part : parts) {
            result.add(Integer.parseInt(part));
        }
        return result;
    }
}
