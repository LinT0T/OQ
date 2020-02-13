package com.example.qq.bihu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qq.MyImageView;
import com.example.qq.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainBiHuActivity extends AppCompatActivity {

    private OkHttpClient mOkHttpClient;
    private RecyclerView recyclerView;
    private List<Question> list = new ArrayList<>();
    private Bitmap bitmap_head = null, bitmap_content = null;
    private static final int IMG_CONTENT = 1, IMG_HEAD = 0;
    private BiHuAdapter adapter;
    private LinearLayout linearLayout, linearLayout2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bi_hu);
        linearLayout = findViewById(R.id.ll_main);
        linearLayout2 = findViewById(R.id.ll_bh_me);
        MyImageView myImageView = findViewById(R.id.img_bh_bottom);
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainBiHuActivity.this, BiHuUserActivity.class);
                startActivity(intent);
            }
        });
        myImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainBiHuActivity.this, BiHuUserActivity.class);
                startActivity(intent);
            }
        });
        Button button = findViewById(R.id.btn_bh_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainBiHuActivity.this, QsActivity.class);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.rv_bh);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = linearLayoutManager.findFirstVisibleItemPosition();
                if (position >= 1) {
                    Animation animBottomOut = AnimationUtils.loadAnimation(MainBiHuActivity.this,
                            R.anim.bottom_out);
                    animBottomOut.setDuration(240);
                    if (linearLayout.getVisibility() != View.GONE)
                        linearLayout.startAnimation(animBottomOut);
                    linearLayout.setVisibility(View.GONE);

                } else {
                    Animation animBottomIn = AnimationUtils.loadAnimation(MainBiHuActivity.this,
                            R.anim.bottom_in);
                    animBottomIn.setDuration(240);
                    if (linearLayout.getVisibility() != View.VISIBLE)
                        linearLayout.startAnimation(animBottomIn);
                    linearLayout.setVisibility(View.VISIBLE);


                }
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new BiHuAdapter(list);
        recyclerView.setAdapter(adapter);
        getQues();
    }

    private void getQues() {
        mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("page", "0")
                .add("count", "20")
                .add("token", BiHuBean.getToken())
                .build();
        Request request = new Request.Builder()
                .url(BiHuBean.getBaseUri() + "getQuestionList.php")
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject object = null;
                        try {
                            object = new JSONObject(str);
                            BiHuBean.setStatus(object.getInt("status"));
                            BiHuBean.setInfo(object.getString("info"));
                            String str2 = object.getString("data");
                            object = new JSONObject(str2);
                            BiHuBean.setTotalCount(object.getInt("totalCount"));
                            BiHuBean.setTotalPage(object.getInt("totalPage"));
                            JSONArray jsonArr = object.getJSONArray("questions");
                            for (int i = 0; i < BiHuBean.getTotalCount(); i++) {
                                object = jsonArr.getJSONObject(i);
                                getBitmapFromURL(object.getString("authorAvatar"), IMG_HEAD);
                                getBitmapFromURL(object.getString("images"), IMG_CONTENT);
                                Question question = new Question(object.getInt("id"), object.getString("title"), object.getString("content")
                                        , object.getString("images"), object.getString("date"), object.getString("authorName"), object.getString("authorAvatar"),
                                        object.getBoolean("is_exciting"), object.getBoolean("is_favorite"), bitmap_head, bitmap_content);
                                list.add(question);
                                adapter.notifyDataSetChanged();
                                adapter.notifyItemChanged(i);
                                adapter.notifyItemInserted(list.size() - 1);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public void getBitmapFromURL(String src, int type) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    // 定义一个URL对象
                    URL url = new URL(src);
                    // 打开该URL对应的资源输入流
                    InputStream is = url.openStream();
                    // 从InputStream中解析出图片
                    if (type == IMG_HEAD)
                        bitmap_head = BitmapFactory.decodeStream(is);
                    else bitmap_content = BitmapFactory.decodeStream(is);
                    is.close();
                    // 再次打开该URL对应的资源输入流
                    is = url.openStream();
                    // 打开文件对应的输出流
                    OutputStream os = openFileOutput("bh_head.jpg", MODE_PRIVATE);
                    byte[] buff = new byte[1024];
                    int hasRead = 0;
                    // 将URL对应的资源下载到本地
                    while ((hasRead = is.read(buff)) > 0) {
                        os.write(buff, 0, hasRead);
                    }
                    is.close();
                    os.close();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        list.clear();
        getQues();
    }
}
