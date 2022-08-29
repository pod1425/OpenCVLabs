package labs.Lab2;

import org.opencv.core.Scalar;

public class Lab2Fields {
    public static final int MAX_THRESHOLD = 255;
    public static final int TRESHOLD = 100;
    public static final String SINGLE_IMAGE_FILENAME = "src\\labs\\Lab2\\source\\cat2.png";//change number to test on different pics
    public static final String IMAGE_FILENAME = "src\\labs\\Lab2\\source\\cats.png";
    public static final String SINGLE_WIN_NAME = "Cat";
    public static final String WHOLE_WIN_NAME = "Cats :3";

    public static class COLORS{
        public static final Scalar RED = new Scalar(0,0,255);
        public static final Scalar GREEN = new Scalar(0,255,0);
        public static final Scalar BLUE = new Scalar(255,0,0);
        public static final Scalar YELLOW = new Scalar(0, 255, 255);
    }

}
