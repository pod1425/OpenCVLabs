package labs.Lab7;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.util.Scanner;

class Values{
    public static final String SRC_PATH = "src\\labs\\Lab7\\source\\";
}

public class Lab7Main {
    public static void clustering(String imgname, Mat img){
        img.convertTo(img, CvType.CV_32F);
        Mat data = img.reshape(1, (int)img.total());

        System.out.print("Enter cluster amount> ");

        int k = new Scanner(System.in).nextInt();
        System.out.println("\nNumber of clusters: " + k + "\nWait please, this could take some time...");

        long time = System.nanoTime();
        Mat bestLabels = new Mat();
        TermCriteria criteria = new TermCriteria();

        int attempts = 5;
        int flags = Core.KMEANS_PP_CENTERS;
        Mat centers = new Mat();

        double compactness = Core.kmeans(data, k, bestLabels, criteria, attempts, flags, centers);
        System.out.println("Compactness is: " + compactness);
        Mat draw = new Mat((int)img.total(),1, CvType.CV_32FC3);
        Mat colors = centers.reshape(3, k);

        Mat col, mask = new Mat(); // a mask for each cluster label
        double[] d;
        for (int i=0; i<k; i++) {
            Core.compare(bestLabels, new Scalar(i), mask, Core.CMP_EQ);
            col = colors.row(i); // can't use the Mat directly with setTo() (see #19100)
            d = col.get(0,0); // can't create Scalar directly from get(), 3 vs 4 elements
            draw.setTo(new Scalar(d[0],d[1],d[2]), mask);
        }

        draw = draw.reshape(3, img.rows());
        draw.convertTo(draw, CvType.CV_8U);

        time = System.nanoTime() - time;
        System.out.println("Clustering of image " + imgname + " complete!\nTime elapsed: " + time/1000000 + " ms");
        HighGui.imshow(imgname, draw);
        HighGui.waitKey();

        HighGui.destroyWindow(imgname);
    }

    public static void run(){
        File[] files = new File(Values.SRC_PATH).listFiles();
        Mat img;
        String name;

        for(File file : files){
            name = file.getName();
            img = Imgcodecs.imread(Values.SRC_PATH + name);

            HighGui.imshow(name, img);
            HighGui.waitKey();

            clustering(name, img);
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
