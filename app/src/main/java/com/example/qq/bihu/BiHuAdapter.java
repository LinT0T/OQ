package com.example.qq.bihu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qq.MyImageView;
import com.example.qq.R;

import java.util.List;

public class BiHuAdapter extends RecyclerView.Adapter<BiHuAdapter.ViewHolder> {

    private List<Question> qsList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        private MyImageView mimg_head;
        private ImageView mimg_content;
        private TextView mtv_name, mtv_content, mtv_date,mtv_title;
        private EditText met_answer;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mimg_head = itemView.findViewById(R.id.img_bh_head);
            mtv_name = itemView.findViewById(R.id.tv_bh_name);
            mtv_content = itemView.findViewById(R.id.tv_bh_content);
            mtv_date = itemView.findViewById(R.id.tv_time);
            met_answer = itemView.findViewById(R.id.et_bh_answer);
            mimg_content = itemView.findViewById(R.id.img_bh_content);
            mtv_title = itemView.findViewById(R.id.tv_bh_title_item);
        }
    }

    public BiHuAdapter(List<Question> qsList) {
        this.qsList = qsList;
    }

    @NonNull
    @Override
    public BiHuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bh_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BiHuAdapter.ViewHolder holder, int position) {
        Question question = qsList.get(position);
        holder.mtv_name.setText(question.getAuthorName());
        holder.mtv_content.setText(question.getContent());
        holder.mtv_date.setText(question.getDate());
        holder.mtv_title.setText(question.getTitle());
        if (question.getBitmap_head() != null)
            holder.mimg_head.setImageBitmap(question.getBitmap_head());
        holder.mimg_content.setImageBitmap(question.getBitmap_content());

    }

    @Override
    public int getItemCount() {
        return qsList.size();
    }


}
