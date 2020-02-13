package com.example.qq;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qq.bean.ImgPath;
import com.example.qq.discuss.Msg;
import com.example.qq.discuss.MsgAdapter;
import com.example.qq.tuling.Input;
import com.example.qq.tuling.TulingMenuActivity;

import java.util.ArrayList;
import java.util.List;

public class DiscussActivity extends AppCompatActivity {

    private Button mbtn_back;
    private RecyclerView mrv_discuss;
    private List<Msg> msgList = new ArrayList<>();
    private EditText medt_discuss;
    private String sendText = "";
    private Msg msg2;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            if (!sendText.equals("")) {
                intent.putExtra("result",msg2.getMcontent());
                setResult(3,intent);
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
             setContentView(R.layout.activity_discuss);

//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .detectDiskReads().detectDiskWrites().detectNetwork()
//                .penaltyLog().build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
//                .penaltyLog().penaltyDeath().build());

        medt_discuss = findViewById(R.id.et_discuss);
        mbtn_back = findViewById(R.id.btn_back);
        sharedPreferences = getSharedPreferences("userdata",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Button mbtn_takephoto = findViewById(R.id.btn_takephoto);
        Button mbtn_list = findViewById(R.id.other_list);
        mbtn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiscussActivity.this, TulingMenuActivity.class);
                startActivity(intent);
            }
        });
        mbtn_takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);
            }
        });
      //  SharedPreferences sharedPreferences = getSharedPreferences("userdata",MODE_PRIVATE);
       // String imgPath = sharedPreferences.getString("imagepath",null);
       // String tuLingimgPath = sharedPreferences.getString("tulingimagepath",null);
        String imgPath = ImgPath.getUserPath();
        String tuLingimgPath = ImgPath.getTuLingPath();
        final MsgAdapter adapter =new MsgAdapter(msgList,imgPath,tuLingimgPath);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        initMsgs(adapter);
        mrv_discuss = findViewById(R.id.rv_discuss);
        mrv_discuss.setLayoutManager(linearLayoutManager);
        mrv_discuss.setAdapter(adapter);
        Button button1 = findViewById(R.id.bt_send);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendText = medt_discuss.getText().toString();
                if (!"".equals(sendText)){
                    Msg msg = new Msg(sendText,Msg.TYPE_ME);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size() - 1);
                    mrv_discuss.scrollToPosition(msgList.size() - 1);
                    medt_discuss.setText("");
                    msg2 = new Msg(Input.getString(sendText),Msg.TYPE_OTHER);
                    msgList.add(msg2);
                    adapter.notifyItemInserted(msgList.size() - 1);
                    mrv_discuss.scrollToPosition(msgList.size() - 1);
                    medt_discuss.setText("");
                }
            }
        });

        mbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  moveTaskToBack(true);
                Intent intent = new Intent();
                if (!sendText.equals("")) {
                intent.putExtra("result",msg2.getMcontent());
                setResult(3,intent);
                }
                finish();
            }
        });
    }

    private void initMsgs(MsgAdapter adapter) {
        int size = sharedPreferences.getInt("size",0);
        if (size == 0) {
            Msg msg1 = new Msg("我们已经是好友了，快开始聊天吧！", Msg.TYPE_OTHER);
            msgList.add(msg1);
        }
        else {
            String s = null;
            Msg msg3;
            for(int i=0;i<size;i++) {
                s = sharedPreferences.getString("item" + i,null);
                if (i % 2 == 0) {
                    msg3 = new Msg(s,Msg.TYPE_OTHER);
                    msgList.add(msg3);
                    adapter.notifyItemInserted(msgList.size() - 1);
                } else {
                    msg3 = new Msg(s,Msg.TYPE_ME);
                    msgList.add(msg3);
                    adapter.notifyItemInserted(msgList.size() - 1);
                }
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.putInt("size",msgList.size());
        for (int i = 0; i < msgList.size(); i++) {
            editor.putString("item" + i, msgList.get(i).getMcontent());
        }
        editor.apply();
    }
}
