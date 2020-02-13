package com.example.qq.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qq.DiscussActivity;
import com.example.qq.MainViewActivity;
import com.example.qq.MyMainAdapter;
import com.example.qq.R;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    private RecyclerView mrv_main;
    private Context mContext;
    private List<String> mNowMsg = new ArrayList<>();


    private  MyMainAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        String imagepath = ((MainViewActivity) mContext).getTuLingImagePath();
        Log.d("onCreateView: " , "------------" + imagepath);
        mrv_main = view.findViewById(R.id.rv_main);

        adapter = new MyMainAdapter(mContext,imagepath, mNowMsg, new MyMainAdapter.ItemOnClickListener() {
            @Override
            public void onClick(int pos) {
                Intent intent = new Intent(mContext, DiscussActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mrv_main.setLayoutManager(linearLayoutManager);
        mrv_main.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 3) {
            String result = data.getStringExtra("result");
            mNowMsg.add(result);
            adapter.notifyDataSetChanged();
            adapter.notifyItemChanged(0);
        }
    }
}