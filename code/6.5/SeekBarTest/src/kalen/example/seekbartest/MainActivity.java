package kalen.example.seekbartest;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.app.Activity;

public class MainActivity extends Activity {

	TextView textView;
	SeekBar seekBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		textView = (TextView) findViewById(R.id.seekbar_value);
		seekBar = (SeekBar) findViewById(R.id.seekbar);
		
		//设置textView显示根据seekBar的位置
		textView.setText(seekBar.getProgress() + "");
		
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				//设置textView显示根据seekBar的位置
				textView.setText(arg1 + "");
			}
		});
		
	}

}
