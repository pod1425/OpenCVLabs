package test;

import org.opencv.core.Core;

public class test{

    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) {
        System.out.println("Hello world!");
        System.out.println("Welcome to OpenCV version " + Core.VERSION);
        System.out.println("Created dev branch btw");
    }
}