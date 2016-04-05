package com.demo.androidontheway.bt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.wafejlu.uiproductionfactory.R;

import android.app.Activity;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class BTOpenCameraActivity extends Activity implements OnClickListener, android.widget.CompoundButton.OnCheckedChangeListener {

	private Button btnChooseDevice;
	private TextView tvConnectStatus;
	private Button btnOpenCamera;
	private Button btnDisBT;
	private BluetoothSocket btSocket = null;
	private BufferedInputStream bis = null;
	private BufferedOutputStream bos = null;
	private ToggleButton tbTabClientServer;
	private BluetoothServerSocket btServerSocket = null; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bt_open_camera_activity);

		initView();

	}

	private void initView() {
		btnChooseDevice = (Button) findViewById(R.id.btn_select_device);
		btnChooseDevice.setOnClickListener(this);
		tvConnectStatus = (TextView) findViewById(R.id.tv_bt_status);
		btnOpenCamera = (Button) findViewById(R.id.btn_open_camera);
		btnOpenCamera.setOnClickListener(this);
		btnDisBT = (Button) findViewById(R.id.btn_dis_bt);
		btnDisBT.setOnClickListener(this);
		tbTabClientServer = (ToggleButton) findViewById(R.id.tb_tab_client_server);
		tbTabClientServer.setOnCheckedChangeListener(this);
		tbTabClientServer.setChecked(true);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_select_device:
			Intent intent = new Intent(this, BTDeviceActivity.class);
			startActivityForResult(intent, 100);
			break;
		case R.id.btn_open_camera:
			sendMessage();
			break;
		case R.id.btn_dis_bt:
			disBT();
			break;

		default:
			break;
		}
	}

	private void disBT(){
		try{  
            if(bis!=null)  
                bis.close();  
            if(bos!=null)  
                bos.close();  
            if(btSocket!=null)  
                btSocket.close();  
        }catch(IOException e){  
            e.printStackTrace();  
        }  
		
        BluetoothMsg.isOpen = false;    
        BluetoothMsg.serviceOrCilent=BluetoothMsg.ServerOrCilent.NONE;    
        Message msg = new Message();    
        msg.obj = "已断开连接!";    
        msg.what = 1;    
        detectedHandler.sendMessage(msg); 
        Toast.makeText(getApplicationContext(), "已断开连接！", Toast.LENGTH_SHORT).show();
	}
	
	private void sendMessage(){
		String text ="333";  
        if(null==btSocket||bos==null)  {
            Message msg = new Message();    
            msg.obj = "send fail!! Connection Exception";    
            msg.what = 1;    
            detectedHandler.sendMessage(msg); 
            return;
        } 
        try {  
            bos.write(text.getBytes());  
            bos.flush();  
            Message msg1 = new Message();    
            msg1.obj = text;    
            msg1.what = 1;    
            detectedHandler.sendMessage(msg1); 
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (100 == requestCode) {
			Toast.makeText(this, "result == 100", Toast.LENGTH_LONG).show();
			if (tbTabClientServer.isChecked()) {
				startBTClient();
			}else {
				startBTServer();
			}
			
		}
	}

	public void startBTServer(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {  
                    btServerSocket = BTManage
							.getInstance()
							.getBtAdapter().listenUsingRfcommWithServiceRecord("btspp",  
                    UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));  
                      
                    Message msg = new Message();    
                    msg.obj = "请稍候，正在等待客户端的连接...";    
                    msg.what = 0;    
                    detectedHandler.sendMessage(msg);    
                      
                    btSocket=btServerSocket.accept();  
                    Message msg2 = new Message();    
                    String info = "客户端已经连接上！可以发送信息。";    
                    msg2.obj = info;    
                    msg.what = 0;    
                    detectedHandler.sendMessage(msg2);    
                       
                    receiverMessageTask();  
                } catch(EOFException e){  
                    Message msg = new Message();    
                    msg.obj = "client has close!";    
                    msg.what = 1;    
                    detectedHandler.sendMessage(msg);  
                }catch (IOException e) {  
                    e.printStackTrace();  
                    Message msg = new Message();    
                    msg.obj = "receiver message error! please make client try again connect!";    
                    msg.what = 1;    
                    detectedHandler.sendMessage(msg);  
                }  
			}
		}).start(); 
	}
	
	public void startBTClient() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					btSocket = BTManage
							.getInstance()
							.getBtAdapter()
							.getRemoteDevice(BluetoothMsg.BlueToothAddress)
							.createRfcommSocketToServiceRecord(
									UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));

					Message msg = new Message();
					msg.obj = "请稍候，正在连接服务器:" + BluetoothMsg.BlueToothAddress;
					msg.what = 0;
					detectedHandler.sendMessage(msg);

					btSocket.connect();
					Message msg2 = new Message();
					String info = "已经连接上服务端！可以发送信息。";
					msg2.obj = info;
					msg.what = 0;
					detectedHandler.sendMessage(msg2);

					receiverMessageTask();
				} catch (EOFException e) {
					Message msg = new Message();
					msg.obj = "client has close!";
					msg.what = 1;
					detectedHandler.sendMessage(msg);
				} catch (IOException e) {
					e.printStackTrace();
					Message msg = new Message();
					msg.obj = "receiver message error! please make client try again connect!";
					msg.what = 1;
					detectedHandler.sendMessage(msg);
				}
			}
		}).start();
	}

	private void receiverMessageTask() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				byte[] buffer = new byte[2048];
				int totalRead;
				try {
					bis = new BufferedInputStream(btSocket.getInputStream());
					bos = new BufferedOutputStream(btSocket.getOutputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					// ByteArrayOutputStream arrayOutput=null;
					while ((totalRead = bis.read(buffer)) > 0) {
						// arrayOutput=new ByteArrayOutputStream();
						String txt = new String(buffer, 0, totalRead, "UTF-8");
						Message msg = new Message();
						msg.obj = "Receiver: " + txt;
						msg.what = 1;
						detectedHandler.sendMessage(msg);
						if (333 == Integer.parseInt(txt)) {
							Message msgOpen = new Message();
							msgOpen.obj = "Open Camera";
							msgOpen.what = 3;
							detectedHandler.sendMessage(msgOpen);
						}
					}
				} catch (EOFException e) {
					Message msg = new Message();
					msg.obj = "server has close!";
					msg.what = 1;
					detectedHandler.sendMessage(msg);
				} catch (IOException e) {
					e.printStackTrace();
					Message msg = new Message();
					msg.obj = "receiver message error! make sure server is ok,and try again connect!";
					msg.what = 1;
					detectedHandler.sendMessage(msg);
				}
			}
		}).start();
	}

	private Handler detectedHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				tvConnectStatus.setText(msg.obj.toString());
				break;
			case 1:
				tvConnectStatus.setText(msg.obj.toString());
				Toast.makeText(BTOpenCameraActivity.this, msg.obj.toString(),
						Toast.LENGTH_LONG).show();
				break;
			case 3:
				Intent intent = new Intent();
				intent.setAction("android.media.action.IMAGE_CAPTURE");
				intent.addCategory("android.intent.category.DEFAULT");
				startActivity(intent);

			default:
				break;
			}
		};
	};

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.tb_tab_client_server:
			if (arg1) {//Client
				btnChooseDevice.setEnabled(true);
				BluetoothMsg.serviceOrCilent = BluetoothMsg.ServerOrCilent.CILENT;  
				tvConnectStatus.setText("Choose Device first");
			}else {//Server
				btnChooseDevice.setEnabled(false);
				BluetoothMsg.serviceOrCilent = BluetoothMsg.ServerOrCilent.SERVICE;  
				startBTServer();  
	            BluetoothMsg.isOpen = true;  
			}
			break;

		default:
			break;
		}
	}
}
