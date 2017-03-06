package ge.edu.tsu.hcrs.image_processing.characterdetect.model;

import ge.edu.tsu.hcrs.image_processing.characterdetect.model.TextRow;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class TextAdapter {

    private Queue<TextRow> rows = new PriorityQueue<>(new Comparator<TextRow>() {
        @Override
        public int compare(TextRow o1, TextRow o2) {
            return Short.compare(o1.getTopPoint(), o2.getTopPoint());
        }
    });

    public Queue<TextRow> getRows() {
        return rows;
    }

    public void setRows(Queue<TextRow> rows) {
        this.rows = rows;
    }
}
