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
                float deleteCost = matrix[i][j - 1] + input.getDeleteRate();
                float insertCost = matrix[i - 1][j] + input.getInsertRate();
                float changeCost = matrix[i - 1][j - 1] + input.getChangeRate() * input.getChangePossibilities().get(j - 1).get(t.charAt(i - 1));
                float min = Math.min(deleteCost, insertCost);
                matrix[i][j] = Math.min(min, changeCost);
            }
        }
        return matrix[t.length()][s.length()];
    }
}
