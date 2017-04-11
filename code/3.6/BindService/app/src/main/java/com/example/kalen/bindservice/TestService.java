package com.example.kalen.bindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by kalen on 2017/4/11.
 */

public class TestService extends Service{
    MyBinder binder = new MyBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    binder.setSecond(binder.getSecond() + 1);
                    try {//睡眠1秒
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return binder;
    }

    public class MyBinder extends Binder {
        private int second = 0;

        public int getSecond() {
            return second;
        }

        public void setSecond(int second) {
            this.second = second;
        }
    }
}
