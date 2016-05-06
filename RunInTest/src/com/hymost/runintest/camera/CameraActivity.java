package com.hymost.runintest.camera;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.hymost.runintest.LogUtils;
import com.hymost.runintest.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class CameraActivity extends Activity implements SurfaceHolder.Callback {
	
	private static final String LOG_TAG = "CameraTest";
	private static final String DST_FOLDER_NAME = "RunInCamera";  
	
	private Camera mCamera = null;
	private SurfaceView mSurfaceView = null;
	private SurfaceHolder mSurfaceHolder = null;
	
	private int mCurrentCameraId = -1;

	private Timer mTimer = null; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_activity);
		LogUtils.writeLog("\r\n");
		LogUtils.writeLog(this.getString(R.string.log_camera_test_begin));
		LogUtils.log(LOG_TAG, "onCreate()");
		mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LogUtils.log(LOG_TAG, "onResume()");
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(this);
		
		mTimer = new Timer();
		//10分拍照一次 600000
		mTimer.schedule(timerTask, 3000, 20000);
		//startTestCamera();
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
				startTestCamera();
				break;

			default:
				break;
			}
		};
	};
	private int getCameraDisplayOrientation(int cameraId) {
        // TODO Auto-generated method stub
        LogUtils.log(LOG_TAG, "setCameraDisplayOrientation() camearId = " + cameraId);
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int degrees = 0;
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        
        return result;
	}
	
	private void startTestCamera() {
		if (mCurrentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
			mCurrentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
		} else {
			mCurrentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
		}

		if (mCamera == null) {
			try{
			mCamera = Camera.open(mCurrentCameraId);	
			} catch (Exception e){
				stopCamera();
				mCurrentCameraId = (mCurrentCameraId == 0) ? 1 : 0;
				android.util.Log.i("fengw","Exception: " + e.toString());
			}
		}
		if (mCamera != null) {
			try {
				if (mCamera != null) {
					mCamera.setPreviewDisplay(mSurfaceHolder);
					mCamera.setDisplayOrientation(getCameraDisplayOrientation(mCurrentCameraId));
					mCamera.setPreviewCallback(new Camera.PreviewCallback() {
						@Override
						public void onPreviewFrame(byte[] data, Camera camera) {
							// TODO Auto-generated method stub
							doTakePicture(camera);
						}
					});
					Camera.Parameters parameters = mCamera.getParameters(); 
					parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
					mCamera.setParameters(parameters);
					mCamera.startPreview();
					//doTakePicture(mCamera);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
	            if (mCurrentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
	        		LogUtils.writeLog(this.getString(R.string.log_camera_test_result_back_failed) + e.toString());
	        		//LogUtils.writeLog("\r\n");
				}else {
	        		LogUtils.writeLog(this.getString(R.string.log_camera_test_result_front_failed) + e.toString());
	        		//LogUtils.writeLog("\r\n");
				}
				stopCamera();
			}
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		LogUtils.log(LOG_TAG, "onDestroy()");
		LogUtils.writeLog(this.getString(R.string.log_camera_test_end));
		LogUtils.writeLog("\r\n");
		if (mTimer != null) {
			mTimer.cancel();
		}
		stopCamera();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		LogUtils.log(LOG_TAG, "surfaceCreated()");
		//startTestCamera();

		/*mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
		
		try {
			if (mCamera != null) {
				mCamera.setPreviewDisplay(holder);
				mCamera.setDisplayOrientation(90); 
				mCamera.setPreviewCallback(new Camera.PreviewCallback() {
					@Override
					public void onPreviewFrame(byte[] data, Camera camera) {
						// TODO Auto-generated method stub
						doTakePicture(camera);
					}
				});
				Camera.Parameters parameters = mCamera.getParameters(); 
				parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
				mCamera.setParameters(parameters);
				mCamera.startPreview();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			stopCamera();
		}*/
	
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		LogUtils.log(LOG_TAG, "surfaceChanged()");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		LogUtils.log(LOG_TAG, "surfaceDestroyed()");
		stopCamera();
	}
	
	public void doTakePicture(Camera camera) {  
		if (camera != null) {
			camera.takePicture(null, null, mJpegPictureCallback);
		}
    }
	
	PictureCallback mJpegPictureCallback = new PictureCallback() {  
        public void onPictureTaken(byte[] data, Camera camera) {  
            // TODO Auto-generated method stub  
            LogUtils.log(LOG_TAG, "mJpegPictureCallback: onPictureTaken");  
            Bitmap bitmap = null;  
            if(null != data) {   
            	bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);//data是字节数据，将其解析成位图  
            	camera.stopPreview();  
            }  
            //保存图片到sdcard  
            if(null != bitmap) {
            	Bitmap rotaBitmap = getRotateBitmap(bitmap, 90.0f);  
            	if (mCurrentCameraId == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            		rotaBitmap = getRotateBitmap(bitmap, -90.0f);  
            	}
                
                saveBitmap(rotaBitmap);  
            }
            
            if (mCurrentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
            	stopCamera();
            	startTestCamera();
            } else {
            	stopCamera();
            	//camera.startPreview();
            }
            //camera.startPreview();
            //startTestCamera();
        }  
   };  
    
	private String initPath() {
		File parentPath = Environment.getExternalStorageDirectory();
		String storagePath = parentPath.getAbsolutePath() + "/" + DST_FOLDER_NAME;
		File file = new File(storagePath);
		if (!file.exists()) {
			file.mkdir();
		}
		return storagePath;
	}  
  
	public void saveBitmap(Bitmap b) {
		String path = initPath();  
        //long dataTake = System.currentTimeMillis();  
        String jpegName = path + "/" + getDate() +".jpg";  
        LogUtils.log(LOG_TAG, "saveBitmap:jpegName = " + jpegName);  
        try {
            FileOutputStream fos = new FileOutputStream(jpegName);  
            BufferedOutputStream bos = new BufferedOutputStream(fos);  
            b.compress(Bitmap.CompressFormat.JPEG, 100, bos);  
            bos.flush();  
            bos.close();  
            LogUtils.log(LOG_TAG, "saveBitmap: success");  
            if (mCurrentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
        		LogUtils.writeLog(this.getString(R.string.log_camera_test_result_back_success) + jpegName);
        		//LogUtils.writeLog("\r\n");
			}else {
        		LogUtils.writeLog(this.getString(R.string.log_camera_test_result_front_success) + jpegName);
        		//LogUtils.writeLog("\r\n");
			}
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
        	LogUtils.log(LOG_TAG, "saveBitmap: fail");  
            if (mCurrentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
        		LogUtils.writeLog(this.getString(R.string.log_camera_test_result_back_failed) + jpegName);
        		//LogUtils.writeLog("\r\n");
			}else {
        		LogUtils.writeLog(this.getString(R.string.log_camera_test_result_front_failed) + jpegName);
        		//LogUtils.writeLog("\r\n");
			}
        }  
   }  
    
   public Bitmap getRotateBitmap(Bitmap b, float rotateDegree){  
        Matrix matrix = new Matrix();  
        matrix.postRotate((float)rotateDegree);
        matrix.postScale(-1, 1); // 镜像水平翻转  
        Bitmap rotaBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, false);  
        return rotaBitmap;  
   }  
    
   public String getDate(){  
        Calendar ca = Calendar.getInstance();     
        int year = ca.get(Calendar.YEAR);           
        int month = ca.get(Calendar.MONTH);            
        int day = ca.get(Calendar.DATE);           
        int minute = ca.get(Calendar.MINUTE);         
        int hour = ca.get(Calendar.HOUR);           
        int second = ca.get(Calendar.SECOND);            
       
        String date = "" + year + (month + 1)+ day + hour + minute + second;  
        LogUtils.log(LOG_TAG, "date:" + date);  
          
        return date;           
   }  
	
   private void stopCamera() {
		Log.d(LOG_TAG, "stopCamera()");
		if (mCamera != null) {
			mCamera.setPreviewCallback(null);
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
   }
   
}
