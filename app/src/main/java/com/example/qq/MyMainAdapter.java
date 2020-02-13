package com.example.qq;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;



public class MyMainAdapter extends RecyclerView.Adapter <MyMainAdapter.LinearViewHolder> {

    private Context mContext;
    private ItemOnClickListener mlistener;
    private List<String> mNowMsg;
    private String tuLingImgPath;

    public MyMainAdapter(Context context,String s,List<String> nowMsg,ItemOnClickListener listener){
        this.mContext = context;
        this.mlistener = listener;
        this.mNowMsg = nowMsg;
        this.tuLingImgPath = s;
    }
    @NonNull
    @Override
    public MyMainAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myadapter_main_item,parent,false);

        LinearViewHolder holder = new LinearViewHolder(view);
        return holder;
       // return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.myadapter_main_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyMainAdapter.LinearViewHolder holder, final int position) {
        holder.mTextView_name.setText("MIKU");
        Log.d("onBindViewHolder: " , "------------------" + tuLingImgPath);
        if(tuLingImgPath != null){
            {
                Bitmap bitmap = BitmapFactory.decodeFile(tuLingImgPath);
                holder.mImageView.setImageBitmap(bitmap);
            }

        }
        int point = mNowMsg.size();
        if (point == 0) holder.mTextView_content.setText("我们已经是好友了，快开始聊天吧！");
        else holder.mTextView_content.setText(mNowMsg.get(point - 1));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener.onClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static class LinearViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageView;
        private TextView mTextView_name;
        private TextView mTextView_content;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.img_main_item);
            mTextView_name = itemView.findViewById(R.id.tv_name_item);
            mTextView_content = itemView.findViewById(R.id.tv_content_item);
        }
    }

    public interface ItemOnClickListener{
        void onClick(int pos);

    }



}
