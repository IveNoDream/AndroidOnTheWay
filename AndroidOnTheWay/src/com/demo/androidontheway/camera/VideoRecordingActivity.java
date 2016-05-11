package com.demo.androidontheway.camera;

import java.io.File;
import java.util.List;
import java.util.Locale;

import com.demo.androidontheway.R;

import android.app.Activity;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class VideoRecordingActivity extends Activity {
	private final static String TAG = "mijie";
	private final static int UPDATE_RECORDING_TIME = 0;
	private final static int START_RECORDING = 1;
	private final static int STOP_RECORDING = 2;
	private final static int INTERRUPT_RECORDING = 3;
	private Button btn_VideoStart, btn_VideoStop;
	private TextView tv_time;
	private SurfaceView sv_view;
	private boolean isRecording;
	private MediaRecorder mediaRecorder;
	private long mTotalRecordingDuration = 0;
	private long mRecordingStartTime = 0;
	private String mCurrentFilePath = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_recording);

		btn_VideoStart = (Button) findViewById(R.id.btn_VideoStart);
		btn_VideoStop = (Button) findViewById(R.id.btn_VideoStop);
		sv_view = (SurfaceView) findViewById(R.id.sv_view);
		tv_time = (TextView) findViewById(R.id.tv_time);

		btn_VideoStop.setEnabled(false);

		btn_VideoStart.setOnClickListener(click);
		btn_VideoStop.setOnClickListener(click);

		//Log.i(TAG, "path = " + Environment.getExternalStorageDirectory());

		// Face below Android3.0 devices
		// sv_view.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	private OnClickListener click = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_VideoStart:
				start();
				break;
			case R.id.btn_VideoStop:
				stop(false);
				break;
			default:
				break;
			}
		}
	};

	private String formatTime(long millis, boolean showMillis) {
		final int totalSeconds = (int) millis / 1000;
		final int millionSeconds = (int) (millis % 1000) / 10;
		final int seconds = totalSeconds % 60;
		final int minutes = (totalSeconds / 60) % 60;
		final int hours = totalSeconds / 3600;
		String text = null;
		if (showMillis) {
			if (hours > 0) {
				text = String.format(Locale.ENGLISH, "%d:%02d:%02d.%02d",
						hours, minutes, seconds, millionSeconds);
			} else {
				text = String.format(Locale.ENGLISH, "%02d:%02d.%02d", minutes,
						seconds, millionSeconds);
			}
		} else {
			if (hours > 0) {
				text = String.format(Locale.ENGLISH, "%d:%02d:%02d", hours,
						minutes, seconds);
			} else {
				text = String.format(Locale.ENGLISH, "%02d:%02d", minutes,
						seconds);
			}
		}
		//Log.d(TAG, "formatTime(" + millis + ", " + showMillis + ") return "
		//		+ text);
		return text;
	}

	private void updateRecordingTime() {
		if (!isRecording) {
			return;
		}
		long now = SystemClock.uptimeMillis();
		mTotalRecordingDuration = now - mRecordingStartTime;
		long delayTime = 1000;
		mVideoRecordingHandler.sendEmptyMessageDelayed(UPDATE_RECORDING_TIME,
				delayTime);
		tv_time.setText(formatTime(mTotalRecordingDuration, false));
		if (mTotalRecordingDuration >= 10000 ) {
			mVideoRecordingHandler.sendEmptyMessage(STOP_RECORDING);
		}
	}

	Handler mVideoRecordingHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATE_RECORDING_TIME:
				updateRecordingTime();
				break;
			case START_RECORDING:
				start();
				break;
			case STOP_RECORDING:
				stop(true);
				break;
			case INTERRUPT_RECORDING:

				break;

			default:
				break;
			}
		};
	};

	public static long getSDTotalSize(String SDCardPath) {
		StatFs stat = new StatFs(SDCardPath);
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return blockSize * totalBlocks;
	}

	public static long getSDAvailableSize(String SDCardPath) {
		// StatFs stat = new StatFs("/storage/sdcard1");
		StatFs stat = new StatFs(SDCardPath);
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return blockSize * availableBlocks;
	}

	public boolean deleteFiles() {
		long totalSpace = getSDTotalSize(Environment
				.getExternalStorageDirectory().toString());
		// sd avalible space
		long avaSpace = getSDAvailableSize(Environment
				.getExternalStorageDirectory().toString());
		Log.i(TAG, "SD total: " + totalSpace + "avaSpace: " + avaSpace);
		if (avaSpace > 66609152) {
			Log.i(TAG, "can record");
		} else {
			Log.i(TAG, "can not record");
			// delete some files
			DriveVideoDbHelper helper = new DriveVideoDbHelper(
					VideoRecordingActivity.this);
			if (helper.getNewestVideoId() < 0) {
				Log.i(TAG, "无视频");
				return false;
			}
			int id = helper.getOldestVideoId();
			String name = helper.getVideNameById(id);
			Log.i(TAG, "name := " + name);
			// if(helper.isVideoExist(name)){
			helper.deleteDriveVideoById(id);
			// }
			deleteFiles(name);
			deleteFiles();
		}
		return true;

	}

	public void deleteFiles(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
			Log.i(TAG, "delete file: " + path);
		} else {
			Log.i(TAG, "file not exist path :" + path);
		}
	}

	public String getRecordingPath() {
		String filePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/DINA/";
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdir();
		}
		String fileName = (String) DateFormat.format("yyyy-MM-dd HH:mm:ss",
				System.currentTimeMillis());
		return filePath + fileName + ".mp4";
	}

	protected void start() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			boolean isDeleteSuccess = deleteFiles();
			if (!isDeleteSuccess) {
				Log.i(TAG, "Please Manually delete Some Files");
				return;
			}
		} else {
			Log.i(TAG, "SD not Exists");
			return;
		}
		mRecordingStartTime = SystemClock.uptimeMillis();
		mVideoRecordingHandler.sendEmptyMessage(UPDATE_RECORDING_TIME);
		mCurrentFilePath = getRecordingPath();
		try {
			File file = new File(mCurrentFilePath);
			if (file.exists()) {
				Log.i(TAG, "File Exists,delete");
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
					isRecording = false;
					btn_VideoStart.setEnabled(true);
					btn_VideoStop.setEnabled(false);
					Log.i(TAG, "Error, Stop Video recording");
				}
			});

			mediaRecorder.prepare();
			mediaRecorder.start();

			btn_VideoStart.setEnabled(false);
			btn_VideoStop.setEnabled(true);
			isRecording = true;
			Log.i(TAG, "Start Video recording");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void stop(boolean isCirculate) {
		if (isRecording) {
			mVideoRecordingHandler.removeMessages(UPDATE_RECORDING_TIME);
			mediaRecorder.stop();
			mediaRecorder.release();
			mediaRecorder = null;
			isRecording = false;
			btn_VideoStart.setEnabled(true);
			btn_VideoStop.setEnabled(false);
			saveToVideoDB();
			Log.i(TAG, "Stop Video recording");
			if (isCirculate) {
				mVideoRecordingHandler.sendEmptyMessage(START_RECORDING);
			}
		}
	}

	private void saveToVideoDB(){
		DriveVideoDbHelper helper = new DriveVideoDbHelper(VideoRecordingActivity.this);
		helper.addDriveVideo(new DriveVideo(/*mDriveVideoDbHelper.getNewestVideoId()+*/1, mCurrentFilePath, 0, 1));
		List <DriveVideo> v = helper.getAllDriveVideo();
		for(int i = 0; i < v.size(); i++){
			Log.i(TAG,"saved video: " + "id " + v.get(i).getId() + 
				"name " + v.get(i).getName());
			}
	}
	
	@Override
	protected void onDestroy() {
		if (isRecording) {
			Log.i(TAG, "On destroy, Stop Video recording");
			mediaRecorder.stop();
			mediaRecorder.release();
			mediaRecorder = null;
		}
		super.onDestroy();
	}
}
