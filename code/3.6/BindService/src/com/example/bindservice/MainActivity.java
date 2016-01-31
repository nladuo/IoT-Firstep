package com.example.bindservice;

import com.example.bindservice.TestService.MyBinder;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;

public class MainActivity extends Activity {
	
	MyBinder myBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, TestService.class);
        
        bindService(intent, new ServiceConnection() {
			
			@Override
			public void onServiceDisconnected(ComponentName name) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				// TODO Auto-generated method stub
				System.out.println("∞Û∂®TestService≥…π¶");
				myBinder = (MyBinder) service;
				
			}
		}, BIND_AUTO_CREATE);
        findViewById(R.id.btn_show).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (myBinder != null) {
					Toast.makeText(MainActivity.this, 
							myBinder.getSecond() + "√Î",
							Toast.LENGTH_SHORT).show();
				}
				
			}
		});
    }
}
