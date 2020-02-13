package com.example.qq.tuling;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ToString {
    public static String toString(InputStream in) {
        String result = "";
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = in.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
                byteArrayOutputStream.flush();
            }
            result = new String(byteArrayOutputStream.toByteArray(), "utf-8");
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}
