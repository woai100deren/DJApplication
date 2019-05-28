package com.dynamsoft.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.dj.logutil.LogUtils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.LinkedList;

public class BufferPic5Manager extends Thread {
	private static final int MAX_BUFFER_COUNT = 20;
	private LinkedList<Bitmap> mBitmapQueue = new LinkedList<>();
	private LinkedList<byte[]> mYUVdata = new LinkedList<>();
	private DataListener mListener;
	private int headLength = 8;
	private byte[] headArray = new byte[headLength];
	private int imageLength;
	private byte[] imageArray;


	public void fillBuffer(byte[] data) {
		synchronized (mBitmapQueue) {
			try {

				if (data.length == 0 || data.length < headLength) {
					return;
				}

				//截取前8位
				//src：byte源数组
				//srcPos：截取源byte数组起始位置（0位置有效）
				//dest,：byte目的数组（截取后存放的数组）
				//destPos：截取后存放的数组起始位置（0位置有效）
				//length：截取的数据长度
				System.arraycopy(data, 0, headArray, 0, headLength);


				imageLength = byteArrayToInt(headArray);

				LogUtils.e("1111111111111","图片长度："+imageLength);

				if(imageLength == 0){
					return;
				}

				if (mBitmapQueue.size() == MAX_BUFFER_COUNT) {
					mBitmapQueue.poll();
				}
				mBitmapQueue.add(BitmapFactory.decodeByteArray(data, headLength, imageLength));

//				LogUtils.e("1111111111111","结束时间："+System.currentTimeMillis());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

//	public void fillBuffer(byte[] data) {
//		synchronized (mYUVdata) {
//			try {
//
//				if (mYUVdata.size() == MAX_BUFFER_COUNT) {
//					mYUVdata.poll();
//				}
//				mYUVdata.add(data);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}

	public void setOnDataListener(DataListener listener) {
		mListener = listener;
		start();
	}

	public void close() {
		interrupt();
		try {
			join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		super.run();
//
//		while (!Thread.currentThread().isInterrupted()) {
//			Bitmap bitmap;
//			synchronized (mYUVdata) {
//				bitmap = dealBitmap(mYUVdata.poll());
//				if (bitmap != null) {
//					mListener.onDirty(bitmap);
//				}
//
//			}
//		}
//	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		while (!Thread.currentThread().isInterrupted()) {
			Bitmap bitmap;
			synchronized (mBitmapQueue) {
				bitmap = mBitmapQueue.poll();
				if (bitmap != null) {
					mListener.onDirty(bitmap);
				}

			}
		}
	}


	private Bitmap dealBitmap(byte[] data) {
		try {

			if(data == null|| data.length<8){
				return null;
			}

			//截取前8位
			//src：byte源数组
			//srcPos：截取源byte数组起始位置（0位置有效）
			//dest,：byte目的数组（截取后存放的数组）
			//destPos：截取后存放的数组起始位置（0位置有效）
			//length：截取的数据长度
			System.arraycopy(data, 0, headArray, 0, headLength);
			imageLength = byteArrayToInt(headArray);
			LogUtils.e("11111111111111111111111","数据长度："+data.length);
			LogUtils.e("11111111111111111111111","图片长度："+imageLength);

			if(imageLength == 0){
				return null;
			}
			return BitmapFactory.decodeByteArray(data, headLength, imageLength);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 字节数组转换成整数 关键技术：ByteArrayInputStream和DataInputStream
	 *
	 * @param byteArray 需要转换的字节数组
	 * @return
	 */
	private int byteArrayToInt(byte[] byteArray) {
		int n = 0;
		try {
			ByteArrayInputStream byteInput = new ByteArrayInputStream(byteArray);
			DataInputStream dataInput = new DataInputStream(byteInput);
			n = dataInput.readInt();

			byteInput.reset();
			byteInput.close();

			dataInput.reset();
			dataInput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n;
	}
}
