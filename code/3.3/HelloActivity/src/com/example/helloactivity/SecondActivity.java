package com.example.helloactivity;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends Activity{
	TextView tv;
	Button btn;
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		tv = (TextView) findViewById(R.id.tv_second);
		tv.setText("第二个activity");
		btn = (Button) findViewById(R.id.btn_second);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tv.setText("点击了Button");
				Toast.makeText(SecondActivity.this, "点击了Button", 
						Toast.LENGTH_LONG).show();
			}
		});
	};
}
