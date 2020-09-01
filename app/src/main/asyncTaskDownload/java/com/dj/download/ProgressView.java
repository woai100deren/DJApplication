package com.dj.download;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 自定义简易圆形进度框
 */
public class ProgressView extends View {
    /**
     * 弧度
     */
    private int mAngleValue = 0;
    /**
     * 圆弧画笔
     */
    private Paint mArcCirclePaint;
    /**
     * 文本画笔
     */
    private Paint mTextPaint;
    /**
     * 文本
     */
    private String mText  ="0%";

    public ProgressView(Context context) {
        this(context,null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mArcCirclePaint = new Paint();
        mArcCirclePaint.setAntiAlias(true);
        mArcCirclePaint.setColor(Color.RED);
        mArcCirclePaint.setStrokeWidth((float) 10);              //线宽
        mArcCirclePaint.setStyle(Paint.Style.STROKE);

        mTextPaint  = new Paint();
        //设置抗锯齿
        mTextPaint.setAntiAlias(true);
        //使文本看起来更清晰
        mTextPaint.setLinearText(true);
        mTextPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画圆弧
        RectF oval=new RectF();                     //RectF对象
        oval.left=10;                              //左边
        oval.top=10;                                   //上边
        oval.right=getWidth()-10;                             //右边
        oval.bottom=getHeight()-10;                                //下边
        canvas.drawArc(oval, 270, mAngleValue, false, mArcCirclePaint);    //绘制圆弧

        //2-文本的位置:居中显示
        int centerX = getWidth()/2;
        mTextPaint.setTextSize(36);
        //计算文本宽度
        int textWidth = (int) mTextPaint.measureText(mText, 0, mText.length());
        //计算baseline:垂直方向居中
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        int textX = centerX-textWidth/2;
        canvas.drawText(mText,textX,baseline,mTextPaint);
    }

    public void setProgress(float value){
        int angleValue = (int) ((value * 1.0)/100 * 360);
        if (angleValue != 0 && value <= 100){
            mAngleValue  = angleValue;
            mText = value+"%";
        }
        //刷新重绘
        invalidate();
    }
}
