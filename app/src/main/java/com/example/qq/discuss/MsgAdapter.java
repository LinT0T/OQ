package com.example.qq.discuss;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qq.DiscussActivity;
import com.example.qq.R;

import java.util.List;
import java.util.ResourceBundle;

import static android.content.Context.MODE_PRIVATE;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private List<Msg> mMsgList;
    private String mImagepath;
    private String mTuLingImagepath;
    static class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout otherLayout;
        private LinearLayout meLayout;
        private ImageView imageView1;
        private ImageView imageView2;
        private TextView otherTextView;
        private TextView meTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            otherLayout = itemView.findViewById(R.id.layout_other);
            meLayout = itemView.findViewById(R.id.layout_me);
            imageView1 = itemView.findViewById(R.id.img_other_item);
            imageView2 = itemView.findViewById(R.id.img_me_item);
            otherTextView = itemView.findViewById(R.id.tv_msg_other_item);
            meTextView = itemView.findViewById(R.id.tv_msg_me_item);
        }
    }

    public MsgAdapter(List<Msg> msgList,String imagepath,String s) {
        this.mMsgList = msgList;
        this.mImagepath = imagepath;
        this.mTuLingImagepath = s;
    }

    @NonNull
    @Override
    public MsgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discuss_main_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MsgAdapter.ViewHolder holder, int position) {
        Msg msg = mMsgList.get(position);
        if (mTuLingImagepath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(mTuLingImagepath);
            holder.imageView1.setImageBitmap(bitmap);
        }
        if (mImagepath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(mImagepath);
            holder.imageView2.setImageBitmap(bitmap);
        }

        if (msg.getMtype() == Msg.TYPE_OTHER) {
            holder.otherLayout.setVisibility(View.VISIBLE);
            holder.meLayout.setVisibility(View.GONE);
            holder.otherTextView.setText(msg.getMcontent());
        } else {
            holder.meLayout.setVisibility(View.VISIBLE);
            holder.otherLayout.setVisibility(View.GONE);
            holder.meTextView.setText(msg.getMcontent());
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }
}
