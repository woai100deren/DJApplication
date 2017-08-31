package com.detail;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by wangjing4 on 2017/8/4.
 */

public class TopPicScrollView extends ScrollView{
    private  boolean isonce;//加载该View的布局时是否是第一次加载，是第一次就让其实现OnMeasure里的代码
    private LinearLayout mParentView;//布局的父布局，ScrollView内部只能有一个根ViewGroup，就是此View
    private ImageView topImageView;//顶部要滑动放大的图片
    private int mScreenWith;//整个手机屏幕的宽度，这是为了初始化该View时设置mTopView用的
    private int mTopViewHeight;//这个就是mTopView的高度
    private int mCurrentOffset=0;//当前右侧滚条顶点的偏移量。ScrollView右侧是有滚动条的，当下拉时，滚动条向上滑，当向下滑动时，滚动条向下滑动。
    public TopPicScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOverScrollMode(View.OVER_SCROLL_NEVER);
        WindowManager wm= (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        mScreenWith =metrics.widthPixels;
        mTopViewHeight= mScreenWith /2;
    }

    /**
     * 将记录的值设置到控件上，并只让控件设置一次
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(!isonce) {
            mParentView = (LinearLayout) this.getChildAt(0);
            topImageView = (ImageView) mParentView.getChildAt(0);
            topImageView.getLayoutParams().height = mTopViewHeight;
            isonce=true;
        }
    }


    private float startY=0;//向下拉动要放大，手指向下滑时，点击的第一个点的Y坐标
    private boolean isBig;//是否正在向下拉放大上半部分View
    private boolean isTouchOne;//是否是一次连续的MOVE，默认为false,
    //在MoVe时，如果发现滑动标签位移量为0，则获取此时的Y坐标，作为起始坐标，然后置为true,为了在连续的Move中只获取一次起始坐标
    //当Up弹起时，一次触摸移动完成，将isTouchOne置为false
    private float distance=0;//向下滑动到释放的高度差
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action =ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                startY=ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                distance=ev.getY()-startY;
                setT((int)distance);
                startY=ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                if(isBig) {
                    reset();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 对象动画要有的设置方法
     * @param t 手指滑动的距离：负数表示往上，正数表示网下滑
     */
    public void setT(int t) {
        int currentImageViewHeight = topImageView.getLayoutParams().height;//当前顶部图片控件高度
        if(t>=0 && currentImageViewHeight>=mTopViewHeight && mCurrentOffset==0){//表示正在往下滑放大图片
            isBig = true;
            if (currentImageViewHeight + t >= mTopViewHeight * 2) {
                topImageView.getLayoutParams().height = mTopViewHeight * 2;
            } else {
                topImageView.getLayoutParams().height = currentImageViewHeight + t;
            }
            topImageView.requestLayout();
        }else if(t>=0 && currentImageViewHeight==mTopViewHeight && mCurrentOffset==0){//正在往下滑，把顶部图片滑进屏幕
            isBig = false;
        }else if(t<=0 && currentImageViewHeight==mTopViewHeight ){//正在往上滑，把图片滑出屏幕
            isBig = false;
        }else if(t<=0 && currentImageViewHeight <= mTopViewHeight*2 && currentImageViewHeight>mTopViewHeight && mCurrentOffset>=0){//正在往上滑，缩小图片
            isBig = true;
            if(currentImageViewHeight + t <= mTopViewHeight) {
                topImageView.getLayoutParams().height = mTopViewHeight;
            } else {
                topImageView.getLayoutParams().height = currentImageViewHeight + t;
            }
            topImageView.requestLayout();
            scrollTo(0,0);
        }
    }

    /**
     * 释放手指重置高度
     */
    private void reset() {
        topImageView.getLayoutParams().height = mTopViewHeight;
        topImageView.requestLayout();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mCurrentOffset = t;
    }
}
