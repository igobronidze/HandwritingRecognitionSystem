package ge.edu.tsu.hrs.control_panel.server.processor.systemparameter;

import ge.edu.tsu.hrs.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.hrs.control_panel.server.caching.CachedSystemParameter;

import java.util.ArrayList;
import java.util.List;

public class SystemParameterProcessor {

    public String getStringParameterValue(Parameter parameter) {
        return CachedSystemParameter.getStringParameterValue(parameter);
    }

    public Integer getIntegerParameterValue(Parameter parameter) {
        return Integer.parseInt(getStringParameterValue(parameter));
    }

    public Float getFloatParameterValue(Parameter parameter) {
        return Float.parseFloat(getStringParameterValue(parameter));
    }

    public Long getLongParameterValue(Parameter parameter) {
        return Long.parseLong(getStringParameterValue(parameter));
    }

    public List<Integer> getIntegerListParameterValue(Parameter parameter) {
        String text = getStringParameterValue(parameter);
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
