package ge.edu.tsu.hrs.words_processing.matching;

public class LevenshteinDistance {

    public static float countDistance(String s, String t, MatchingInput input) {
        float[][] matrix = new float[t.length() + 1][s.length() + 1];
        for (int i = 0; i <= s.length(); i++) {
            matrix[0][i] = i * input.getInsertRate();
        }
        for (int i = 1; i <= t.length(); i++) {
            matrix[i][0] = i * input.getDeleteRate();
            for (int j = 1; j <= s.length(); j++) {
                float same = t.charAt(i - 1) == s.charAt(j - 1) ? 0 : 1;
                float min = Math.min(matrix[i - 1][j], matrix[i][j - 1]);
                min = Math.min(min, matrix[i - 1][j - 1]);
                matrix[i][j] = min + same;
            }
        }
        return matrix[t.length()][s.length()];
    }
}
