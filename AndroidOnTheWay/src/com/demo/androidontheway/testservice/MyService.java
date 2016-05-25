package com.demo.androidontheway.testservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {
	private static final int MAX_PROGRESS = 100;
	int progress = 0;
	OnProgressListner listner;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return new MsgBinder();
	}

	public void SetOnProgressListner(OnProgressListner listner) {
		this.listner = listner;
	}

	public void startDownLoad() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (progress < MAX_PROGRESS) {
					progress += 5;

					if (listner != null) {
						listner.onProgress(progress);
					}

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		}).start();
	}

	public class MsgBinder extends Binder {
		/**
		 * 
		 * @return object of service
		 */
		public MyService getService() {
			return MyService.this;
		}
	}
}
