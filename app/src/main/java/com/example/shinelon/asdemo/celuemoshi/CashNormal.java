package com.example.shinelon.asdemo.celuemoshi;

/**
 * Created by Shinelon on 2016/5/26.
 * 正常收费，原价返回
 */
public class CashNormal extends CashBase {
    @Override
    public double acceptCash(double money) {
        return money;
    }
}
