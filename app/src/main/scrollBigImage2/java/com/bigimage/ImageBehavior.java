package com.bigimage;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.orhanobut.logger.Logger;

/**
 * Created by wangjing4 on 2017/8/7.
 */

public class ImageBehavior extends AppBarLayout.Behavior{
    private static final String TAG_IMAGE = "imageView";
    private static final String TAG_RECYCLERVIEW = "rvToDoList";
    private RecyclerView recyclerView;
    private View mTopImageView;//顶部图片
    private int mScreenWith;//整个手机屏幕的宽度，这是为了初始化该View时设置mTopView用的
    private int mTopViewHeight;//这个就是mTopView的高度
    private Context context;
    private float distance=0;//向下滑动到释放的高度差
    private boolean isBig = false;//图片是否放大了
    public ImageBehavior() {
    }

    public ImageBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, AppBarLayout abl, int layoutDirection) {
        boolean handled = super.onLayoutChild(parent, abl, layoutDirection);
        // 需要在调用过super.onLayoutChild()方法之后获取
        if (mTopImageView == null) {
            mTopImageView = parent.findViewWithTag(TAG_IMAGE);
            if (mTopImageView != null) {
                initial(abl);
            }
        }

        if(recyclerView == null){
            recyclerView = (RecyclerView) parent.findViewWithTag(TAG_RECYCLERVIEW);
        }
        return handled;
    }

    /**
     * 初始化图片的高度
     * @param abl
     */
    private void initial(AppBarLayout abl) {
        abl.setClipChildren(false);
        WindowManager wm= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        mScreenWith =metrics.widthPixels;
        mTopViewHeight= mScreenWith /2;

        mTopImageView.getLayoutParams().height = mTopViewHeight;
        mTopImageView.requestLayout();

        abl.setBottom(mTopViewHeight);
    }


    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed) {
        if (mTopImageView != null && ((dy < 0 && child.getBottom() >= mTopViewHeight)
                || (dy > 0 && child.getBottom() > mTopViewHeight))) {//下滑
            scale(child, target, dy);
            return;
        }
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
    }

    /**
     * 停止滑动
     * @param coordinatorLayout
     * @param abl
     * @param target
     */
    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl, View target) {
        recovery(abl);
        super.onStopNestedScroll(coordinatorLayout, abl, target);
    }

    /**
     * 还原位置
     * @param abl
     */
    private void recovery(final AppBarLayout abl) {
        if (isBig) {
            mTopImageView.getLayoutParams().height = mTopViewHeight;
            mTopImageView.requestLayout();
            abl.setBottom(mTopViewHeight);
        }
    }

    /**
     * 放大图片处理
     * @param abl
     * @param target
     * @param dy
     */
//    private void scale(AppBarLayout abl, View target, int dy) {
//        System.out.println(dy);
//        mTotalDy += -dy;
//
//        if(recyclerView!=null) {
//            recyclerView.scrollToPosition(0);
//        }
//
//        if((mTopViewHeight + mTotalDy) >= mScreenWith){
//            mTopImageView.getLayoutParams().height = mScreenWith;
//            mTopImageView.requestLayout();
//            abl.setBottom(mScreenWith);
//        }else if((mTopViewHeight + mTotalDy) <= mTopViewHeight){
//            mTopImageView.getLayoutParams().height = mTopViewHeight;
//            mTopImageView.requestLayout();
//            abl.setBottom(mTopViewHeight);
//        }else{
//            mTopImageView.getLayoutParams().height = mTopViewHeight + mTotalDy;
//            mTopImageView.requestLayout();
//            abl.setBottom(mTopViewHeight + mTotalDy);
//        }
//    }
    private void scale(AppBarLayout abl, View target, int dy) {
        distance= - dy;
        int currentImageViewHeight = mTopImageView.getLayoutParams().height;//当前顶部图片控件高度
        if(distance>=0 && currentImageViewHeight>=mTopViewHeight){//表示正在往下滑放大图片
            isBig = true;
            if (currentImageViewHeight + distance >= mTopViewHeight * 2) {
                mTopImageView.getLayoutParams().height = mTopViewHeight * 2;
                abl.setBottom(mTopViewHeight * 2);
            } else {
                mTopImageView.getLayoutParams().height = (int) (currentImageViewHeight + distance);
                abl.setBottom((int) (currentImageViewHeight + distance));
            }
            mTopImageView.requestLayout();
        }else if(distance>=0 && currentImageViewHeight==mTopViewHeight){//正在往下滑，把顶部图片滑进屏幕
            isBig = false;
        }else if(distance<=0 && currentImageViewHeight==mTopViewHeight ){//正在往上滑，把图片滑出屏幕
            isBig = false;
        }else if(distance<=0 && currentImageViewHeight <= mTopViewHeight*2 && currentImageViewHeight>mTopViewHeight){//正在往上滑，缩小图片
            isBig = true;
            if(currentImageViewHeight + distance <= mTopViewHeight) {
                mTopImageView.getLayoutParams().height = mTopViewHeight;
                abl.setBottom(mTopViewHeight);
            } else {
                mTopImageView.getLayoutParams().height = (int) (currentImageViewHeight + distance);
                abl.setBottom((int) (currentImageViewHeight + distance));
            }
            mTopImageView.requestLayout();
            recyclerView.scrollToPosition(0);
        }
    }
}
