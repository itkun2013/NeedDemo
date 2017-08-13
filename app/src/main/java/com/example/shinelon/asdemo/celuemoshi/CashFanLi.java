package com.example.shinelon.asdemo.celuemoshi;

/**
 * Created by Shinelon on 2016/5/26.
 */
public class CashFanLi extends CashBase {
    private double mCondition = 0.0d;
    private double mReturn = 0.0d;

    /**
     * @param moneyCondition 初始化返利条件
     * @param moneyReturn    返利值
     */
    public CashFanLi(String moneyCondition, String moneyReturn) {
        this.mCondition = Double.parseDouble(moneyCondition);
        this.mReturn = Double.parseDouble(moneyReturn);

    }

    @Override
    public double acceptCash(double money) {
        double result = money;
        if (money >= mCondition) {
            //开始返利
            result = money - Math.floor(money / mCondition) * mReturn;

        }
        return result;
    }
}
