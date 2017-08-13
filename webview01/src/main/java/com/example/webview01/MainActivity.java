package com.example.webview01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    protected WebView mWebView;
    protected Button mButton;
//    private TextView logTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.webview);
        mButton = (Button) findViewById(R.id.btn_click);
        //设置编码
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        //支持js代码
        mWebView.getSettings().setJavaScriptEnabled(true);
        //设置背景透明
        mWebView.setBackgroundColor(0);
        //设置本地对象,定义js与安卓约束的方法
        mWebView.addJavascriptInterface(new AndriodJsObject(this), "andriodweb");
        //载入js
        mWebView.loadUrl("file:///android_asset/test.html");
        //点击调用js方法
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl("javascript:funFromjs()");

            }
        });

    }


}
