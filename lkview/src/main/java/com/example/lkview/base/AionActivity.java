package com.example.lkview.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.example.lkview.R;

/**
 * Created by Shinelon on 2016/12/11.
 */
public abstract class AionActivity  extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    protected abstract void initView();

    protected abstract void initData();
}
