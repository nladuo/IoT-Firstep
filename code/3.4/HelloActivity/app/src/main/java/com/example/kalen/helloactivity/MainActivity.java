package com.example.kalen.helloactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        System.out.println("In MainActivity--->" + getIntent().getStringExtra("data"));
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
