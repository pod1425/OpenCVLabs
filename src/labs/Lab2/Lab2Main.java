package labs.Lab2;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

class Lab2Methods {
    private Mat srcGray = new Mat();
    private Mat hierarchy = new Mat();


    private final int MAX_THRESHOLD = Lab2Fields.MAX_THRESHOLD;
    private int threshold = Lab2Fields.TRESHOLD;

    public List<MatOfPoint> findContours(Mat img) {
        if (img.empty()) {
            System.err.println("Cannot read image!");
            System.exit(0);
        }
        Imgproc.cvtColor(img, srcGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.blur(srcGray, srcGray, new  Size(3, 3));

        Mat cannyOutput = new Mat();
        Imgproc.Canny(srcGray, cannyOutput, threshold, threshold * 2);
        List<MatOfPoint> contours = new ArrayList<>();

        Imgproc.findContours(cannyOutput, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        return contours;
    }

    public void drawContours(String winName, Mat img, List<MatOfPoint> contours) throws InterruptedException {

        for (int i = 0; i < contours.size(); i++) {
            Imgproc.drawContours(img, contours, i, Lab2Fields.COLORS.BLUE, 2, Core.LINE_8, hierarchy, 0, new Point());
        }
        HighGui.imshow(winName, img);
    }

    public void drawSimilar(Mat img, List<MatOfPoint> contours, List<MatOfPoint> contour){
        double result;
        for (int i = 0; i < contours.size(); i++){
            for(int j = 0; j < contour.size(); j++){
                result = Imgproc.matchShapes(contours.get(i), contour.get(j),Imgproc.CV_CONTOURS_MATCH_I1, 0.0);
                if(result  < 0.03){
                    Imgproc.drawContours(img, contours, i, Lab2Fields.COLORS.RED, 2, Core.LINE_8, hierarchy, 0, new Point());
                } else if(result < 0.2) {
                    Imgproc.drawContours(img, contours, i, Lab2Fields.COLORS.YELLOW, 2, Core.LINE_8, hierarchy, 0, new Point());
                } else if(result < 1.5) {
                    Imgproc.drawContours(img, contours, i, Lab2Fields.COLORS.GREEN, 2, Core.LINE_8, hierarchy, 0, new Point());
                }
            }
        }
        HighGui.imshow(Lab2Fields.WHOLE_WIN_NAME, img);
    }
    /*
    //old code
    public static final String filename = "src\\labs\\Lab2\\source\\cat.png";
    public static final String catsPicPath = "src\\labs\\Lab2\\source\\cats.png";
    public static final String defaultWindowName = "Cat image";

    public static List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

    public static List<MatOfPoint> getContours(Mat mBase, boolean external) {
        Mat hierarchy = new Mat();
        if (external) {
            Imgproc.findContours(mBase, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        } else {
            Imgproc.findContours(mBase, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        }
        return contours;
    }

    private static void showPic(Mat matrix, String windowName){
        HighGui.imshow(windowName, matrix);
    }

    public static void startExecution(){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat catmatrix = Imgcodecs.imread(catPicPath, IMREAD_GRAYSCALE);
        // Creating the destination matrix
        Mat dst = new Mat();

        // Converting to binary image...
        Imgproc.threshold(catmatrix, dst, 200, 500, Imgproc.THRESH_BINARY);
        showPic(catmatrix, defaultWindowName);
        //HighGui.waitKey();
        List<MatOfPoint> contours = getContours(dst, true);
        //HighGui.waitKey();
        Imgproc.drawContours(catmatrix, contours, 0, new Scalar(0,0,255));
        showPic(dst, defaultWindowName);
        HighGui.waitKey();
    }
    */
}

public class Lab2Main {


    public static void main(String[] args) throws InterruptedException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Lab2Methods tasks = new Lab2Methods();
        Mat cat = Imgcodecs.imread(Lab2Fields.SINGLE_IMAGE_FILENAME);
        Mat cats = Imgcodecs.imread(Lab2Fields.IMAGE_FILENAME);

        HighGui.imshow(Lab2Fields.SINGLE_WIN_NAME, cat);
        HighGui.imshow(Lab2Fields.WHOLE_WIN_NAME, cats);
        HighGui.waitKey();

        List<MatOfPoint> catContours = tasks.findContours(cat);
        List<MatOfPoint> wholeContours = tasks.findContours(cats);

        tasks.drawContours(Lab2Fields.SINGLE_WIN_NAME, cat, catContours);
        tasks.drawContours(Lab2Fields.WHOLE_WIN_NAME, cats, wholeContours);
        HighGui.waitKey();

        tasks.drawSimilar(cats, wholeContours, catContours);
        HighGui.imshow(Lab2Fields.SINGLE_WIN_NAME, cat);
        HighGui.waitKey();

        HighGui.destroyWindow(Lab2Fields.SINGLE_WIN_NAME);
        HighGui.destroyWindow(Lab2Fields.WHOLE_WIN_NAME);
        System.exit(1);
    }
}
