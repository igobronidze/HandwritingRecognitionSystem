package ge.edu.tsu.hrs.control_panel.server.util;

public class CharUtil {

    public static String getCharValueForFileName(char c) {
        String s;
        switch (c) {
            case '?' :
                s = "questionMark";
                break;
            case '<' :
                s = "lessThan";
                break;
            case '>' :
                s = "greaterThan";
                break;
            case ':' :
                s = "colon";
                break;
            case '"' :
                s = "doubleQuote";
                break;
            case '/' :
                s = "forwardSlash";
                break;
            case '\\' :
                s = "backslash";
                break;
            case '|' :
                s = "verticalBar";
                break;
            case '*' :
                s = "asterisk";
                break;
            default:
                s = "" + c;
        }
        return s;
    }

    public static char getCharFromFileName(String text) {
        switch (text) {
            case "questionMark" :
                return '?';
            case "lessThan" :
                return '<';
            case "greaterThan" :
                return '>';
            case "colon" :
                return ':';
            case "doubleQuote" :
                return '"';
            case "forwardSlash" :
                return '/';
            case "backslash" :
                return '\\';
            case "verticalBar" :
                return  '|';
            case "asterisk" :
                return '*';
            default:
                return text.charAt(0);
        }
    }
}
