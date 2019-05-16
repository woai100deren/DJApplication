package com.dynamsoft;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.ImageView;

import com.dj.camera.AndroidHradwareDecode;
import com.dj.collection.BaseActivity;
import com.dj.collection.R;
import com.dj.logutil.LogUtils;
import com.dynamsoft.data.DataListener;
import com.dynamsoft.io.SocketServer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraShowActivity extends BaseActivity implements DataListener {
    @BindView(R.id.surface_view)
    SurfaceView surfaceView;

    @BindView(R.id.imageView)
    ImageView imageView;

    AndroidHradwareDecode mDecode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //保持屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

//        initSurface();

        SocketServer server = new SocketServer();
        server.setOnDataListener(this);
        server.start();
    }


    @Override
    public void onDirty(byte[] data) {
        updateUI(data);
    }

    @Override
    public void onDirty(final Bitmap bitmap) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LogUtils.e("111111111111111111",System.currentTimeMillis());
                imageView.setImageBitmap(bitmap);
            }
        });

    }

    private void updateUI(byte[] data) {
        if(mDecode!=null){
            mDecode.onDecodeData(data);
        }
    }

    /**
     * 初始化预览界面
     */
    void initSurface() {
        SurfaceHolder mHolder = surfaceView.getHolder();
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
//                mDecode = new AndroidHradwareDecode(surfaceHolder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            }
        });
    }
}