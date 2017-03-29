package ge.edu.tsu.hrs.control_panel.model.network.normalizeddata;

import java.io.Serializable;
import java.util.Arrays;

public class NormalizedData implements Serializable {

    private int id;

    private Float[] data;

    private Character letter;

    public NormalizedData() {
    }

    public NormalizedData(Float[] data, Character letter) {
        this.data = data;
        this.letter = letter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Float[] getData() {
        return data;
    }

    public void setData(Float[] data) {
        this.data = data;
    }

    public Character getLetter() {
        return letter;
    }

    public void setLetter(Character letter) {
        this.letter = letter;
    }

    @Override
    public String toString() {
        return "NormalizedData{" +
                "grid=" + Arrays.toString(data) +
                ", letter=" + letter +
                '}';
    }
}
