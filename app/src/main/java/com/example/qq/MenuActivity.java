package com.example.qq;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qq.bean.ImgPath;

public class MenuActivity extends AppCompatActivity {

    private Button mbtn_back,mbtn_finish,mbtn_delete;
    private MyImageView myImageView;
    private final static int CHOOSE_PHOTO = 2;
  //  private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sliping_menu);
        mbtn_back = findViewById(R.id.btn_back2);
        myImageView = findViewById(R.id.img_head);
        mbtn_finish = findViewById(R.id.btn_finish);
        mbtn_delete = findViewById(R.id.btn_delete);
     //   sharedPreferences = getSharedPreferences("userdata",MODE_PRIVATE);
        String imagepath = ImgPath.getUserPath();
        if (imagepath != null)
        {
            Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
            myImageView.setImageBitmap(bitmap);
        }
        myImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,ChooseHeadActivity.class);
                startActivity(intent);
            }
        });
        mbtn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("userdata", MODE_PRIVATE);
                sharedPreferences.edit().remove("autologin").apply();
                Intent intent = new Intent(MenuActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mbtn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.warning,null);
                Button button1 = view.findViewById(R.id.btn_sure_del);
                Button button2 = view.findViewById(R.id.btn_no_del);
                PopupWindow pop = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = getSharedPreferences("userdata",MODE_PRIVATE);
                        sharedPreferences.edit().clear().apply();
                        Toast.makeText(MenuActivity.this,"数据清空完成,重新启动",Toast.LENGTH_SHORT).show();
                        final Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop.dismiss();
                    }
                });
                pop.showAtLocation(view, Gravity.CENTER,0,0);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String imagepath = ImgPath.getUserPath();
        if (imagepath != null)
        {
            Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
            myImageView.setImageBitmap(bitmap);
        }
    }
}
