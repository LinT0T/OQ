package com.example.qq.bihu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class BiHuActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mbtn_bh_login, mbtn_bh_register, mbtn_login_with_oq;
    private EditText met_bh_name, met_bh_password;
    private OkHttpClient mOkHttpClient;
    private TextView test;
    private CheckBox checkBox;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bi_hu);
        mbtn_login_with_oq = findViewById(R.id.login_with_oq);
        mbtn_bh_login = findViewById(R.id.btn_bh_login);
        met_bh_name = findViewById(R.id.edt_bh_user);
        met_bh_password = findViewById(R.id.edt_bh_password);
        mbtn_bh_register = findViewById(R.id.btn_bh_register);
        checkBox = findViewById(R.id.cb_bh_remme);
        test = findViewById(R.id.test);
        sharedPreferences = getSharedPreferences("userdata", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        initData();
        mbtn_bh_login.setOnClickListener(this);
        mbtn_bh_register.setOnClickListener(this);
        mbtn_login_with_oq.setOnClickListener(this);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    editor.putBoolean("bhremme", true);
                else
                    editor.putBoolean("bhremme", false);
                editor.apply();
            }
        });
        boolean haveLogin = sharedPreferences.getBoolean("bhhavelogin", false);
        if (sharedPreferences.getBoolean("bhremme", false) && haveLogin) {
            checkBox.setChecked(true);
            met_bh_name.setText(BiHuBean.getUsername());
            met_bh_password.setText(BiHuBean.getPassword());
        }
    }

    private void initData() {
        BiHuBean.setUsername(sharedPreferences.getString("bhusername", ""));
        BiHuBean.setPassword(sharedPreferences.getString("bhpassword", ""));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bh_login:
                loginPost(met_bh_name.getText().toString(), met_bh_password.getText().toString());
                break;
            case R.id.btn_bh_register:
                if (BiHuBean.getHave() == 0)
                    registerPost(met_bh_name.getText().toString(), met_bh_password.getText().toString());
                else Toast.makeText(getApplicationContext(), "账号已存在", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_with_oq:
                String s1, s2;
                s1 = sharedPreferences.getString("username", null);
                s2 = sharedPreferences.getString("password", null);
                if (BiHuBean.getUsername().equals(""))
                    registerPost(s1, s2);
                loginPost(s1, s2);
                break;
        }
    }

    private void loginPost(String name, String pass) {
        mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("username", name)
                .add("password", pass)
                .build();
        Request request = new Request.Builder()
                .url(BiHuBean.getBaseUri() + "login.php")
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
                            if (BiHuBean.getStatus() == 200) {
                                String data = object.getString("data");
                                JSONObject object2 = new JSONObject(data);
                                editor.putString("bhusername", name);
                                editor.putString("bhpassword", pass);
                                BiHuBean.setToken(object2.getString("token"));
                                editor.putBoolean("bhhavelogin", true);
                                editor.apply();
                                Intent intent = new Intent(BiHuActivity.this, MainBiHuActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), BiHuBean.getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            test.setText(BiHuBean.getInfo());
                        }
                    }
                });
            }
        });
    }

    private void registerPost(String name, String pass) {
        mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("username", name)
                .add("password", pass)
                .build();
        Request request = new Request.Builder()
                .url(BiHuBean.getBaseUri() + "register.php")
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
                            String data = object.getString("data");
                            JSONObject object2 = new JSONObject(data);
                            BiHuBean.setUsername(object2.getString("username"));
                            BiHuBean.setPassword(object2.getString("password"));
                            BiHuBean.setToken(object2.getString("token"));
                            BiHuBean.setHave(1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
