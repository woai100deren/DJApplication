package com.dynamsoft.data;


import android.graphics.Bitmap;

public interface DataListener {
	void onDirty(byte[] data);
	void onDirty(Bitmap bitmap);
}
