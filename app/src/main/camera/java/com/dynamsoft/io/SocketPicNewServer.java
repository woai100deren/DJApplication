package com.dynamsoft.io;

import android.graphics.BitmapFactory;

import com.dj.logutil.LogUtils;
import com.dynamsoft.data.BufferPic5Manager;
import com.dynamsoft.data.DataListener;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketPicNewServer extends Thread {
	private ServerSocket mServer;
	private DataListener mDataListener;
	private static final int HEAD_TOTAL_LENGTH = 8;
	private static final int IMAGE_MAX_LENGTH = 50000;
	private static final int IMAGE_BUFFER_LENGTH = 1024;
	/**头协议byte[]*/
	private byte [] headerResultBuffer = new byte[HEAD_TOTAL_LENGTH];
	/**头协议byte[]解析时的临时存储*/
	private byte [] headerBuffer;
	/**头协议中H的下标*/
	private int hIndex = -1;
	/**头协议已经找到了多少位*/
	private int headLength = 0;
	/**每一张图片的数据长度*/
	private int imageLength = 0;
	/**图片已经找到了多少位*/
	private int findImageLength = 0;
	/**一张图byte[]*/
	private byte [] oneImageAllData;
	/**一张图byte[]解析时的临时存储*/
	private byte [] oneImageData;

	public SocketPicNewServer() {
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		BufferedInputStream inputStream = null;
		Socket socket = null;


		try {
			//监听1234端口
			mServer = new ServerSocket(1234);
			////监听客户端链接，调用accept()方法 accept方法是一个阻塞的方法，会阻塞当前线程
			socket = mServer.accept();
			inputStream = new BufferedInputStream(socket.getInputStream());
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();

			while(!Thread.currentThread().isInterrupted()) {
				dealFirstH(inputStream);
				//LogUtils.e("111111111111111","头部byte[]长度："+headLength);
				if(headLength != HEAD_TOTAL_LENGTH) {
					//第一次取出来的数据不够一组8字节头数据，继续取
					dealOneImageHeadData(hIndex, inputStream);
				}

				//如果是H开头，获取图片长度
				imageLength = byteArrayToInt(headerResultBuffer);

				//LogUtils.e("111111111111111","图片长度："+imageLength);

				//判断长度是否合法，暂定最大不能超过IMAGE_MAX_LENGTH
				if(imageLength > 0 && imageLength < IMAGE_MAX_LENGTH){
					//LogUtils.e("111111111111111","图片长度合法");
					oneImageAllData = new byte[imageLength];
					dealOneImageData(imageLength,inputStream);
				}


				if(mDataListener!=null && oneImageAllData != null && oneImageAllData.length > 0){
//				if(mDataListener!=null){
					mDataListener.onDirty(BitmapFactory.decodeByteArray(oneImageAllData, 0, oneImageAllData.length));
				}

				hIndex = -1;
				imageLength = 0;
				headLength = 0;
				findImageLength = 0;
				oneImageAllData = null;
				oneImageData = null;
				headerBuffer = null;
				headerResultBuffer = new byte[HEAD_TOTAL_LENGTH];
				byteArray.reset();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 处理一张图片开始时，找到H
	 * @param inputStream 输入流
	 */
	private void dealFirstH(BufferedInputStream inputStream){
		try {
			//LogUtils.e("开始处理head：dealFirstH");
			headerBuffer = new byte[HEAD_TOTAL_LENGTH];
			int dataLength = inputStream.read(headerBuffer);
			//LogUtils.e("dealFirstH获取到的长度："+dataLength);
			for (int i = 0; i < dataLength; i++) {
				//判断是否包含H
				if ('H' == headerBuffer[i]) {
					hIndex = i;
				}
			}

			if(hIndex < 0){
				//LogUtils.e("dealFirstH不包含H");
				dealFirstH(inputStream);
			}else{
				headLength = dataLength - hIndex;
				//LogUtils.e("dealFirstH包含H，H在第几位："+hIndex);
				//src：byte源数组
				//srcPos：截取源byte数组起始位置（0位置有效）
				//dest,：byte目的数组（截取后存放的数组）
				//destPos：截取后存放的数组起始位置（0位置有效）
				//length：截取的数据长度
				System.arraycopy(headerBuffer, hIndex, headerResultBuffer, 0, headLength);
			}
		}catch (IOException e){
			e.printStackTrace();
			//LogUtils.e("dealFirstH报错了");
		}
	}

	/**
	 * 递归获取一张图片的完整byte[]数据
	 * @param length 要取的头协议byte[]长度
	 * @param inputStream 输入流
	 */
	private void dealOneImageHeadData(int length,BufferedInputStream inputStream){
		//LogUtils.e("111111111111","继续取head长度："+length);
		boolean hasIncludeH = false;
		try {
			headerBuffer = new byte[length];
			int dataLength = inputStream.read(headerBuffer);
			//LogUtils.e("111111111111","继取出来head长度："+dataLength);
			for (int i = 0; i < dataLength; i++) {
				//LogUtils.e("111111111111","headerBuffer【"+i+"】："+headerBuffer[i]);
				//判断是否包含H
				if ('H' == headerBuffer[i]) {
					hIndex = i;
					hasIncludeH = true;
				}
			}

			if(hasIncludeH){
				//LogUtils.e("111111111111","包含H");
				//包含H
				headerResultBuffer = new byte[HEAD_TOTAL_LENGTH];
				headLength = dataLength - hIndex;
				dealOneImageHeadData(HEAD_TOTAL_LENGTH - headLength,inputStream);
			}else {
				//LogUtils.e("111111111111","不包含H");
				//不包含H
				//src：byte源数组
				//srcPos：截取源byte数组起始位置（0位置有效）
				//dest,：byte目的数组（截取后存放的数组）
				//destPos：截取后存放的数组起始位置（0位置有效）
				//length：截取的数据长度
				System.arraycopy(headerBuffer, 0, headerResultBuffer, headLength, dataLength);
				headLength = headLength + dataLength;
				//LogUtils.e("111111111111","已经取到的总head长度："+headLength);
				if (headLength != HEAD_TOTAL_LENGTH) {
					dealOneImageHeadData(HEAD_TOTAL_LENGTH - headLength, inputStream);
				}
			}
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 递归获取一张图片的完整byte[]数据
	 * @param inputStream 输入流
	 */
	private void dealOneImageData(int length,BufferedInputStream inputStream){
		try {
			if (length > IMAGE_BUFFER_LENGTH) {
				oneImageData = new byte[IMAGE_BUFFER_LENGTH];
			} else {
				oneImageData = new byte[length];
			}

			int dataLength = inputStream.read(oneImageData);

			//LogUtils.e("111111111111111","取出来的imagedata长度是："+dataLength);

			//src：byte源数组
			//srcPos：截取源byte数组起始位置（0位置有效）
			//dest,：byte目的数组（截取后存放的数组）
			//destPos：截取后存放的数组起始位置（0位置有效）
			//length：截取的数据长度
			System.arraycopy(oneImageData, 0, oneImageAllData, findImageLength, dataLength);
			findImageLength = findImageLength + dataLength;
			//LogUtils.e("111111111111111","当前已经拿到的findImageLength长度："+findImageLength);
			if(findImageLength != imageLength) {
				dealOneImageData(imageLength - findImageLength, inputStream);
			}

			//LogUtils.e("111111111111111","已经全部取完一张图片："+findImageLength);
		}catch (IOException e){
			e.printStackTrace();
		}
	}


	public void setOnDataListener(DataListener listener) {
		mDataListener = listener;
	}

	/**
	 * 字节数组转换成整数 关键技术：ByteArrayInputStream和DataInputStream
	 *
	 * @param byteArray 需要转换的字节数组
	 * @return
	 */
	private int byteArrayToInt(byte[] byteArray) {
		int length = 0;
		try {
			//要把第一位变为0，否则用H去计算，是错误的
			byteArray[0] = 48;
			length = Integer.parseInt(new String(byteArray));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return length;
	}
}
