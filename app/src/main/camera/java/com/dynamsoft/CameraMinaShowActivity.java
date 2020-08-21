package com.dynamsoft;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.ImageView;

import com.dj.camera.kotlin.MyGLSurfaceView;
import com.dj.collection.BaseActivity;
import com.dj.collection.R;
import com.dj.logutil.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.cuihp.serverlibrary.server.MinaServer;
import top.cuihp.serverlibrary.server.ServerConfig;

public class CameraMinaShowActivity extends BaseActivity{
    private static final String TAG = CameraMinaShowActivity.class.getName();
    
    @BindView(R.id.surface_view)
    SurfaceView surfaceView;

    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.openGlSurface)
    MyGLSurfaceView myGLSurfaceView;

    private MinaServer minaServer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //保持屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

        initServer();
    }

    private void initServer() {
        //服务器初始化
        ServerConfig serverConfig = new ServerConfig.Builder().setPort(8880).build();
        minaServer = new MinaServer(serverConfig);
        minaServer.setServerStateListener(new MinaServer.ServerStateListener() {
            @Override
            public void sessionCreated() {
                LogUtils.e(TAG, "server sessionCreated ");
            }

            @Override
            public void sessionOpened() {
                LogUtils.e(TAG, "server sessionOpened ");
            }

            @Override
            public void sessionClosed() {
                LogUtils.e(TAG, "server sessionClosed ");
            }

            @Override
            public void messageReceived(String message) {
                LogUtils.e(TAG, "server messageReceived ");
            }

            @Override
            public void messageSent(String message) {
                LogUtils.e(TAG, "server messageSent "+message);

            }

            @Override
            public void messageReceived(byte[] data) {
                LogUtils.e(TAG, "server messageReceived length："+data.length);
                myGLSurfaceView.setYuvDataSize(640,480);
                myGLSurfaceView.feedData(data, 2);
            }
        });
    }
}
