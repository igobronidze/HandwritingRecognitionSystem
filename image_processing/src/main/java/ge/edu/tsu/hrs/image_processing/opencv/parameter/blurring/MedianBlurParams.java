package ge.edu.tsu.hrs.image_processing.opencv.parameter.blurring;

public class MedianBlurParams implements BlurringParams {

    // ksize - aperture linear size; it must be odd and greater than 1, for example: 3, 5, 7 ...
    private int kSize = 5;

    public int getkSize() {
        return kSize;
    }

    public void setkSize(int kSize) {
        this.kSize = kSize;
    }
}
