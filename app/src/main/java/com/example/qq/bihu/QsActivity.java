package com.example.qq.bihu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qq.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class QsActivity extends AppCompatActivity {

    private EditText met_qs, met_content;
    private Button mbtn_send_qs;
    private OkHttpClient mOkHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qs);
        met_qs = findViewById(R.id.et_bh_qs);
        met_content = findViewById(R.id.et_bh_content);
        mbtn_send_qs = findViewById(R.id.btn_bh_send);
        Button button = findViewById(R.id.btn_bh_qs_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mbtn_send_qs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendQues();
            }
        });
    }

    private void sendQues() {
        mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("title", met_qs.getText().toString())
                .add("content", met_content.getText().toString())
                .add("images", "")
                .add("token", BiHuBean.getToken())
                .build();
        Request request = new Request.Builder()
                .url(BiHuBean.getBaseUri() + "question.php")
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
                            if (BiHuBean.getStatus() == 200) {
                                Toast.makeText(getApplicationContext(), BiHuBean.getInfo(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
