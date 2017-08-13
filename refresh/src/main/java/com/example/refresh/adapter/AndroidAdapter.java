package com.example.refresh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.refresh.R;
import com.example.refresh.bean.DouBan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingbin on 2016/12/2.
 */

public class AndroidAdapter extends RecyclerView.Adapter<AndroidAdapter.DaoBanViewHolder> {

    /**
     * 数据
     */
    private List<DouBan.Detail> mDatas = new ArrayList<>();
    private Context mContext;

    public AndroidAdapter(List<DouBan.Detail> datas, Context context) {
        this.mDatas = datas;
        mContext = context;
    }

    @Override
    public AndroidAdapter.DaoBanViewHolder onCreateViewHolder(final ViewGroup
                                                                      parent,
                                                              int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_layout, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent.getContext(), "good", Toast
                        .LENGTH_SHORT).show();
            }
        });
        return new AndroidAdapter.DaoBanViewHolder(view) {
        };
    }


    @Override
    public void onBindViewHolder(AndroidAdapter.DaoBanViewHolder holder, int
            position) {
        Log.e("adapter","postion: "+position);
        Glide.with(mContext).load(mDatas.get(position)
                .images
                .large).into(holder.mLeftImage);
        holder.mDirector.setText(mDatas.get(position)
                .directors.get(0).name);
        holder.mCast.setText(mDatas.get(position)
                .casts.get(0).name);
        holder.mGenres.setText("类型：" + mDatas.get(position)
                .genres.get(0));
        holder.mNumber.setText(mDatas.get(position)
                .rating.average + "");
    }

    @Override
    public int getItemCount() {

        return mDatas.size();
    }

    class DaoBanViewHolder extends RecyclerView.ViewHolder {
        public ImageView mLeftImage;//左边图
        public TextView mDirector;//导演
        public TextView mCast;//主演
        public TextView mNumber;//类型
        public TextView mGenres;//评分

        public DaoBanViewHolder(View itemView) {
            super(itemView);
            mLeftImage = (ImageView) itemView.findViewById(R.id
                    .iv_one_photo);
            mDirector = (TextView) itemView.findViewById(R.id
                    .tv_one_directors);
            mCast = (TextView) itemView.findViewById(R.id
                    .tv_one_casts);
            mNumber = (TextView) itemView.findViewById(R.id
                    .tv_one_rating_number);
            mGenres = (TextView) itemView.findViewById(R.id
                    .tv_one_genres);
        }
    }

    public void addAllReresh(List<DouBan.Detail> data) {
//        this.mDatas.addAll(data);
        mDatas.addAll(0,data);
    }
    public void addAllMore(List<DouBan.Detail> data) {
        this.mDatas.addAll(data);
//        mDatas.addAll(0,data);
    }

    public void add(DouBan.Detail object) {
        mDatas.add(object);
    }

    public void clear() {
        mDatas.clear();
    }

}
