package kalen.app.example.netrequest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread t =new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Start Http Get ....");
                String result = HttpUtils.TestGet();
                System.out.println(result);
            }
        });
        t.start();

    }

}
