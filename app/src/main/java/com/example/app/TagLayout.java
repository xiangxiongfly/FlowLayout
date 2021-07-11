package com.example.app;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class TagLayout extends ViewGroup {

    private int horizontalSpace = dp2px(20);
    private int verticalSpace = dp2px(16);

    //所有子View
    private ArrayList<ArrayList<View>> allViewList = new ArrayList<>();
    //存储每一行高度
    private ArrayList<Integer> lineHeightList = new ArrayList<>();


    public TagLayout(Context context) {
        super(context);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        allViewList.clear();
        lineHeightList.clear();

        //获取父View的模式和尺寸
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        //定义TagLayout已使用宽和高空间
        int widthUsed = 0;
        int heightUsed = 0;

        //定义单行的宽的已使用空间
        int lineWidthUsed = 0;

        //定义单行最大值
        int lineMaxHeight = 0;

        //一行的子View
        ArrayList<View> lineViews = new ArrayList<>();

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);

            //换行
            if (widthMode != MeasureSpec.UNSPECIFIED &&
                    getPaddingLeft() + getPaddingRight() + lineWidthUsed + childView.getMeasuredWidth() + horizontalSpace > widthSize) {
                allViewList.add(lineViews);
                lineHeightList.add(lineMaxHeight);
                lineViews = new ArrayList<>();

                widthUsed = Math.max(widthUsed, lineWidthUsed);
                heightUsed += lineMaxHeight + verticalSpace;

                lineWidthUsed = 0;
                lineMaxHeight = 0;
                measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            }

            lineViews.add(childView);

            lineWidthUsed += childView.getMeasuredWidth() + horizontalSpace;
            lineMaxHeight = Math.max(lineMaxHeight, childView.getMeasuredHeight());

            //处理最后一行
            if (i == childCount - 1) {
                allViewList.add(lineViews);
                lineHeightList.add(lineMaxHeight);
                widthUsed = Math.max(widthUsed, lineWidthUsed);
                heightUsed += lineMaxHeight;
            }
        }

        int selfWidth = widthUsed;
        int selfHeight = heightUsed;
        setMeasuredDimension(selfWidth, selfHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int lineCount = allViewList.size();
        int left = getPaddingLeft();
        int top = getPaddingTop();
        for (int i = 0; i < lineCount; i++) {
            ArrayList<View> lineViewList = allViewList.get(i);
            for (View childView : lineViewList) {
                int childLeft = left;
                int childTop = top;
                int childRight = childLeft + childView.getMeasuredWidth();
                int childBottom = childTop + childView.getMeasuredHeight();
                childView.layout(childLeft, childTop, childRight, childBottom);
                left = childRight + horizontalSpace;
            }

            int lineHeight = lineHeightList.get(i);
            top += lineHeight + verticalSpace;
            left = getPaddingLeft();
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }

    public int dp2px(float dpValue) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
