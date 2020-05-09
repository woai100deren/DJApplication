package com.dj.customclock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MyClockView extends View {
    private Context mContext;
    private Paint mPaint;

    public MyClockView(Context context) {
        super(context);
        this.mContext = context;
        initPaint();
    }

    public MyClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initPaint();
    }

    public MyClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initPaint();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint(){
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画具体内容
        mPaint.setColor(Color.BLACK);
        //圆形边框
        mPaint.setStrokeWidth(2);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 3, mPaint);
        //画圆心点
        canvas.drawPoint(getWidth() / 2,getHeight() / 2,mPaint);
        //画刻度
        //刻度有60个，360°的话，一个刻度6°。一个刻度实际上就是一条直线
        mPaint.setStrokeWidth(1);
        //把坐标原点移到圆心
        canvas.translate(getWidth()/2,getHeight()/2);
        for(int i=0;i<60;i++){
            if (i % 5 == 0) {//长刻度
                canvas.drawLine(getWidth() / 3 - 25, 0,getWidth() / 3, 0, mPaint);
            } else  {//短刻度*/
                canvas.drawLine(getWidth() / 3 - 14, 0,getWidth() / 3, 0, mPaint);
            }
            //旋转坐标系
            canvas.rotate(6);
        }
        //画数字
        mPaint.setTextSize(25);
        mPaint.setStyle(Paint.Style.FILL);
        Rect textBound = new Rect();//创建一个矩形
        for (int i = 0; i <12; i++) {
            if (i == 0) {
                drawNum(canvas, i * 30, 12 + "", mPaint);
            } else {
                drawNum(canvas, i * 30, i + "", mPaint);
            }
        }




        //时针
        canvas.save();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(7);
        canvas.rotate(mHourDegree);
        canvas.drawLine(0, 0, 0,
                -90, mPaint);
        canvas.restore();

        //分针
        canvas.save();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        canvas.rotate(mMinDegree);
        canvas.drawLine(0, 0, 0,
                -130, mPaint);
        canvas.restore();

        //秒针
        canvas.save();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        canvas.rotate(mSecondDegree);
        //其实坐标点（0,0）终点坐标（0，-190），这里的190为秒针长度
        canvas.drawLine(0, 0, 0,
                -190, mPaint);
        canvas.restore();
    }

    private void drawNum(Canvas canvas, int degree, String text, Paint paint) {
        Rect textBound = new Rect();
        paint.getTextBounds(text, 0, text.length(), textBound);
        canvas.rotate(degree);
        canvas.translate(0, 50 - getWidth() / 3);//这里的50是坐标中心距离时钟最外边框的距离，当然你可以根据需要适当调节
        canvas.rotate(-degree);
        canvas.drawText(text, -textBound.width() / 2,
                textBound.height() / 2, paint);
        canvas.rotate(degree);
        canvas.translate(0, getWidth() / 3 - 50);
        canvas.rotate(-degree);
    }





    private float mSecondDegree;//秒针的度数
    private float mMinDegree;//分针的度数
    private float mHourDegree;//时针的度数
    private boolean mIsNight;
    private Timer mTimer = new Timer();
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {//具体的定时任务逻辑
            if (mSecondDegree == 360) {
                mSecondDegree = 0;
            }
            if (mMinDegree == 360) {
                mMinDegree = 0;
            }
            if (mHourDegree == 360) {
                mHourDegree = 0;
            }
            mSecondDegree = mSecondDegree + 6;//秒针
            mMinDegree = mMinDegree + 0.1f;//分针
            mHourDegree = mHourDegree + 1.0f/240;//时针
            postInvalidate();
        }
    };
    /**
     *开启定时器
     */
    public void start() {
        mTimer.schedule(task,0,1000);
    }

    /**
     * 设置时间
     * @param hour
     * @param min
     * @param second
     */
    public void setTime(int hour, int min, int second) {
        if (hour >= 24 || hour < 0 || min >= 60 || min < 0 || second >= 60 || second < 0) {
            Toast.makeText(getContext(), "时间不合法",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (hour >= 12) {//这里我们采用24小时制
            mIsNight = true;//添加一个变量，用于记录是否为下午。
            mHourDegree = (hour + min * 1.0f/60f + second * 1.0f/3600f - 12)*30f;
        } else {
            mIsNight = false;
            mHourDegree = (hour + min * 1.0f/60f + second * 1.0f/3600f )*30f;
        }
        mMinDegree = (min + second * 1.0f/60f) *6f;
        mSecondDegree = second * 6f;
        invalidate();
    }
}
