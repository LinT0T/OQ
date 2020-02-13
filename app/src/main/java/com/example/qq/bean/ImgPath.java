package com.example.qq.bean;

public class ImgPath {
    private static String userPath;
    private static String tuLingPath;

    public static String getUserPath() {
        return userPath;
    }

    public static void setUserPath(String userPath) {
        ImgPath.userPath = userPath;
    }

    public static String getTuLingPath() {
        return tuLingPath;
    }

    public static void setTuLingPath(String tuLingPath) {
        ImgPath.tuLingPath = tuLingPath;
    }
}
