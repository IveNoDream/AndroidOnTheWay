package com.hymost.runintest.tone;

import java.util.Timer;
import java.util.TimerTask;

import com.hymost.runintest.LogUtils;
import com.hymost.runintest.R;

import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.WindowManager;
import android.widget.TextView;

public class ToneTestActivity extends Activity {

	private TextView toneTestReport;
	private Timer mTimer = null; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); 
		setContentView(R.layout.tone_activity_report);

		LogUtils.writeLog("\r\n");
		LogUtils.writeLog(this.getString(R.string.log_tone_test_result_start));
		
		toneTestReport = (TextView) findViewById(R.id.tone_test_result_text);
		
		mTimer = new Timer();
		//10分钟响一次 
		mTimer.schedule(timerTask, 0, 5000);
	}
	
	TimerTask timerTask = new TimerTask() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message msg = new Message();
			msg.what = 1;
			handler.sendMessage(msg);
		}
	};
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				//响铃-自定义
				//SoundPool soundPool;
				//soundPool= new SoundPool(10,AudioManager.STREAM_SYSTEM,5);
				//soundPool.load(this,R.raw.collide,1);
				//soundPool.play(1,1, 1, 0, 0, 1);
				//系统铃声
				Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);  
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);  
                r.play(); 
                LogUtils.writeLog(DateFormat.format("yyyy-hh-mm-ss", System.currentTimeMillis()) + ToneTestActivity.this.getString(R.string.log_tone_test_result_success));
				toneTestReport.append("\n" + DateFormat.format("yyyy-hh-mm-ss", System.currentTimeMillis()));
				break;

			default:
				break;
			}
		};
	};
	
	protected void onDestroy() {
		super.onDestroy();

		LogUtils.writeLog("\r\n");
		LogUtils.writeLog(this.getString(R.string.log_tone_test_result_end));
		mTimer.cancel();
	};
}
