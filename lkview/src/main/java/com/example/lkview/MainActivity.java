package com.example.lkview;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ipc.ICat;
import com.example.lkview.base.AionActivity;

public class MainActivity extends AionActivity {
    public ICat binder;
    private Button button;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("main", "onCreate");
//        initData();
        initView();
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = ICat.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            binder = null;
        }
    };

    @Override
    protected void initView() {
        Log.e("main", "initView");
        button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.example.ipc.action.aidl");
                intent.setPackage("com.example.ipc");
                bindService(intent, conn, BIND_AUTO_CREATE);
//                final Intent intent = new Intent(this,AidlService.class);
//                bindService(intent,conn, Service.BIND_AUTO_CREATE)
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String str = binder.getColor();
                    double aa = binder.getWeight();
                    Toast.makeText(MainActivity.this, "str:" + str + "0000  " + aa, Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void initData() {
        Log.e("main", "initData");
    }
}
