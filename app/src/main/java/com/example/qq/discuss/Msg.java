package com.example.qq.discuss;

public class Msg {

    public static final int TYPE_OTHER = 0;
    public static final int TYPE_ME = 1;
    private String mcontent;
    private int mtype;

    public Msg(String content, int type) {
        this.mcontent = content;
        this.mtype = type;
    }

    public String getMcontent() {
        return mcontent;
    }

    public int getMtype() {
        return mtype;
    }
}
