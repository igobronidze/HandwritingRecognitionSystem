package ge.edu.tsu.hcrs.control_panel.model.network;

import java.io.Serializable;
import java.lang.*;
import java.util.Arrays;

public class NormalizedData implements Serializable {

    private int width;

    private int height;

    private Float[] data;

    private Character letter;

    private CharSequence charSequence;

    private String trainingSetGeneration;

    public NormalizedData() {
    }

    public NormalizedData(int width, int height, Float[] data) {
        this.width = width;
        this.height = height;
        this.data = data;
    }

    public NormalizedData(int width, int height, Float[] data, Character letter, CharSequence charSequence, String trainingSetGeneration) {
        this.width = width;
        this.height = height;
        this.data = data;
        this.letter = letter;
        this.charSequence = charSequence;
        this.trainingSetGeneration = trainingSetGeneration;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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

    public CharSequence getCharSequence() {
        return charSequence;
    }

    public void setCharSequence(CharSequence charSequence) {
        this.charSequence = charSequence;
    }

    public String getTrainingSetGeneration() {
        return trainingSetGeneration;
    }

    public void setTrainingSetGeneration(String trainingSetGeneration) {
        this.trainingSetGeneration = trainingSetGeneration;
    }

    @Override
    public String toString() {
        return "NormalizedData{" +
                "width=" + width +
                ", height=" + height +
                ", grid=" + Arrays.toString(data) +
                ", letter=" + letter +
                ", charSequence=" + charSequence +
                ", trainingSetGeneration='" + trainingSetGeneration + '\'' +
                '}';
    }
}
