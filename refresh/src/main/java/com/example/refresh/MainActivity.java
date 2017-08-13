package com.example.refresh;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.refresh.activity.BaseActivity;
import com.example.refresh.adapter.AndroidAdapter;
import com.example.refresh.bean.DouBan;
import com.example.refresh.common.RetrofitManager;
import com.example.refresh.view.RefreshListView;
import com.example.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private CompositeSubscription mCompositeSubscription;
    private XRecyclerView xRecyclerView;
    protected AndroidAdapter mAndroidAdapter;
    protected int mPage = 1;
    /**
     * 数据
     */
    private List<DouBan.Detail> mDatas;
    private RefreshListView mListView;
    private boolean isRefresh; //是否要刷新

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatas = new ArrayList<DouBan.Detail>();
        //创建适配器
        mAndroidAdapter = new AndroidAdapter(mDatas, this);
        //第三方自定义
        xRecyclerView = (XRecyclerView) findViewById(R.id.xrv_android);
        //事件
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                Log.e(TAG, "onRefresh");
//                mPage = 1;
                isRefresh = true;
                mPage++;
                loadAndroidData();
            }

            @Override
            public void onLoadMore() {
                Log.e(TAG, "onLoadMore");
                mPage++;
                loadAndroidData();
            }
        });
        //只加载一次
        loadData();

    }

    private void loadData() {
        if (mDatas != null && mDatas.size() > 0) {
            showContentView();
            setAdapter(mDatas);
        } else {
            loadAndroidData();
        }
    }

    private void loadAndroidData() {
        Subscription subscribe = RetrofitManager.getInstance().netWorkUtil.getDouBan((mPage - 1) * 10, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DouBan>() {
                    @Override
                    public void onCompleted() {
                        showContentView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        xRecyclerView.refreshComplete();
                        showError();
                        if (mPage > 1) {
                            mPage--;
                        }
                    }

                    @Override
                    public void onNext(DouBan douBan) {
                        mDatas = douBan.subjects;
                        Log.e(TAG, "mPage " + mPage + "  size:" + mDatas.size());
                        if (mPage == 1) {
                            if (mDatas != null && mDatas.size() > 0) {
                                setAdapter(mDatas);
                            }
                        } else {
                            if (mDatas != null && mDatas.size() > 0) {
                                Log.e(TAG, "mDatas " + mDatas.size());
                                //如果是下拉刷新
                                if (isRefresh) {
                                    //显示最新的数据
                                    isRefresh = false;
                                    xRecyclerView.refreshComplete();
                                    mAndroidAdapter.addAllReresh(mDatas);
                                    mAndroidAdapter.notifyDataSetChanged();

                                } else {
                                    //上拉加载
                                    xRecyclerView.refreshComplete();
                                    mAndroidAdapter.addAllMore(mDatas);
                                    mAndroidAdapter.notifyDataSetChanged();

                                }

                            } else {
                                xRecyclerView.noMoreLoading();
                            }
                        }
                    }

                });
        addSubscription(subscribe);
    }

    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * 设置adapter
     */
    private void setAdapter(List<DouBan.Detail> data) {
        mAndroidAdapter.clear();
        mAndroidAdapter.addAllMore(data);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        xRecyclerView.setAdapter(mAndroidAdapter);
        mAndroidAdapter.notifyDataSetChanged();
        xRecyclerView.refreshComplete();
    }
}
