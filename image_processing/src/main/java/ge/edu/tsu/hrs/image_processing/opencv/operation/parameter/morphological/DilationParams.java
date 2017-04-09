package ge.edu.tsu.hrs.image_processing.opencv.operation.parameter.morphological;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;

public class DilationParams {

    // 0 - 1 - 2
    private int dilation_elem = 0;

    private int dilation_size = 1;

    private opencv_core.Mat element;

    public DilationParams() {
        int dilation_type = 0;
        switch (dilation_elem) {
            case 0 :
                dilation_type = opencv_imgproc.MORPH_RECT;
                break;
            case 1 :
                dilation_type = opencv_imgproc.MORPH_CROSS;
                break;
            case 2 :
                dilation_type = opencv_imgproc.MORPH_ELLIPSE;
        }
        element = opencv_imgproc.getStructuringElement(dilation_type, new opencv_core.Size(2 * dilation_size + 1, 2 * dilation_size + 1), new opencv_core.Point(dilation_size, dilation_size));
    }

    public int getDilation_elem() {
        return dilation_elem;
    }

    public void setDilation_elem(int dilation_elem) {
        this.dilation_elem = dilation_elem;
    }

    public int getDilation_size() {
        return dilation_size;
    }

    public void setDilation_size(int dilation_size) {
        this.dilation_size = dilation_size;
    }

    public opencv_core.Mat getElement() {
        return element;
    }

    public void setElement(opencv_core.Mat element) {
        this.element = element;
    }
}
