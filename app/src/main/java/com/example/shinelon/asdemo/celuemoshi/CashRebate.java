package com.example.shinelon.asdemo.celuemoshi;

/**
 * Created by Shinelon on 2016/5/26.
 */
public class CashRebate extends CashBase {
    private double  mRebate=1d;
    public CashRebate(String moneyRebate){
        this.mRebate=Double.parseDouble(moneyRebate);

    }
    @Override
    public double acceptCash(double money) {
        return money*mRebate;
    }
}
