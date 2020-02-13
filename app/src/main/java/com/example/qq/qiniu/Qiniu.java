package com.example.qq.qiniu;

import android.util.Log;

import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.util.Auth;

import org.json.JSONObject;

public class Qiniu {
    private final static String bucket = "foroq";
    private final static String accessKey = "Ug-yj3SqggbvnTtqqxRIHssKkD2qNpNiAe4AYsO3";
    private final static String secretKey = "K41Sq__m15-3q9K_uu5II4XbtbmL1OBdy5GW_u9r";

    public static void uploadToken(String imgPath) {
//构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
//...其他参数参考类注释

//...生成上传凭证，然后准备上传
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String data = imgPath;
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Log.d( "uploadToken: ","---------------" + imgPath);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        Configuration config = new Configuration();

// 重用uploadManager。一般地，只需要创建一个uploadManager对象
        UploadManager uploadManager = new UploadManager();//配置3个线程数并发上传；不配置默认为3，只针对file.size>4M生效。线程数建议不超过5，上传速度主要取决于上行带宽，带宽很小的情况单线程和多线程没有区别
        uploadManager.put(data, key, upToken,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if(info.isOK()) {
                            Log.i("qiniu", "Upload Success");
                        } else {
                            Log.i("qiniu", "Upload Fail");
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                    }
                }, null);

    }
}