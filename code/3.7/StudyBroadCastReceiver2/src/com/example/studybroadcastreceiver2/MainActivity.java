package com.example.studybroadcastreceiver2;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
	
	public static final String MY_ACTION = "自己定义的Action";
	MyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_send).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction(MY_ACTION);
				//发送MY_ACTION
				sendBroadcast(intent);
			}
		});
        
        IntentFilter filter = new IntentFilter();
        filter.addAction(MY_ACTION);
        
        receiver = new MyReceiver();
        registerReceiver(receiver, filter);
    }
    
    
    public class MyReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals(MY_ACTION)) {
				System.out.println("接收到MY_ACTION");
			}
		}
    	
    }

    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	unregisterReceiver(receiver);
    }
    
}
