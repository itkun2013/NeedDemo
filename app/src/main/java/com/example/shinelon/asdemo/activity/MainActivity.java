package com.example.shinelon.asdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shinelon.asdemo.R;
import com.example.shinelon.asdemo.celuemoshi2.CashContext;
import com.example.shinelon.asdemo.constans.Constans;

public class MainActivity extends Activity {
    double totle=0.0d;//声明一个变量，totle计算总量
private Button mButton;
private TextView mnorTextView;
private TextView mfanTextView;
private TextView meigTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mnorTextView = (TextView) findViewById(R.id.normal_price);
        mfanTextView = (TextView) findViewById(R.id.fan_price);
        meigTextView = (TextView) findViewById(R.id.eight_price);
    }
    public void calculate(View v){
        double totlePrices = Constans.txtPrice*Constans.tetNum;
        CashContext mCash = new CashContext("打8折");
        mCash.GetResult(100);
        meigTextView.setText("单价："+Constans.txtPrice+"数量： "+Constans.tetNum+"8折合计： "+mCash.GetResult(totlePrices));
    }

}
