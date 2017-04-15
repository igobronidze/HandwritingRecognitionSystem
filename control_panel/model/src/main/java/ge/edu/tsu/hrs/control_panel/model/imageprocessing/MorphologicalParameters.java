package ge.edu.tsu.hrs.control_panel.model.imageprocessing;

public class MorphologicalParameters {

    private MorphologicalType morphologicalType;

    private int erosionAmount;

    private int erosionElem;

    private int erosionSize;

    private int dilationAmount;

    private int dilationElem;

    private int dilationSize;

    public MorphologicalType getMorphologicalType() {
        return morphologicalType;
    }

    public void setMorphologicalType(MorphologicalType morphologicalType) {
        this.morphologicalType = morphologicalType;
    }

    public int getErosionAmount() {
        return erosionAmount;
    }

    public void setErosionAmount(int erosionAmount) {
        this.erosionAmount = erosionAmount;
    }

    public int getErosionElem() {
        return erosionElem;
    }

    public void setErosionElem(int erosionElem) {
        this.erosionElem = erosionElem;
    }

    public int getErosionSize() {
        return erosionSize;
    }

    public void setErosionSize(int erosionSize) {
        this.erosionSize = erosionSize;
    }

    public int getDilationAmount() {
        return dilationAmount;
    }

    public void setDilationAmount(int dilationAmount) {
        this.dilationAmount = dilationAmount;
    }

    public int getDilationElem() {
        return dilationElem;
    }

    public void setDilationElem(int dilationElem) {
        this.dilationElem = dilationElem;
    }

    public int getDilationSize() {
        return dilationSize;
    }

    public void setDilationSize(int dilationSize) {
        this.dilationSize = dilationSize;
    }
}
