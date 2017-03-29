package ge.edu.tsu.hrs.image_processing.characterdetect.model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class TextAdapter {

    private Queue<TextRow> rows = new PriorityQueue<>(new Comparator<TextRow>() {
        @Override
        public int compare(TextRow o1, TextRow o2) {
            return Short.compare(o1.getTopPoint(), o2.getTopPoint());
        }
    });

    private Map<Integer, Integer> distanceBetweenContours = new HashMap<>();

    private int possibleDistance = -1;

    public Queue<TextRow> getRows() {
        return rows;
    }

    public void setRows(Queue<TextRow> rows) {
        this.rows = rows;
    }

    public Map<Integer, Integer> getDistanceBetweenContours() {
        return distanceBetweenContours;
    }

    public void setDistanceBetweenContours(Map<Integer, Integer> distanceBetweenContours) {
        this.distanceBetweenContours = distanceBetweenContours;
    }

    public int getPossibleDistance() {
        return possibleDistance;
    }

    public void setPossibleDistance(int possibleDistance) {
        this.possibleDistance = possibleDistance;
    }
}
