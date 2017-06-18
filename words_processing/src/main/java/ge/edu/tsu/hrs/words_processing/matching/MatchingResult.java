package ge.edu.tsu.hrs.words_processing.matching;

import java.util.List;

public class MatchingResult {

    private List<String> matches;

    private float distance;

    private long duration;

    public List<String> getMatches() {
        return matches;
    }

    public void setMatches(List<String> matches) {
        this.matches = matches;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
