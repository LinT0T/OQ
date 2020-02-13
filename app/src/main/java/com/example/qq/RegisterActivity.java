package com.example.qq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText met_repassword, met_name, met_password, met_save;
    private Button mbtn_register;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        met_name = findViewById(R.id.et_register_username);
        met_password = findViewById(R.id.et_register_password);
        met_repassword = findViewById(R.id.et_register_repassword);
        met_save = findViewById(R.id.et_register_save);
        mbtn_register = findViewById(R.id.btn_register);
        sharedPreferences = getSharedPreferences("userdata", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        intent = new Intent(RegisterActivity.this, LoginActivity.class);
        if (!sharedPreferences.getString("username", "").equals("")) {
            Toast.makeText(RegisterActivity.this, "账号已存在，若忘记请使用忘记密码", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }
        mbtn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password, repassword;
                password = met_password.getText().toString();
                repassword = met_repassword.getText().toString();
                Log.d("onClick___ ",password + "  " + repassword);
                if ((!met_name.getText().toString().equals("")) && (!password.equals("")) && (!repassword.equals("")) && (!met_save.getText().toString().equals(""))) {
                    if (password.equals(repassword)) {
                        editor.putString("username", met_name.getText().toString());
                        editor.putString("password", password);
                        editor.putString("save", met_save.getText().toString());
                        editor.apply();
                        Toast.makeText(RegisterActivity.this, "注册成功，2秒后跳转至登录界面", Toast.LENGTH_SHORT).show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent);
                                finish();
                            }
                        }, 2000);

                    } else
                        Toast.makeText(RegisterActivity.this, "密码不一致，请重新输入", Toast.LENGTH_SHORT).show();}
                else Toast.makeText(RegisterActivity.this, "请输入完整信息", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
