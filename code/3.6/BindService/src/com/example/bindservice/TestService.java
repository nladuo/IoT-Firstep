package com.example.bindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class TestService extends Service{
	
	MyBinder binder = new MyBinder();
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					binder.setSecond(binder.getSecond() + 1);
					try {//Ë¯Ãß1Ãë
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		return binder;
	}
	
	
	public class MyBinder extends Binder{
		private int second = 0;

		public int getSecond() {
			return second;
		}

		public void setSecond(int second) {
			this.second = second;
		}
	}

}
