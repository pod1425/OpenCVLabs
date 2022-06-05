package labs.Lab4;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Lab4Main {
    public static Mat findImage(Mat source, Mat template){
        Mat outputImage = new Mat();
        Imgproc.matchTemplate(source, template, outputImage, 4);

        Core.MinMaxLocResult mmr = Core.minMaxLoc(outputImage);
        Point matchLoc = mmr.maxLoc;
        Imgproc.rectangle(source, matchLoc, new Point(matchLoc.x + template.cols(),
                matchLoc.y + template.rows()), new Scalar(0, 0, 255), 3);
        return source;
    }

    public static void run(){
        for(int i = 0; i < Values.SRC.length; i++){
            HighGui.imshow(Values.WIN_NAME, findImage(
                    Imgcodecs.imread(Values.SRC[i]), Imgcodecs.imread(Values.TEMPL[i])));
            HighGui.waitKey();
        }
    }
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        run();
        HighGui.destroyAllWindows();
        System.exit(0);
    }
}