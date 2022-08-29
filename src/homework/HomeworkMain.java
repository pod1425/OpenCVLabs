package homework;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class Values{
    public static final String SRC_PATH = "src\\homework\\source\\";
    public static final String RES_PATH = "src\\homework\\res\\";
}
public class HomeworkMain {
    public static List<Scalar> clustering(Mat img){
        int k = 5;
        img.convertTo(img, CvType.CV_32F);
        Mat data = img.reshape(1, (int)img.total());

        System.out.println("\nNumber of clusters: " + k + "\nWait please, this could take some time...");
        long time = System.nanoTime();

        Mat bestLabels = new Mat();
        TermCriteria criteria = new TermCriteria();

        int attempts = 5;
        int flags = Core.KMEANS_PP_CENTERS;
        Mat centers = new Mat();

        double compactness = Core.kmeans(data, k, bestLabels, criteria, attempts, flags, centers);
        System.out.println("Compactness is: " + compactness);
        Mat colors = centers.reshape(3, k);

        Mat col, mask = new Mat();
        double[] d;
        List<Scalar> picColors = new ArrayList<>();
        for (int i=0; i<k; i++) {
            Core.compare(bestLabels, new Scalar(i), mask, Core.CMP_EQ);
            col = colors.row(i);
            d = col.get(0,0);
            picColors.add(new Scalar(d[0],d[1],d[2]));
            System.out.println(d[0] + " " + d[1] + " " + d[2]);
        }
        time = System.nanoTime() - time;
        System.out.println("Time elapsed: " + time/1000000 + " ms");

        return picColors;
    }

    public static void drawColors(List<Scalar> colors, String name){
        Mat res = new Mat(new Size(1000, 1000), 16);

        for(int i = 0; i < colors.size(); i++){
            Imgproc.rectangle(res, new Point(0, 200 * i), new Point(1000, 200 * i + 200), colors.get(i), -1);
        }
        Imgcodecs.imwrite(Values.RES_PATH + name, res);
    }

    public static void run(){
        File[] files = new File(Values.SRC_PATH).listFiles();
        Mat img;
        String name;
        if(files.length != 0){
            for(File file : files){
                name = file.getName();
                img = Imgcodecs.imread(Values.SRC_PATH + name);
                drawColors(clustering(img), name);
            }
        } else {
            System.out.println("Source directory is empty!");
        }
    }

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        run();
        HighGui.destroyAllWindows();
        System.exit(0);
    }
}
