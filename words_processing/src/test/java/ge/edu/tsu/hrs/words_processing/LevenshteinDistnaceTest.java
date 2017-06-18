package ge.edu.tsu.hrs.words_processing;

import ge.edu.tsu.hrs.words_processing.matching.LevenshteinDistance;
import org.junit.Assert;
import org.junit.Test;

public class LevenshteinDistnaceTest {

    private static final String s = "abcdef";

    private static final String t = "akced";

    private static final float ans = 3.0F;

    @Test
    public void testCountDistance() {
        Assert.assertEquals(ans, LevenshteinDistance.countDistance(s, t), 0.0F);
    }
}
