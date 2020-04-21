package com.service;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.dj.collection.R;

import java.util.List;

public class TagGroup extends ViewGroup {
    private TagViewHolder moreTagHolder;
    private int moreTagMeasureWidth;
    private int moreTagMeasureHeight;


    private int maxRow;
    private int horizontalSpacing;
    private int verticalSpacing;

    private float default_horizontal_spacing;
    private float default_vertical_spacing;
    private int MAX_ROW_COUNT;

    public TagGroup(Context context) {
        this(context,null);
    }

    public TagGroup(Context context,AttributeSet attrs) {
        this(context,attrs,0);
    }

    public TagGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        default_horizontal_spacing = dp2px(8.0f);
        default_vertical_spacing = dp2px(8.0f);
        MAX_ROW_COUNT = Integer.MAX_VALUE;

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TagGroup);
        try {
            horizontalSpacing = (int) typedArray.getDimension(R.styleable.TagGroup_tag_horizontalSpacing, default_horizontal_spacing);
            verticalSpacing = (int) typedArray.getDimension(R.styleable.TagGroup_tag_verticalSpacing, default_vertical_spacing);
            maxRow = typedArray.getInt(R.styleable.TagGroup_max_row, MAX_ROW_COUNT);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //计算子view
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int width = 0;
        int height = 0;

        //有多少行view
        int row = 0;
        int rowWidth = 0;
        int rowMaxHeight = 0;

        if (moreTagHolder != null) {
            moreTagMeasureWidth = moreTagHolder.getView().getMeasuredWidth();
            moreTagMeasureHeight = moreTagHolder.getView().getMeasuredHeight();
        }

        //获取子view的个数
        final int count = getChildCount();

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            final int childWidth = child.getMeasuredWidth();
            final int childHeight = child.getMeasuredHeight();

            if (child.getVisibility() != GONE) {
                if (row + 1 >= maxRow && rowWidth + childWidth  > widthSize) {
                    break;
                }
                rowWidth += childWidth;
                if (rowWidth > widthSize) {
                    rowWidth = childWidth;
                    height += rowMaxHeight + verticalSpacing;
                    rowMaxHeight = childHeight;
                    row++;
                } else { // This line.
                    rowMaxHeight = Math.max(rowMaxHeight, childHeight);
                }
                rowWidth += horizontalSpacing;
            }
        }

        // 总高度
        height += rowMaxHeight;

        // 总高度还要加上内部距顶部和距底部
        height += getPaddingTop() + getPaddingBottom();

        // If the tags grouped in one row, set the width to wrap the tags.
        if (row == 0) {
            width = rowWidth;
            width += getPaddingLeft() + getPaddingRight();
        } else {
            width = widthSize;
        }

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width,
                heightMode == MeasureSpec.EXACTLY ? heightSize : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int parentLeft = getPaddingLeft();
        final int parentRight = r - l - getPaddingRight();
        final int parentTop = getPaddingTop();
        final int parentBottom = b - t - getPaddingBottom();

        int childLeft = parentLeft;
        int childTop = parentTop;

        int row = 0;
        int rowMaxHeight = 0;

        boolean showMoreTag = false;

        final int count = getChildCount();
        int unTagCount = count;
        if (moreTagHolder != null) {
            unTagCount--;
        }
        for (int i = 0; i < unTagCount; i++) {
            final View child = getChildAt(i);
            final int width = child.getMeasuredWidth();
            final int height = child.getMeasuredHeight();

            if (child.getVisibility() != GONE) {
                if (row + 1 >= maxRow && childLeft + width + (horizontalSpacing + moreTagMeasureWidth)  > parentRight) {
                    // 预留一个空位放置moreTag
                    showMoreTag = true;
                    break;
                }
                if (childLeft + width > parentRight) {
                    childLeft = parentLeft;
                    childTop += rowMaxHeight + verticalSpacing;
                    rowMaxHeight = height;
                    row++;
                } else {
                    rowMaxHeight = Math.max(rowMaxHeight, height);
                }

                // this is point
                child.layout(childLeft, childTop, childLeft + width, childTop + height);

                childLeft += width + horizontalSpacing;
            }
        }

        if (showMoreTag) {
            final View child = getChildAt(count - 1);
            final int width = child.getMeasuredWidth();
            final int height = child.getMeasuredHeight();
            child.layout(childLeft, childTop, childLeft + width, childTop + height);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public interface TagViewHolder {
        View getView();
    }

    public void setTags(List<TagViewHolder> viewHolders) {
        setTags(viewHolders, null);
    }

    public void setTags(List<TagViewHolder> viewHolders, TagViewHolder moreTagHolder) {
        removeAllViews();
        this.moreTagHolder = moreTagHolder;
        viewHolders.add(moreTagHolder);
        for (TagViewHolder viewHolder : viewHolders) {
            addView(viewHolder.getView());
        }
    }

    public float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    public float sp2px(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getResources().getDisplayMetrics());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("11111111","2222222222222");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("11111111","3333333333");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("11111111","444444444");
        return super.onInterceptTouchEvent(ev);
    }
}
