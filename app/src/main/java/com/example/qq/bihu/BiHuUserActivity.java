package com.example.qq.bihu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.qq.R;

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

public class BiHuUserActivity extends AppCompatActivity {

    private OkHttpClient mOkHttpClient;
    EditText editText1, editText2;
    Button button_cgpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bi_hu_user);
        Button button = findViewById(R.id.btn_bh_change_password);
        Button button2 = findViewById(R.id.btn_bh_back);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.bh_change_password_pop, null);
                button_cgpass = view.findViewById(R.id.btn_bh_sure_change_pass);
                editText1 = view.findViewById(R.id.et_bh_new_pass);
                editText2 = view.findViewById(R.id.et_bh_new_repass);
                PopupWindow pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                pop.setOutsideTouchable(true);
                pop.setFocusable(true);
                button_cgpass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editText1.getText().toString().equals(editText2.getText().toString()) && (!editText1.getText().toString().equals("")) && (!editText2.getText().toString().equals(""))) {
                            loginPost();
                            pop.dismiss();
                        } else Toast.makeText(BiHuUserActivity.this,"两次输入不一致",Toast.LENGTH_SHORT).show();
                    }
                });
                pop.showAtLocation(view, Gravity.CENTER, 0, 0);
            }
        });
    }

    private void loginPost() {
        mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("password", editText1.getText().toString())
                .add("token", BiHuBean.getToken())
                .build();
        Request request = new Request.Builder()
                .url(BiHuBean.getBaseUri() + "changePassword.php")
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
                        try {
                            JSONObject object = new JSONObject(str);
                            BiHuBean.setStatus(object.getInt("status"));
                            BiHuBean.setInfo(object.getString("info"));
                            Toast.makeText(getApplicationContext(), BiHuBean.getInfo(), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
