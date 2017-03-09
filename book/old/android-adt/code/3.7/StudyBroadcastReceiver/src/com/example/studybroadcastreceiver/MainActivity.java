package com.example.studybroadcastreceiver;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class MainActivity extends Activity {
	
	//接受短信的事件
	private static final String SMS_RECIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
	
	SMSReceiver receiver;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //添加一个IntentFilter,用来过滤SMS_RECIVED_ACTION这个事件。
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SMS_RECIVED_ACTION);
        
        receiver = new SMSReceiver();
        //注册广播
        registerReceiver(receiver, intentFilter);
    }
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	unregisterReceiver(receiver);//解除注册
    }
    
    public class SMSReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals(SMS_RECIVED_ACTION)) {
				System.out.println("收到了短信");
			}
		}
    }
}
