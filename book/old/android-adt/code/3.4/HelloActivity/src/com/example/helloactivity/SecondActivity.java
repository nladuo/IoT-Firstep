package com.example.helloactivity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SecondActivity extends Activity{
	Button btn;
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		btn = (Button) findViewById(R.id.btn_second);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(SecondActivity.this, MainActivity.class);
				intent.putExtra("data", "this is data");
				startActivity(intent);
			}
		});
		System.out.println("SecondActivity--->onCreate");
	};
	
	protected void onStart(){
		super.onStart();
		System.out.println("SecondActivity--->onStart");
	}
    
    protected void onRestart(){
    	super.onRestart();
    	System.out.println("SecondActivity--->onRestart");
    }

    protected void onResume(){
    	super.onResume();
    	System.out.println("SecondActivity--->onResume");
    }

    protected void onPause(){
    	super.onPause();
    	System.out.println("SecondActivity--->onPause");
    }

    protected void onStop(){
    	super.onStop();
    	System.out.println("SecondActivity--->onStop");
    }

    protected void onDestroy(){
    	super.onDestroy();
    	System.out.println("SecondActivity--->onDestroy");
    }
}
