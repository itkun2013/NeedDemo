package com.example.refresh.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.refresh.R;

import rx.subscriptions.CompositeSubscription;


/**
 * Created by Shinelon on 2017/3/27.
 */
public class BaseActivity extends AppCompatActivity {
    // 加载中
    private LinearLayout mLlProgressBar;
    // 加载失败
    private LinearLayout mRefresh;
    // 内容布局
    protected RelativeLayout mContainer;
    // 动画
    private AnimationDrawable mAnimationDrawable;
    private CompositeSubscription mCompositeSubscription;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.fragment_base);
        mLlProgressBar = (LinearLayout) findViewById(R.id.ll_progress_bar);
        ImageView img = (ImageView) findViewById(R.id.img_progress);

        // 加载动画
        mAnimationDrawable = (AnimationDrawable) img.getDrawable();
        // 默认进入页面就开启动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
        mRefresh = (LinearLayout) findViewById(R.id.ll_error_refresh);

    }

    public void showContentView() {
        if (mLlProgressBar != null) {

            if (mLlProgressBar.getVisibility() != View.GONE) {
                mLlProgressBar.setVisibility(View.GONE);
            }
        }
        // 停止动画
        if (mAnimationDrawable != null) {

            if (mAnimationDrawable.isRunning()) {
                mAnimationDrawable.stop();
            }
        }
        if (mRefresh != null) {

            if (mRefresh.getVisibility() != View.GONE) {
                mRefresh.setVisibility(View.GONE);
            }
        }

    }

    /**
     * 加载失败点击重新加载的状态
     */
    protected void showError() {
        if (mLlProgressBar.getVisibility() != View.GONE) {
            mLlProgressBar.setVisibility(View.GONE);
        }
        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        if (mRefresh.getVisibility() != View.VISIBLE) {
            mRefresh.setVisibility(View.VISIBLE);
        }
    }


}
