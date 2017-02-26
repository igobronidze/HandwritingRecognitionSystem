package ge.edu.tsu.hcrs.control_panel.server.util;

import ge.edu.tsu.hcrs.control_panel.model.network.CharSequence;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

    private static final String listSeparator = "J7ფ@";

    private static final String charSequenceSeparator = "-";

    private static final String integerListSeparator = ",";

    public static String getStringFromList(List<String> list) {
        String result = "";
        if (list == null || list.isEmpty()) {
            return result;
        }
        for (int i = 0; i < list.size() - 1; i++) {
            result += list.get(i) + listSeparator;
        }
        result += list.get(list.size() - 1);
        return result;
    }

    public static List<String> getListFromString(String text) {
        List<String> result = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            return result;
        }
        for (String part : text.split(listSeparator)) {
            result.add(part);
        }
        return result;
    }

    public static String getStringFromCharSequence(CharSequence charSequence) {
        return charSequence.getFirstSymbol() + charSequenceSeparator + charSequence.getLastSymbol();
    }

    public static CharSequence getCharSequenceFromString(String text) {
        return new CharSequence(text.charAt(0), text.charAt(2));
    }

    public static String getStringFromIntegerList(List<Integer> list) {
        String result = "";
        if (list == null || list.isEmpty()) {
            return result;
        }
        for (int i = 0; i < list.size() - 1; i++) {
            result += list.get(i) + integerListSeparator;
        }
        result += list.get(list.size() - 1);
        return result;
    }

    public static List<Integer> getIntegerListFromString(String text) {
        List<Integer> result = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            return result;
        }
        for (String part : text.split(integerListSeparator)) {
            result.add(Integer.parseInt(part));
        }
        return result;
    }
}
