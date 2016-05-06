package com.hymost.runintest;

import java.io.File;
import java.io.FileOutputStream;

import android.os.Environment;
import android.util.Log;

public class LogUtils {
	private static final String TAG = "RunIn";
	private static final String LOG_TAG = "LogUtils";
	private static LogUtils mLogUtils;

	public static synchronized LogUtils getInstance() {
		if (mLogUtils == null) {
			mLogUtils = new LogUtils();
		}
		return mLogUtils;
	}

	private LogUtils() {
		
	}
	
	public static void log(String tag, String string) {
		Log.i(TAG, tag + ", " + string);
	}

	public synchronized static void writeLog(String mString) {
		String sdStatus = Environment.getExternalStorageState();
		log(LOG_TAG, "test begin,sdStatus = " + sdStatus);
		log(LOG_TAG, "test begin,Directory = " + Environment.getExternalStorageDirectory());
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
			log(LOG_TAG, "SD card is not avaiable/writeable right now.");
			return;
		}
		
		try {
			String pathName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/runin/";
			String fileName = "RunInProcessLog.txt";
			File path = new File(pathName);
			File file = new File(pathName + fileName);
			if (!path.exists()) {
				log(LOG_TAG, "path Create the path");
				path.mkdir();
			}
			if (!file.exists()) {
				log(LOG_TAG, "file Create the file");
				file.createNewFile();
			}
			FileOutputStream stream = new FileOutputStream(file, true);
			String s = mString + "\r\n";
			byte[] buf = s.getBytes();
			stream.write(buf);
			stream.close();
			log(LOG_TAG, "test end");
		} catch (Exception e) {
			log(LOG_TAG, "Error on writeFileToSD.");
			e.printStackTrace();
		}
	}

}
