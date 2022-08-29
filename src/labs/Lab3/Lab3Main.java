package labs.Lab3;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lab3Main {
    public static List<Mat> histChannels = new ArrayList<Mat>();

    public static void compareHists(){
        double[] res = new double[3];
        for(int i = 0; i < 3; i++){
            res[i] = Imgproc.compareHist(histChannels.get(i), histChannels.get(i+3), 0);
        }
        System.out.println("Histogram comparasing...\n  B channel: " + res[0]);
        System.out.println("  G channel: " + res[1]);
        System.out.println("  R channel: " + res[2]);

    }

    public static Mat createHist(Mat img){
        List<Mat> bgrPlanes = new ArrayList<>();
        Core.split(img, bgrPlanes);

        int histSize = 256;
        float[] range = {0, 256};
        MatOfFloat histRange = new MatOfFloat(range);
        boolean accumulate = false;
        Mat     bHist = new Mat(),
                gHist = new Mat(),
                rHist = new Mat();

        Imgproc.calcHist(bgrPlanes, new MatOfInt(0), new Mat(), bHist, new MatOfInt(histSize), histRange, accumulate);
        Imgproc.calcHist(bgrPlanes, new MatOfInt(1), new Mat(), gHist, new MatOfInt(histSize), histRange, accumulate);
        Imgproc.calcHist(bgrPlanes, new MatOfInt(2), new Mat(), rHist, new MatOfInt(histSize), histRange, accumulate);

        int histW = 512, histH = 400;
        int binW = (int) Math.round((double) histW / histSize);
        Mat histImage = new Mat( histH, histW, CvType.CV_8UC3, new Scalar( 0,0,0) );
        Core.normalize(bHist, bHist, 0, histImage.rows(), Core.NORM_MINMAX);
        Core.normalize(gHist, gHist, 0, histImage.rows(), Core.NORM_MINMAX);
        Core.normalize(rHist, rHist, 0, histImage.rows(), Core.NORM_MINMAX);

        float[] bHistData = new float[(int) (bHist.total() * bHist.channels())];
        bHist.get(0, 0, bHistData);
        float[] gHistData = new float[(int) (gHist.total() * gHist.channels())];
        gHist.get(0, 0, gHistData);
        float[] rHistData = new float[(int) (rHist.total() * rHist.channels())];
        rHist.get(0, 0, rHistData);

        for( int i = 1; i < histSize; i++ ) {
            Imgproc.line(histImage, new Point(binW * (i - 1), histH - Math.round(bHistData[i - 1])),
                    new Point(binW * (i), histH - Math.round(bHistData[i])), new Scalar(255, 0, 0), 2);
            Imgproc.line(histImage, new Point(binW * (i - 1), histH - Math.round(gHistData[i - 1])),
                    new Point(binW * (i), histH - Math.round(gHistData[i])), new Scalar(0, 255, 0), 2);
            Imgproc.line(histImage, new Point(binW * (i - 1), histH - Math.round(rHistData[i - 1])),
                    new Point(binW * (i), histH - Math.round(rHistData[i])), new Scalar(0, 0, 255), 2);
        }

        for (Mat mat : Arrays.asList(bHist, gHist, rHist)) {
            histChannels.add(mat);
        }

        return histImage;
    }
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat pic1 = Imgcodecs.imread(Values.FILENAME1);
        Mat pic2 = Imgcodecs.imread(Values.FILENAME2);

        HighGui.imshow(Values.WIN1_NAME, pic1);
        HighGui.imshow(Values.WIN2_NAME, pic2);

        HighGui.moveWindow(Values.WIN2_NAME, 512, 0);
        HighGui.waitKey();

        Mat hist1 = createHist(pic1);
        Mat hist2 = createHist(pic2);

        HighGui.imshow(Values.WIN1_NAME, pic1);
        HighGui.imshow(Values.WIN2_NAME, pic2);
        HighGui.imshow(Values.HISTOGRAM1_NAME, hist1);
        HighGui.imshow(Values.HISTOGRAM2_NAME, hist2);
        HighGui.moveWindow(Values.HISTOGRAM1_NAME, 0, 300);
        HighGui.moveWindow(Values.HISTOGRAM2_NAME, 512, 300);

        compareHists();
        HighGui.waitKey();

        HighGui.destroyAllWindows();
        System.exit(0);
    }
}
