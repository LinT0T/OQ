package com.example.qq.discuss;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qq.R;


public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.LinearViewHolder> {

    private Context mContext;
    private ItemOnClickListener mlistener;
    private String tuLingImgPath;

    public FriendsAdapter(Context context, String tuLingImgPath, ItemOnClickListener listener) {
        this.mContext = context;
        this.mlistener = listener;
        this.tuLingImgPath = tuLingImgPath;
    }

    @NonNull
    @Override
    public FriendsAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_item, parent, false);

        LinearViewHolder holder = new LinearViewHolder(view);
        return holder;
        // return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.myadapter_main_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsAdapter.LinearViewHolder holder, final int position) {
        holder.mTextView_friends.setText("MIKU");
        if (tuLingImgPath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(tuLingImgPath);
            holder.mImageView.setImageBitmap(bitmap);
        }
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

    static class LinearViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView mTextView_friends;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.img_friends_item);
            mTextView_friends = itemView.findViewById(R.id.tv_friends_item);
        }
    }

    public interface ItemOnClickListener {
        void onClick(int pos);

    }


}
