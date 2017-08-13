package com.example.shinelon.asdemo.celuemoshi2;
import com.example.shinelon.asdemo.celuemoshi.CashBase;
import com.example.shinelon.asdemo.celuemoshi.CashFanLi;
import com.example.shinelon.asdemo.celuemoshi.CashNormal;
import com.example.shinelon.asdemo.celuemoshi.CashRebate;
/**
 * Created by Shinelon on 2016/6/1.
 */
public class CashContext {
    CashBase cb = null;

    public CashContext(String type) {
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
    }
    public double GetResult(double money){
        return cb.acceptCash(money);

    }
}
