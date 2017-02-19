package ge.edu.tsu.hcrs.image_processing.opencv;

import ge.edu.tsu.hcrs.image_processing.Runner;
import ge.edu.tsu.hcrs.image_processing.opencv.parameter.blurring.BilateralFilter;
import ge.edu.tsu.hcrs.image_processing.opencv.parameter.blurring.Blur;
import ge.edu.tsu.hcrs.image_processing.opencv.parameter.blurring.GaussianBlur;
import ge.edu.tsu.hcrs.image_processing.opencv.parameter.blurring.MedianBlur;
import ge.edu.tsu.hcrs.image_processing.opencv.parameter.cutpapper.CannyEdgeDetectionParams;
import ge.edu.tsu.hcrs.image_processing.opencv.parameter.cutpapper.CutPaperParams;
import ge.edu.tsu.hcrs.image_processing.opencv.parameter.cutpapper.LargestFigureParams;
import ge.edu.tsu.hcrs.image_processing.opencv.parameter.cutpapper.PerspectiveTransformParams;
import ge.edu.tsu.hcrs.image_processing.util.OpenCVUtil;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class PaperCutter {

    /**
     * სურათიდან ფურცლის მსგავსი ფიგურის ამოჭრა
     * @param srcImagePath წყაროს მისამართი - relative
     * @param resultImagePath შედეგის მისამართი - relative
     * @param cutPaperParams პარამეტრები
     */
    public static void cutPaperFromImage(String srcImagePath, String resultImagePath, CutPaperParams cutPaperParams) {
        opencv_core.IplImage srcImage = OpenCVUtil.loadImage(OpenCVUtil.getAbsPath(srcImagePath));
        opencv_core.IplImage downImage = downImageScale(srcImage, cutPaperParams.getDownPercent());
        opencv_core.IplImage edgedImage = getCannyEdgeDetectedImage(downImage, cutPaperParams.getCannyEdgeDetectionParams());
        opencv_core.CvSeq contour = findLargestRectOnCannyDetectedImage(edgedImage, cutPaperParams.getLargestFigureParams());
        opencv_core.IplImage foundedImage = applyPerspectiveTransformThresholdOnOriginalImage(srcImage, contour, cutPaperParams.getDownPercent(), cutPaperParams.getPerspectiveTransformParams());
        OpenCVUtil.saveImage(foundedImage, OpenCVUtil.getAbsPath(resultImagePath));
    }

    /**
     * სურათის პროცენტულად შემცირება
     * @param srcImage წყარო
     * @param percent შემცირების პროცენტი
     * @return მიღებული სურათი
     */
    private static opencv_core.IplImage downImageScale(opencv_core.IplImage srcImage, int percent) {
        opencv_core.IplImage destImage = opencv_core.cvCreateImage(opencv_core.cvSize(srcImage.width() * percent / 100,
                srcImage.height() * percent / 100), srcImage.depth(), srcImage.nChannels());
        opencv_imgproc.cvResize(srcImage, destImage);
        return destImage;
    }

    /**
     * სურათისთვის კუთხეების დაფიქსირება
     * @param srcImage წყარო
     * @param params პარამეტრები
     * @return მიღებული სურათი
     */
    private static opencv_core.IplImage getCannyEdgeDetectedImage(opencv_core.IplImage srcImage, CannyEdgeDetectionParams params) {
        // ნაცრისფერი სურათი მიღება წყაროს ზომებით
        opencv_core.IplImage grayImage = opencv_core.cvCreateImage(opencv_core.cvGetSize(srcImage), params.getGrayImageDepth(), params.getGrayImageChannels());
        // წყაროს დაედება ნაცრისფერ სურათს და მიიღება grayImage ცვლადში განაცრისფერებული წყარო
        opencv_imgproc.cvCvtColor(srcImage, grayImage, params.getColorConversion());
        // Mat-ს მიღევა grayImage-დან
        OpenCVFrameConverter.ToMat converterToMat = new OpenCVFrameConverter.ToMat();
        Frame grayImageFrame = converterToMat.convert(grayImage);
        opencv_core.Mat grayImageMat = converterToMat.convertToMat(grayImageFrame);
        // სურათის გაწმენდა ზედმეტი ხმაურისგან, შესაძლო მეთოდებია - blur, gaussianBlur, medianBlur, bilateralFilter
        if (params.getBlurring() instanceof Blur) {
            Blur blur = (Blur)params.getBlurring();
            opencv_imgproc.blur(grayImageMat, grayImageMat, new opencv_core.Size(blur.getkSizeWidth(), blur.getkSizeHeight()));
        } else if (params.getBlurring() instanceof GaussianBlur) {
            GaussianBlur gaussianBlur = (GaussianBlur)params.getBlurring();
            opencv_imgproc.GaussianBlur(grayImageMat, grayImageMat, new opencv_core.Size(gaussianBlur.getkSizeWidth(), gaussianBlur.getkSizeHeight()),
                    gaussianBlur.getSigmaX(), gaussianBlur.getSigmaY(), gaussianBlur.getBorderType());
        } else if (params.getBlurring() instanceof MedianBlur) {
            MedianBlur medianBlur = (MedianBlur)params.getBlurring();
            opencv_imgproc.medianBlur(grayImageMat, grayImageMat, medianBlur.getkSize());
        } else if (params.getBlurring() instanceof BilateralFilter) {
            BilateralFilter bilateralFilter = (BilateralFilter)params.getBlurring();
            opencv_imgproc.bilateralFilter(grayImageMat, grayImageMat, bilateralFilter.getDiameter(), bilateralFilter.getSigmaColor(), bilateralFilter.getSigmaSpace());
        }
        // Mat-დან სურათის დაბრუნება destImage-ში
        opencv_core.IplImage destImage = converterToMat.convertToIplImage(grayImageFrame);
        // გამოიყენება კონტურების დაპატარავებისთვის
        if (params.isApplyErode()) {
            opencv_imgproc.cvErode(destImage, destImage);
        }
        // გამოიყენება კონტრუების გაზრდისთვის
        if (params.isApplyDilate()) {
            opencv_imgproc.cvDilate(destImage, destImage);
        }
        // კუთხეების გამოყოფა
        opencv_imgproc.cvCanny(destImage, destImage, params.getThreshold1(), params.getThreshold2(), params.getkSize());
//        OpenCVUtil.saveImage(destImage, OpenCVUtil.getAbsPath(Runner.resultImagePath));
        return destImage;
    }

    /**
     * ყველაზე დიდი მართკუთხედის პოვნა კუთხეების აღმოჩენილ სურათში
     * @param cannyEdgeDetectedImage წყარო(კუთხეების დაფიქსირებული სურათი)
     * @param params პარამეტრები
     * @return ადგილმდებარეობა(სასურველი ფიგურის)
     */
    private static opencv_core.CvSeq findLargestRectOnCannyDetectedImage(opencv_core.IplImage cannyEdgeDetectedImage, LargestFigureParams params) {
        // წყაროს დაკოპირება
        opencv_core.IplImage foundedContoursImage = opencv_core.cvCloneImage(cannyEdgeDetectedImage);
        // ყველა კონტურის პოვნა
        opencv_core.CvMemStorage memory = opencv_core.CvMemStorage.create();
        opencv_core.CvSeq contours = new opencv_core.CvSeq();
        opencv_imgproc.cvFindContours(foundedContoursImage, memory, contours, Loader.sizeof(opencv_core.CvContour.class), params.getRetrievalMode(),
                params.getConAppMode(), params.getPoint());
        // ყველაზე დიდი მართკუდხედი კონტურის პოვნა
        int maxWidth = 0;
        int maxHeight = 0;
        opencv_core.CvRect contour;
        opencv_core.CvSeq seqFounded = null;
        opencv_core.CvSeq nextSeq;
        for (nextSeq = contours; nextSeq != null; nextSeq = nextSeq.h_next()) {
            contour = opencv_imgproc.cvBoundingRect(nextSeq, 0);
            if ((contour.width() >= maxWidth) && (contour.height() >= maxHeight)) {
                maxWidth = contour.width();
                maxHeight = contour.height();
                seqFounded = nextSeq;
            }
        }
        // აპროქსიმაცია, შესწორება
        opencv_core.CvSeq result = opencv_imgproc.cvApproxPoly(seqFounded, Loader.sizeof(opencv_core.CvContour.class), memory, opencv_imgproc.CV_POLY_APPROX_DP,
                opencv_imgproc.cvContourPerimeter(seqFounded) * params.getApproxAccuracy(), 0);

        return result;
    }

    private static opencv_core.IplImage applyPerspectiveTransformThresholdOnOriginalImage(opencv_core.IplImage srcImage, opencv_core.CvSeq contour, int percent, PerspectiveTransformParams params) {
        // სურათის დაკოპირება
        opencv_core.IplImage warpImage = opencv_core.cvCloneImage(srcImage);
        // გაზრდა ზომაში(პროცენტის მიხდვით, რითიც დავაპატარავეთ)
        for (int i = 0; i < contour.total(); i++) {
            opencv_core.CvPoint point = new opencv_core.CvPoint(opencv_core.cvGetSeqElem(contour, i));
            point.x((point.x() * 100) / percent);
            point.y((point.y() * 100) / percent);
        }
        // ფიგურის ოთხი წერტილის დაფიქსირება
        opencv_core.CvPoint topRightPoint = new opencv_core.CvPoint(opencv_core.cvGetSeqElem(contour, 0));
        opencv_core.CvPoint topLeftPoint = new opencv_core.CvPoint(opencv_core.cvGetSeqElem(contour, 1));
        opencv_core.CvPoint bottomLeftPoint = new opencv_core.CvPoint(opencv_core.cvGetSeqElem(contour, 2));
        opencv_core.CvPoint bottomRightPoint = new opencv_core.CvPoint(opencv_core.cvGetSeqElem(contour, 3));
        // მანძილების დათვლა x და y კკორდინატებს შორის
        int resultWidth = topRightPoint.x() - topLeftPoint.x();
        int bottomWidth = bottomRightPoint.x() - bottomLeftPoint.x();
        if (bottomWidth > resultWidth) {
            resultWidth = bottomWidth;
        }
        int resultHeight = bottomLeftPoint.y() - topLeftPoint.y();
        int bottomHeight = bottomRightPoint.y() - topRightPoint.y();
        if (bottomHeight > resultHeight) {
            resultHeight = bottomHeight;
        }
        // არსებულის კოორდინატები
        float[] sourcePoints = {topLeftPoint.x(), topLeftPoint.y(), topRightPoint.x(), topRightPoint.y(),
                bottomLeftPoint.x(), bottomLeftPoint.y(), bottomRightPoint.x(), bottomRightPoint.y()};
        // მისაღების კოორდინატები
        float[] destinationPoints = {0, 0, resultWidth, 0, 0, resultHeight, resultWidth, resultHeight};
        // homography-ში ინახება გადაკეთებული სურათი - sourcePoint-დან destinationPoints-ში
        opencv_core.CvMat homography = opencv_core.cvCreateMat(3, 3, params.getHomographyMatType());
        opencv_imgproc.cvGetPerspectiveTransform(sourcePoints, destinationPoints, homography);
        // დაკოპირება
        opencv_core.IplImage destImage = opencv_core.cvCloneImage(warpImage);
        // homography-დან სურათის მიღება
        opencv_imgproc.cvWarpPerspective(warpImage, destImage, homography, params.getFlags(), params.getCvScalar());

        return cropImage(destImage, 0, 0, resultWidth, resultHeight);
    }

    /**
     * სურათის ნაწილის მოჭრა
     * @param srcImage წყარო
     * @param fromX საწყისის X
     * @param fromY საწყისის Y
     * @param toWidth დასასრულის X
     * @param toHeight დასასრულის Y
     * @return მიღებული სურათი
     */
    private static opencv_core.IplImage cropImage(opencv_core.IplImage srcImage, int fromX, int fromY, int toWidth, int toHeight) {
        opencv_core.cvSetImageROI(srcImage, opencv_core.cvRect(fromX, fromY, toWidth, toHeight));
        opencv_core.IplImage destImage = opencv_core.cvCloneImage(srcImage);
        opencv_core.cvCopy(srcImage, destImage);
        return destImage;
    }
}
