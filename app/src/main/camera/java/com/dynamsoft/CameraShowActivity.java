package com.dynamsoft;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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

    AndroidHradwareDecode mDecode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

        initSurface();

        SocketServer server = new SocketServer();
        server.setOnDataListener(this);
        server.start();
    }


    @Override
    public void onDirty(byte[] data) {
        updateUI(data);
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
                mDecode = new AndroidHradwareDecode(surfaceHolder);
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
