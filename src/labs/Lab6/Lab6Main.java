package labs.Lab6;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Lab6Main {

    public static List<Mat> divideIntoChannels(Mat img){
        Mat hsvImg = new Mat();
        Imgproc.cvtColor(img, hsvImg, Imgproc.COLOR_RGB2HSV);
        List<Mat> channels = new ArrayList<Mat>(3);
        Core.split(hsvImg, channels);
        return channels;
    }
    public static void showImgs(List<Mat> hsv){
        String[] hsvNames = new String[]{Values.H_WIN_NAME, Values.S_WIN_NAME, Values.V_WIN_NAME};
        int w, h;
        for(int i = 0; i < 3; i++){
            HighGui.imshow(hsvNames[i], hsv.get(i));
            w = hsv.get(i).width() > 256 ? hsv.get(i).width() : 256;
            h = hsv.get(i).height() > 256 ? hsv.get(i).height() : 256;
            if(hsv.get(i).width() < 512 && hsv.get(i).height() < 512){
                HighGui.resizeWindow(hsvNames[i], w, h);
                HighGui.moveWindow(hsvNames[i], w * i + 10 * i, h + 10);
            }
        }
        HighGui.waitKey();
        //з незрозумілої мені причини, ні один, ні другий спосіб закрити всі вікна
        HighGui.destroyAllWindows();
        for(String winName : hsvNames){
            HighGui.destroyWindow(winName);
        }
    }
    public static void run(){

        File[] files = new File(Values.SRC_PATH).listFiles();
        Mat img;
        String name;

        for(File file : files){
            name = Values.SRC_PATH + file.getName();
            img = Imgcodecs.imread(name);
            HighGui.imshow(file.getName(), img);
            showImgs(divideIntoChannels(img));

            HighGui.destroyWindow(name);
        }
    }
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        run();
        HighGui.destroyAllWindows();
        System.exit(0);
    }
}
