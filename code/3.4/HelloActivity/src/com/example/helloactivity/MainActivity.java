package com.example.helloactivity;

import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("MainActivity--->onCreate");
    } 
   
    protected void onStart(){
		super.onStart();
		System.out.println("MainActivity--->onStart");
	}
    
    protected void onRestart(){
    	super.onRestart();
    	System.out.println("MainActivity--->onRestart");
    }

    protected void onResume(){
    	super.onResume();
    	System.out.println("MainActivity--->onResume");
    }

    protected void onPause(){
    	super.onPause();
    	System.out.println("MainActivity--->onPause");
    }

    protected void onStop(){
    	super.onStop();
    	System.out.println("MainActivity--->onStop");
    }

    protected void onDestroy(){
    	super.onDestroy();
    	System.out.println("MainActivity--->onDestroy");
    }
}
