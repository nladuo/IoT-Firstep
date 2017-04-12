package com.example.kalen.receivebroadcast;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String SMS_RECIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    private static final int SMS_REQ_CODE = 101;

    boolean isRegistered = false;
    SMSReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mayRegisterReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRegistered) {
            unregisterReceiver(receiver); //解除注册
        }
    }

    private void mayRegisterReceiver() {
        if (Build.VERSION.SDK_INT >= 23) {
            int grant = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
            if(grant != PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)){
                    Toast.makeText(this, "需要接受短信权限", Toast.LENGTH_SHORT).show();
                }
                ActivityCompat.requestPermissions(this ,new String[]{Manifest.permission.RECEIVE_SMS}, SMS_REQ_CODE);
            }else{
                doRegisterReceiver();
            }
        } else {
            doRegisterReceiver();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_REQ_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "获取权限成功", Toast.LENGTH_LONG).show();
                doRegisterReceiver();
            } else {
                Toast.makeText(this, "没有权限接受短信", Toast.LENGTH_LONG).show();
            }
        }
    }

    void doRegisterReceiver() {
        isRegistered = true;
        //添加一个IntentFilter,用来过滤SMS_RECIVED_ACTION这个事件。
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SMS_RECIVED_ACTION);

        receiver = new SMSReceiver();
        registerReceiver(receiver, intentFilter);//注册广播
    }

    public class SMSReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(SMS_RECIVED_ACTION)) {
                System.out.println("收到了短信");
            }
        }
    }
}
