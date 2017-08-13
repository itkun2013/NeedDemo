package com.example.refresh.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.refresh.R;
import com.example.refresh.bean.DouBan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shinelon on 2017/4/16.
 */
public class DoubanAdapter extends BaseAdapter {
    private List<DouBan.Detail> mDatas;
    private Context mContext;

    @Override
    public int getCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mDatas != null) {
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            // 没有复用
            // 1.加载xml
            convertView = View.inflate(mContext, R.layout.item_layout, null);
            // 2.新建holder
            holder = new ViewHolder();
            // 3.设置标记
            convertView.setTag(holder);
            // 4. 给holder找view
            holder.mLeftImage = (ImageView) convertView.findViewById(R.id
                    .iv_one_photo);
            holder.mDirector = (TextView) convertView.findViewById(R.id
                    .tv_one_directors);
            holder.mCast = (TextView) convertView.findViewById(R.id
                    .tv_one_casts);
            holder.mNumber = (TextView) convertView.findViewById(R.id
                    .tv_one_rating_number);
            holder.mGenres = (TextView) convertView.findViewById(R.id
                    .tv_one_genres);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 给holder的view设置数据
        DouBan.Detail bean = mDatas.get(position);

        holder.mDirector.setText(bean.directors.get(0).name);
        holder.mCast.setText(bean.casts.get(0).name);
        holder.mGenres.setText("类型：" + bean.genres.get(0));
        holder.mNumber.setText(bean.rating.average + "");
        // 去网络加载
        Glide.with(mContext).load(bean.images.large).into(holder.mLeftImage);

//        boolean flag = PreferenceUtils.getBoolean(mContext, bean.id + "");
//
//        // 设置文本颜色
//        holder.tvTitle.setTextColor(flag ? Color.GRAY : Color.BLACK);

        return convertView;
    }

    private class ViewHolder {
        public ImageView mLeftImage;//左边图
        public TextView mDirector;//导演
        public TextView mCast;//主演
        public TextView mNumber;//类型
        public TextView mGenres;//评分
    }


}
