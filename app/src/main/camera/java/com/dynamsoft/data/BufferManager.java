package com.dynamsoft.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.dj.logutil.LogUtils;
import com.dynamsoft.ui.Utils;

import java.util.LinkedList;

public class BufferManager extends Thread {
    private ImageBuffer[] mBufferQueue;
    private int mFillCount = 0;
    private final int mFrameLength;
    private int mRemained = 0;
    private static final int MAX_BUFFER_COUNT = 2;
    private int mWidth, mHeight;
    private LinkedList<byte[]> mYUVQueue = new LinkedList<byte[]>();
    private DataListener mListener;
    
    public BufferManager(int frameLength, int width, int height) {
        // TODO Auto-generated constructor stub
    	mWidth = width;
    	mHeight = height;
        mFrameLength = frameLength;
        mBufferQueue = new ImageBuffer[MAX_BUFFER_COUNT];
        for (int i = 0; i < MAX_BUFFER_COUNT; ++i) {
            mBufferQueue[i] = new ImageBuffer(mFrameLength, width, height);
        }
    }
    
	public void fillBuffer(byte[] data, int len) {
		mFillCount = mFillCount % MAX_BUFFER_COUNT;
		if (mRemained != 0) {
			if (mRemained < len) {
				mBufferQueue[mFillCount].fillBuffer(data, 0, mRemained, mYUVQueue);
				++mFillCount;
				if (mFillCount == MAX_BUFFER_COUNT) {
					mFillCount = 0;
				}
				mBufferQueue[mFillCount].fillBuffer(data, mRemained, len - mRemained, mYUVQueue);
				mRemained = mFrameLength - len + mRemained;
			} else if (mRemained == len) {
				mBufferQueue[mFillCount].fillBuffer(data, 0, mRemained, mYUVQueue);
				mRemained = 0;
				++mFillCount;
				if (mFillCount == MAX_BUFFER_COUNT) {
					mFillCount = 0;
				}
			} else {
				mBufferQueue[mFillCount].fillBuffer(data, 0, len, mYUVQueue);
				mRemained = mRemained - len;
			}
		} else {
			mBufferQueue[mFillCount].fillBuffer(data, 0, len, mYUVQueue);

			if (len < mFrameLength) {
				mRemained = mFrameLength - len;
			} else {
				++mFillCount;
				if (mFillCount == MAX_BUFFER_COUNT) {
					mFillCount = 0;
				}
			}
		}
	}
    
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
    
    @Override
    public void run() {
    	// TODO Auto-generated method stub
    	super.run();
    	
    	while (!Thread.currentThread().isInterrupted()) {
    		byte[] data;
			synchronized (mYUVQueue) {
    			data = mYUVQueue.poll();
    			if (data != null) {
//					int[] rgbArray = Utils.convertYUVtoRGB(data, mWidth, mHeight);
//					mListener.onDirty(Bitmap.createBitmap(rgbArray,  mWidth, mHeight,Bitmap.Config.RGB_565));
//                    mListener.onDirty(data);

LogUtils.e("1111111111111","显示："+data.length);
					mListener.onDirty(BitmapFactory.decodeByteArray(data, 0, data.length));
    			}
    			
    		}
    	}
    }
}
