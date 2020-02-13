package com.example.qq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class ForgetActivity extends AppCompatActivity {

    private Button mbtn_help;
    private EditText met_help;
    private TextView mtv_help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        mbtn_help = findViewById(R.id.btn_help);
        met_help = findViewById(R.id.et_help);
        mtv_help = findViewById(R.id.tv_help);
        mbtn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("userdata",MODE_PRIVATE);
                if (met_help.getText().toString().equals(sharedPreferences.getString("save",""))){
                    String s;
                    s = "账号：" + sharedPreferences.getString("username","") + "  密码：" + sharedPreferences.getString("password","");
                    mtv_help.setText(s);
                }
            }
        });
    }
}
