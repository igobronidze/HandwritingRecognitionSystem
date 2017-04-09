package ge.edu.tsu.hrs.image_processing.opencv.service.condition;

public class ImageCondition {

    private BackgroundCondition backgroundCondition = BackgroundCondition.UNKNOWN;

    private NoiseCondition noiseCondition = NoiseCondition.UNKNOWN;

    private Stridency stridency = Stridency.UNKNOWN;

    public BackgroundCondition getBackgroundCondition() {
        return backgroundCondition;
    }

    public void setBackgroundCondition(BackgroundCondition backgroundCondition) {
        this.backgroundCondition = backgroundCondition;
    }

    public NoiseCondition getNoiseCondition() {
        return noiseCondition;
    }

    public void setNoiseCondition(NoiseCondition noiseCondition) {
        this.noiseCondition = noiseCondition;
    }

    public Stridency getStridency() {
        return stridency;
    }

    public void setStridency(Stridency stridency) {
        this.stridency = stridency;
    }
}
