package com.example.qq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private boolean ifLogin = false;
    private boolean ifInput_password = false;
    private Button mbtn_login;
    private TextView mtv_register, mtv_forget;
    EditText mEdT_user, mEdT_password;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    String mUsername, mPassword;
    private CheckBox mcb_remme,mcb_auto_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEdT_user = findViewById(R.id.edt_user);
        mEdT_password = findViewById(R.id.edt_password);
        mbtn_login = findViewById(R.id.btn_login);
        mtv_register = findViewById(R.id.tv_register);
        mtv_forget = findViewById(R.id.tv_forget);
        mcb_remme = findViewById(R.id.cb_remme);
        mcb_auto_login = findViewById(R.id.cb_auto_login);
        sharedPreferences = getSharedPreferences("userdata", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mUsername = sharedPreferences.getString("username", "");
        mPassword = sharedPreferences.getString("password", "");
        setListenrs();
        boolean haveLogin =sharedPreferences.getBoolean("havelogin",false);
        if (sharedPreferences.getBoolean("autologin",false) && haveLogin){
            mcb_auto_login.setChecked(true);
            Intent intent = new Intent(LoginActivity.this,MainViewActivity.class);
            startActivity(intent);
            finish();
        }
        if (sharedPreferences.getBoolean("remme", false) && haveLogin) {
            mcb_remme.setChecked(true);
            mEdT_user.setText(mUsername);
            mEdT_password.setText(mPassword);
        }
    }

    private void setListenrs() {
        OnClick onClick = new OnClick();
        mbtn_login.setOnClickListener(onClick);
        mtv_register.setOnClickListener(onClick);
        mtv_forget.setOnClickListener(onClick);
        mcb_remme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    editor.putBoolean("remme", true);
                else
                    editor.putBoolean("remme", false);
                editor.apply();
            }
        });
        mcb_auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    editor.putBoolean("autologin", true);
                else
                    editor.putBoolean("autologin", false);
                editor.apply();
            }
        });
    }

    private class OnClick implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.btn_login: {
                    String userName = mEdT_user.getText().toString();
                    String userPassword = mEdT_password.getText().toString();
                    if (!userName.equals("") && !userPassword.equals("")) {
                        if (userName.equals(mUsername) && userPassword.equals(mPassword)) {
                            intent = new Intent(LoginActivity.this, MainViewActivity.class);
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            editor.putBoolean("havelogin",true);
                            editor.apply();
                            startActivity(intent);
                            finish();
                        } else
                            Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(LoginActivity.this, "请输入用户名或密码", Toast.LENGTH_SHORT).show();
                    break;
                }
                case R.id.tv_register:
                    intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tv_forget:
                    intent = new Intent(LoginActivity.this, ForgetActivity.class);
                    startActivity(intent);
                    break;
            }

        }
    }
}
