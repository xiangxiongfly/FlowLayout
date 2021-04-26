package com.example.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;

public class TagLayout extends ViewGroup {
    private final ArrayList<ChildInfo> childrenInfo = new ArrayList<>();

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

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();

            //处理换行逻辑
            if (widthMode != MeasureSpec.UNSPECIFIED && lineWidthUsed + childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin > widthSize) {
                lineWidthUsed = 0;
                heightUsed += lineMaxHeight;
                lineMaxHeight = 0;
                measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            }

            if (i == childrenInfo.size()) {
                childrenInfo.add(new ChildInfo());
            }

            //设置子View的宽高 margin值 子View的范围
            ChildInfo childInfo = childrenInfo.get(i);
            childInfo.width = childView.getMeasuredWidth();
            childInfo.height = childView.getMeasuredHeight();
            childInfo.leftMargin = lp.leftMargin;
            childInfo.rightMargin = lp.rightMargin;
            childInfo.topMargin = lp.topMargin;
            childInfo.bottomMargin = lp.bottomMargin;
            childInfo.left = lineWidthUsed;
            childInfo.top = heightUsed;
            childInfo.right = lineWidthUsed + childInfo.width + childInfo.leftMargin + childInfo.rightMargin;
            childInfo.bottom = heightUsed + childInfo.height + childInfo.topMargin + childInfo.bottomMargin;

            lineWidthUsed += (childInfo.right - childInfo.left);
            widthUsed = Math.max(widthUsed, lineWidthUsed);
            lineMaxHeight = Math.max(lineMaxHeight, childInfo.bottom - childInfo.top);
        }

        int selfWidth = widthUsed;
        int selfHeight = heightUsed + lineMaxHeight;
        setMeasuredDimension(selfWidth, selfHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Iterator<ChildInfo> iterator = childrenInfo.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            ChildInfo itemInfo = iterator.next();
            getChildAt(index).layout(itemInfo.left + itemInfo.leftMargin, itemInfo.top + itemInfo.topMargin, itemInfo.right - itemInfo.rightMargin, itemInfo.bottom - itemInfo.bottomMargin);
            index++;
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

    class ChildInfo {
        //子View的宽高
        public int width, height;
        //子view的margin值
        public int leftMargin, rightMargin, topMargin, bottomMargin;
        //子View在父View中的布局范围
        public int left, top, right, bottom;
    }
}
