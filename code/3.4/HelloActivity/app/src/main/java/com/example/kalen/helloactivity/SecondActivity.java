package com.example.kalen.helloactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class SecondActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SecondActivity.this, MainActivity.class);
                intent.putExtra("data", "this is data");
                startActivity(intent);
            }
        });
        System.out.println("SecondActivity--->onCreate");
    }

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
