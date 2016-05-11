package com.demo.androidontheway.camera;

import java.io.File;

import com.demo.androidontheway.R;

import android.app.Activity;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class VideoRecordingActivity extends Activity {
	private Button btn_VideoStart, btn_VideoStop;
	private SurfaceView sv_view;
	private boolean isRecording;
	private MediaRecorder mediaRecorder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_recording);

		btn_VideoStart = (Button) findViewById(R.id.btn_VideoStart);
		btn_VideoStop = (Button) findViewById(R.id.btn_VideoStop);
		sv_view = (SurfaceView) findViewById(R.id.sv_view);

		btn_VideoStop.setEnabled(false);

		btn_VideoStart.setOnClickListener(click);
		btn_VideoStop.setOnClickListener(click);
		
		// 声明Surface不维护自己的缓冲区，针对Android3.0以下设备支持
		//sv_view.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	private OnClickListener click = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_VideoStart:
				start();
				break;
			case R.id.btn_VideoStop:
				stop();
				break;
			default:
				break;
			}
		}
	};

	protected void start() {
		try {
			File file = new File("/sdcard/video.mp4");
			if (file.exists()) {
				// 如果文件存在，删除它，演示代码保证设备上只有一个录音文件
				file.delete();
			}
			
			mediaRecorder = new MediaRecorder();
			mediaRecorder.reset();
			// Set sound record source
			mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			// Set video/image source
			mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
			// Set record media output format:.mp4
			mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
			// Set sound record encoder
			mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
			// Set video record encoder
			mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
			// Set video frame rate: 4 frame/second
			mediaRecorder.setVideoFrameRate(4);
			// Set video output file path
			mediaRecorder.setOutputFile(file.getAbsolutePath());
			// Set capture video/image preview display
			mediaRecorder.setPreviewDisplay(sv_view.getHolder().getSurface());
			
			mediaRecorder.setOnErrorListener(new OnErrorListener() {
				
				@Override
				public void onError(MediaRecorder mr, int what, int extra) {
					// 发生错误，停止录制
					mediaRecorder.stop();
					mediaRecorder.release();
					mediaRecorder = null;
					isRecording=false;
					btn_VideoStart.setEnabled(true);
					btn_VideoStop.setEnabled(false);
					Toast.makeText(VideoRecordingActivity.this, "录制出错", Toast.LENGTH_SHORT).show();
				}
			});
			
			// 准备、开始
			mediaRecorder.prepare();
			mediaRecorder.start();

			btn_VideoStart.setEnabled(false);
			btn_VideoStop.setEnabled(true);
			isRecording = true;
			Toast.makeText(VideoRecordingActivity.this, "开始录像", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void stop() {
		if (isRecording) {
			mediaRecorder.stop();
			mediaRecorder.release();
			mediaRecorder = null;
			isRecording=false;
			btn_VideoStart.setEnabled(true);
			btn_VideoStop.setEnabled(false);
			Toast.makeText(VideoRecordingActivity.this, "停止录像，并保存文件", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onDestroy() {
		if (isRecording) {
			mediaRecorder.stop();
			mediaRecorder.release();
			mediaRecorder = null;
		}
		super.onDestroy();
	}
}
