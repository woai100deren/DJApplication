package com.dynamsoft.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.dj.logutil.LogUtils;

import java.util.LinkedList;

public class BufferPicManager extends Thread {
    private static final int MAX_BUFFER_COUNT = 5;
    private LinkedList<Bitmap> mBitmapQueue = new LinkedList<>();
    private DataListener mListener;

    
	public void fillBuffer(byte[] data) {
		if(mBitmapQueue.size() == MAX_BUFFER_COUNT){
			mBitmapQueue.poll();
		}
		mBitmapQueue.add(BitmapFactory.decodeByteArray(data, 0, data.length));
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
    		Bitmap bitmap;
			synchronized (mBitmapQueue) {
    			bitmap = mBitmapQueue.poll();
    			if (bitmap != null) {
					mListener.onDirty(bitmap);
    			}
    			
    		}
    	}
    }
}
