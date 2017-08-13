package com.example.shinelon.asdemo.celuemoshi;

/**
 * Created by Shinelon on 2016/5/27.
 * 现金收费工厂类
 */
public class CashFactory {
    public static CashBase creatCashAccept(String type) {
        CashBase cb = null;
        switch (type) {
            case "正常收费":
                cb = new CashNormal();
                break;
            case "满300返100":
                cb = new CashFanLi("300", "100");
                break;
            case "打8折":
                cb = new CashRebate("0.8");
                break;

        }
        return cb;
    }
}
