package labs.Lab1;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.concurrent.TimeUnit;

public class Lab1Main {

    public static final String filepath1 = "src\\labs\\Lab1\\source_pics\\picture1.jpg";
    public static final String filepath2 = "src\\labs\\Lab1\\source_pics\\picture2.jpg";
    public static final String windowName = "Image";

    public static void delay(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private static void showPic(Mat matrix){
        HighGui.imshow(windowName, matrix);
    }
    private static Mat blurPic(Mat matrix){
        Mat tempMat = new Mat();
        Imgproc.blur(matrix, tempMat, new Size(8,8), new Point(1,1));
        return tempMat;
    }
    private static Mat resizePic(Mat matrix){
        Mat tempMat = new Mat();
        Imgproc.resize(matrix, tempMat, new Size(300,300));
        return tempMat;
    }
    public static void SecondVariantTask(Mat matrixImg1){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        HighGui.createJFrame(windowName, 1);
        showPic(matrixImg1);
        HighGui.resizeWindow(windowName, 800,800);

        HighGui.waitKey();
        showPic(blurPic(matrixImg1));
        HighGui.waitKey();
        showPic(resizePic(matrixImg1));
        HighGui.waitKey();
        Mat mat = Imgcodecs.imread(filepath2);
        showPic(resizePic(mat));
        HighGui.waitKey();
        System.exit(0);
    }
    public static void startExecution(){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat matrix = Imgcodecs.imread(filepath1);
        SecondVariantTask(matrix);
    }

    public static void main(String[] args) {
        startExecution();
    }
}
