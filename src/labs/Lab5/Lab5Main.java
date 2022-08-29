package labs.Lab5;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;

public class Lab5Main {

    public static void findFaces(Mat image){
        CascadeClassifier faceDetector = new CascadeClassifier();
        faceDetector.load("src\\labs\\Lab5\\haarcascade_frontalface_default.xml");

        MatOfRect faceDetections = new MatOfRect();

        faceDetector.detectMultiScale(image, faceDetections);
        if(faceDetections.empty()){
            System.out.println("No face found!");
        } else {
            System.out.println(faceDetections.size() + " faces found on image");
        }

        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0), 3);
        }
        HighGui.imshow(Values.WIN_NAME, image);
        HighGui.waitKey();
    }
    public static void run(){

        File folder = new File(Values.SRC_PATH);
        File[] files = folder.listFiles();
        Mat img;
        for(File file : files){
            img = Imgcodecs.imread(Values.SRC_PATH + file.getName());
            findFaces(img);
        }
    }
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        run();
        HighGui.destroyAllWindows();
        System.exit(0);
    }
}
