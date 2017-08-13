package com.example.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class AidlService extends Service {
    private CatBinder catBinder;

    //此处要继承Stub,实现ICat和IBinder接口
    public class CatBinder extends ICat.Stub {

        @Override
        public String getColor() throws RemoteException {
            return "梁坤";
        }

        @Override
        public double getWeight() throws RemoteException {
            return 999.9;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        catBinder = new CatBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("remote service destroy");
    }

    @Override
    public IBinder onBind(Intent intent) {

        return catBinder;
    }
}
