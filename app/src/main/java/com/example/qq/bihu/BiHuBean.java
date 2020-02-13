package com.example.qq.bihu;

public class BiHuBean {

    private static final String BASE_URI = "http://bihu.blogofyb.com/";
    private static String info,username,password,token;
    private static int status,totalCount = 0,have = 0,totalPage = 0;

    public static int getTotalCount() {
        return totalCount;
    }

    public static void setTotalCount(int totalCount) {
        BiHuBean.totalCount = totalCount;
    }

    public static int getTotalPage() {
        return totalPage;
    }

    public static void setTotalPage(int totalPage) {
        BiHuBean.totalPage = totalPage;
    }

    public static int getHave() {
        return have;
    }

    public static void setHave(int have) {
        BiHuBean.have = have;
    }

    public static String getBaseUri() {
        return BASE_URI;
    }

    public static int getStatus() {
        return status;
    }

    public static void setStatus(int status) {
        BiHuBean.status = status;
    }

    public static String getInfo() {
        return info;
    }

    public static void setInfo(String info) {
        BiHuBean.info = info;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        BiHuBean.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        BiHuBean.password = password;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        BiHuBean.token = token;
    }


    public static String print() {
        return "BiHuBean{" +
                "status='" + status + '\'' +
                ", info='" + info + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
