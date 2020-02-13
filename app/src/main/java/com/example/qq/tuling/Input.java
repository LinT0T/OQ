package com.example.qq.tuling;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Input {
    private static final String MYAPIKEY = "173f2af6214f436590ff106fcbaff2f7";

    public static String getString(String question) {
        String finalResult = "";
        try {
            String info = URLEncoder.encode(question, "utf-8");  //编码
            URL url = new URL("http://www.tuling123.com/openapi/api?key="
                    + MYAPIKEY + "&info=" + info);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");
            int code = connection.getResponseCode();
            if (code == 200) {
                InputStream inputStream = connection.getInputStream();
                String result = ToString.toString(inputStream);
                JSONObject object = new JSONObject(result);
                finalResult = object.getString("text");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalResult;
    }
}
