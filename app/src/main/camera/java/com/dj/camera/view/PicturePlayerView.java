package com.dj.camera.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.TextureView;


public class PicturePlayerView extends TextureView implements TextureView.SurfaceTextureListener {

    private Paint mPaint;//画笔
    private Rect mSrcRect;
    private Rect mDstRect;

    private int mPlayFrame;//当前播放到那一帧，总帧数相关

    private String[] mPaths;//图片绝对地址集合
    private int mFrameCount;//总帧数
    private long mDelayTime;//播放帧间隔


    public PicturePlayerView(Context context) {
        this(context, null);
    }

    public PicturePlayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PicturePlayerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setOpaque(false);//设置背景透明，记住这里是[是否不透明]

        setSurfaceTextureListener(this);//设置监听

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);//创建画笔
        mSrcRect = new Rect();
        mDstRect = new Rect();
    }

    public void drawBitmap(Bitmap bitmap) {
        Canvas canvas = lockCanvas();//锁定画布
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);// 清空画布
        mSrcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());//这里我将2个rect抽离出去，防止重复创建
        mDstRect.set(0, 0, getWidth(), bitmap.getHeight() * getWidth() / bitmap.getWidth());
        canvas.drawBitmap(bitmap, mSrcRect, mDstRect, mPaint);//将bitmap画到画布上
        unlockCanvasAndPost(canvas);//解锁画布同时提交
    }

    private static void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        //当TextureView初始化时调用，事实上当你的程序退到后台它会被销毁，你再次打开程序的时候它会被重新初始化
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        //当TextureView的大小改变时调用
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        //当TextureView被销毁时调用
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        //当TextureView更新时调用，也就是当我们调用unlockCanvasAndPost方法时
    }
}
