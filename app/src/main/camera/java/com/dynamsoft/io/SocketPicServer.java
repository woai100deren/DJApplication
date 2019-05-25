package com.dynamsoft.io;

import com.dynamsoft.data.BufferManager;
import com.dynamsoft.data.BufferPicManager;
import com.dynamsoft.data.DataListener;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketPicServer extends Thread {
	private ServerSocket mServer;
	private DataListener mDataListener;


	private BufferManager mBufferManager;

	public SocketPicServer() {
	    
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		int len = 0;
		BufferedInputStream inputStream = null;
		Socket socket = null;
		ByteArrayOutputStream byteArray = null;
		byte [] buffer = new byte[1024];
		byte data[];


		byte[] imageBuff  = new byte[15368];

		try {
			//方案1
//			mServer = new ServerSocket(1234);
//			while (!Thread.currentThread().isInterrupted()) {
//				if (byteArray != null) {
//					byteArray.reset();
//				}else{
//					byteArray = new ByteArrayOutputStream();
//				}
//				socket = mServer.accept();
//				inputStream = new BufferedInputStream(socket.getInputStream());
//				LogUtils.e("11111111111111","1");
//				while( (len=inputStream.read(buffer)) != -1){
//					LogUtils.e("11111111111111","2");
//					byteArray.write(buffer, 0, len);
//				}
//				LogUtils.e("11111111111111","3");
//				data = byteArray.toByteArray();
//				if(mDataListener!=null){
//					mDataListener.onDirty(BitmapFactory.decodeByteArray(data, 0, data.length));
//				}
//			}



			//方案2
//			//监听1234端口
//			mServer = new ServerSocket(1234);
//			////监听客户端链接，调用accept()方法 accept方法是一个阻塞的方法，会阻塞当前线程
//			socket = mServer.accept();
//			inputStream = new BufferedInputStream(socket.getInputStream());
//			mBufferManager = new BufferManager(15368, 640, 480);
//			mBufferManager.setOnDataListener(mDataListener);
//			while ((len = inputStream.read(imageBuff,0,imageBuff.length)) != -1) {
//				LogUtils.e("1111111111111","111111111111111111");
//				mBufferManager.fillBuffer(imageBuff, len);
//			}


//			//方案3
////			mServer = new ServerSocket(1234);
////			InputStream ins;
////			while (!Thread.currentThread().isInterrupted()) {
////				socket = mServer.accept();
////				ins = socket.getInputStream();
////				if (byteArray != null) {
////					byteArray.reset();
////				}else{
////					byteArray = new ByteArrayOutputStream();
////				}
////				while( (len=ins.read(buffer)) != -1){
////					byteArray.write(buffer, 0, len);
////				}
////				ins.close();
////				data = byteArray.toByteArray();
////				if(mDataListener!=null){
////					mDataListener.onDirty(BitmapFactory.decodeByteArray(data, 0, data.length));
////				}
////				if(!socket.isClosed()){
////					socket.close();
////				}
////			}


			//方案4
			mServer = new ServerSocket(1234);
			InputStream ins;
			BufferPicManager bufferPicManager = new BufferPicManager();
			bufferPicManager.setOnDataListener(mDataListener);
			while (!Thread.currentThread().isInterrupted()) {
				socket = mServer.accept();
				ins = socket.getInputStream();
				if (byteArray != null) {
					byteArray.reset();
				}else{
					byteArray = new ByteArrayOutputStream();
				}
				while( (len=ins.read(buffer)) != -1){
					byteArray.write(buffer, 0, len);
				}
				ins.close();
				bufferPicManager.fillBuffer(byteArray.toByteArray());
				if(!socket.isClosed()){
					socket.close();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				
				if (inputStream != null) {
					inputStream.close();
				}

				if (socket != null) {
					socket.close();
				}
				
				if (byteArray != null) {
					byteArray.close();
				}
				
			} catch (IOException e) {

			}

		}

	}

	public void setOnDataListener(DataListener listener) {
		mDataListener = listener;
	}
}
