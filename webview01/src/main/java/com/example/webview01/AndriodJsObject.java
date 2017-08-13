package com.example.webview01;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by Shinelon on 2016/10/24.
 */
public class AndriodJsObject {
    protected Context mContext;

    protected AndriodJsObject(Context context) {
        mContext = context;
    }

    //js调用安卓方法
    @JavascriptInterface
    public void fun1FromAndroid(String str) {
        Toast.makeText(mContext, "js调用" + str, Toast.LENGTH_SHORT).show();
    }

    //js调用有参安卓方法
    @JavascriptInterface
    public void startFunction() {
        Toast.makeText(mContext, "js调用无参02+@@@我是猪", Toast.LENGTH_SHORT).show();
    }

    //js调用有参安卓方法
    @JavascriptInterface
    public void startFunction(String str) {
        Toast.makeText(mContext, "js调用有参02+@@@" + str, Toast.LENGTH_SHORT).show();
    }

}
