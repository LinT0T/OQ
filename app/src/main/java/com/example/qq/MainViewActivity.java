package com.example.qq;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.qq.bean.ImgPath;
import com.example.qq.fragment.ChatFragment;
import com.example.qq.fragment.FriendsFragment;
import com.example.qq.fragment.QzoneFragment;

import java.util.ArrayList;
import java.util.List;

public class MainViewActivity extends AppCompatActivity {

    private TextView mtv_title;
    private Button mbtn_chat, mbtn_Qzone, mbtn_friends, mbtn_camera, mbtn_pop;
    private List<String> nowMsg = new ArrayList<>();
    private ChatFragment chatFragment;
    private FriendsFragment friendsFragment;
    private QzoneFragment qzoneFragment;
    private FragmentManager fragmentManager;
    private MyImageView myImageView;
    // private SharedPreferences sharedPreferences;
    private String tuLingImagePath;
    private PopupWindow mPop;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        mtv_title = findViewById(R.id.tv_title);
        mbtn_camera = findViewById(R.id.btn_camera);
        mbtn_friends = findViewById(R.id.btn_friends);
        mbtn_chat = findViewById(R.id.btn_chat);
        mbtn_Qzone = findViewById(R.id.btn_Qzone);
        myImageView = findViewById(R.id.head);
        mbtn_pop = findViewById(R.id.btn_pop_menu);
        chatFragment = new ChatFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fl_main, chatFragment).commitAllowingStateLoss();
        // sharedPreferences = getSharedPreferences("userdata",MODE_PRIVATE);  多次使用sp会导致主线程阻塞，这里通过定义一个数据类避免sp过多调用
        // String imagepath = sharedPreferences.getString("imagepath",null);
        String imagepath = ImgPath.getUserPath();
        // tuLingImagePath = sharedPreferences.getString("tulingimagepath",null);
        tuLingImagePath = ImgPath.getTuLingPath();
        if (imagepath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
            myImageView.setImageBitmap(bitmap);
        }
        setListenrs();


    }

    private void setListenrs() {
        OnClick onClick = new OnClick();
        mbtn_camera.setOnClickListener(onClick);
        mbtn_friends.setOnClickListener(onClick);
        mbtn_Qzone.setOnClickListener(onClick);
        mbtn_chat.setOnClickListener(onClick);
        myImageView.setOnClickListener(onClick);
        mbtn_pop.setOnClickListener(onClick);
    }

    private class OnClick implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.btn_camera:
                    intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivity(intent);
                    break;
                case R.id.btn_friends:
                    mtv_title.setText("联系人");
                    if (friendsFragment == null) {
                        friendsFragment = new FriendsFragment();
                        fragmentManager.beginTransaction().replace(R.id.fl_main, friendsFragment).commitAllowingStateLoss();
                    } else
                        fragmentManager.beginTransaction().replace(R.id.fl_main, friendsFragment).commitAllowingStateLoss();
                    break;
                case R.id.btn_chat:
                    mtv_title.setText("消息");
                    if (chatFragment == null) {
                        chatFragment = new ChatFragment();
                        fragmentManager.beginTransaction().replace(R.id.fl_main, chatFragment).commitAllowingStateLoss();
                    } else
                        fragmentManager.beginTransaction().replace(R.id.fl_main, chatFragment).commitAllowingStateLoss();
                    break;

                case R.id.btn_Qzone:
                    mtv_title.setText("动态");
                    if (qzoneFragment == null) {
                        qzoneFragment = new QzoneFragment();
                        fragmentManager.beginTransaction().replace(R.id.fl_main, qzoneFragment).commitAllowingStateLoss();
                    } else
                        fragmentManager.beginTransaction().replace(R.id.fl_main, qzoneFragment).commitAllowingStateLoss();
                    break;

                case R.id.head:
                    Intent i = new Intent(MainViewActivity.this, MenuActivity.class);
                    startActivity(i);
                    break;

                case R.id.btn_pop_menu:
                    if (mPop == null) {
                        View view = getLayoutInflater().inflate(R.layout.pop_menu, null);
                        mPop = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        mPop.setOutsideTouchable(true);
                        mPop.setFocusable(true);
                        mPop.setAnimationStyle(R.style.animTranslate);
                        mPop.showAsDropDown(mbtn_pop);
                    } else {
                            mPop.showAsDropDown(mbtn_pop);
                    }
                    break;
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //String imagepath = sharedPreferences.getString("imagepath",null);
        String imagepath = ImgPath.getUserPath();
        // tuLingImagePath = sharedPreferences.getString("tulingimagepath",null);
        tuLingImagePath = ImgPath.getTuLingPath();
        if (imagepath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
            myImageView.setImageBitmap(bitmap);
        }
    }

    public String getTuLingImagePath() {
        return tuLingImagePath;
    }
}
