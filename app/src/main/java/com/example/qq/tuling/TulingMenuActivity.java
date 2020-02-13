package com.example.qq.tuling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qq.MyImageView;
import com.example.qq.R;
import com.example.qq.bean.ImgPath;

public class TulingMenuActivity extends AppCompatActivity {

    private Button mbtn_back,mbtn_delete;
    private MyImageView myImageView;
    private final static int CHOOSE_PHOTO = 2;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tuling_sliping_menu);
        mbtn_back = findViewById(R.id.btn_tuling_back2);
        myImageView = findViewById(R.id.img_tuling_head);
        sharedPreferences = getSharedPreferences("userdata",MODE_PRIVATE);
        String imagepath = ImgPath.getTuLingPath();
        if (imagepath != null)
        {
            Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
            myImageView.setImageBitmap(bitmap);
        }
        myImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TulingMenuActivity.this, TulingChooseHeadActivity.class);
                startActivity(intent);
            }
        });
        mbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mbtn_delete = findViewById(R.id.btn_tuling_delete);
        mbtn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                int i = sharedPreferences.getInt("size",0);
                for (int j = 0; j < i; j++) {
                    editor.remove("item" + j);
                }
                editor.remove("size");
                editor.apply();
                Toast.makeText(TulingMenuActivity.this,"清除成功",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String imagepath = ImgPath.getTuLingPath();
        if (imagepath != null)
        {
            Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
            myImageView.setImageBitmap(bitmap);
        }
    }
}
