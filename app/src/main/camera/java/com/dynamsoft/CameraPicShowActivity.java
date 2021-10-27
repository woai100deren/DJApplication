package com.dynamsoft;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.view.WindowManager;
import android.widget.ImageView;

import com.dj.camera.view.PicturePlayerView;
import com.dj.collection.BaseActivity;
import com.dj.collection.R;
import com.dj.logutil.LogUtils;
import com.dynamsoft.data.DataListener;
import com.dynamsoft.io.SocketPicNewServer;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CameraPicShowActivity extends BaseActivity implements DataListener {
    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.picturePlayerView)
    PicturePlayerView picturePlayerView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //保持屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_camera_pic);
        ButterKnife.bind(this);


        SocketPicNewServer server = new SocketPicNewServer();
        server.setOnDataListener(this);
        server.start();

        try {
            CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            for (String cameraId : cameraManager.getCameraIdList()) {
                LogUtils.e("相机id = "+cameraId);
//                CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraId);
//                characteristics.get(CameraCharacteristics.)
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onDirty(byte[] data) {
    }

    @Override
    public void onDirty(final Bitmap bitmap) {
        picturePlayerView.drawBitmap(bitmap);
    }
}
