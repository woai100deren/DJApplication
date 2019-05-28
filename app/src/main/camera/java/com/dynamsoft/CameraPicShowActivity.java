package com.dynamsoft;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import com.dj.collection.BaseActivity;
import com.dj.collection.R;
import com.dj.logutil.LogUtils;
import com.dynamsoft.data.DataListener;
import com.dynamsoft.io.SocketPicServer;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CameraPicShowActivity extends BaseActivity implements DataListener {
    @BindView(R.id.imageView)
    ImageView imageView;

    private Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //保持屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_camera_pic);
        ButterKnife.bind(this);


        SocketPicServer server = new SocketPicServer();
        server.setOnDataListener(this);
        server.start();
    }


    @Override
    public void onDirty(byte[] data) {
    }

    @Override
    public void onDirty(final Bitmap bitmap) {
        this.bitmap = bitmap;
        handler.sendEmptyMessage(1);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            imageView.setImageBitmap(bitmap);
        }
    };
}
