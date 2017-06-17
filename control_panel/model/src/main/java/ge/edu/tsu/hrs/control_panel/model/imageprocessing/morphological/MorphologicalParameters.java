package ge.edu.tsu.hrs.control_panel.model.imageprocessing.morphological;

public class MorphologicalParameters {

    private MorphologicalType morphologicalType;

    private int erosionAmount;

    private Integer erosionElem;

    private Integer erosionSize;

    private int dilationAmount;

    private Integer dilationElem;

    private Integer dilationSize;

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

    public Integer getErosionElem() {
        return erosionElem;
    }

    public void setErosionElem(Integer erosionElem) {
        this.erosionElem = erosionElem;
    }

    public Integer getErosionSize() {
        return erosionSize;
    }

    public void setErosionSize(Integer erosionSize) {
        this.erosionSize = erosionSize;
    }

    public int getDilationAmount() {
        return dilationAmount;
    }

    public void setDilationAmount(int dilationAmount) {
        this.dilationAmount = dilationAmount;
    }

    public Integer getDilationElem() {
        return dilationElem;
    }

    public void setDilationElem(Integer dilationElem) {
        this.dilationElem = dilationElem;
    }

    public Integer getDilationSize() {
        return dilationSize;
    }

    public void setDilationSize(Integer dilationSize) {
        this.dilationSize = dilationSize;
    }
}
