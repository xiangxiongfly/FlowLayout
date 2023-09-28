package com.example.app

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import java.util.*


class FlowLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
    companion object {
        val HORIZONTAL_SPACE = dp2px(20f)
        val VERTICAL_SPACE = dp2px(16f)

        fun dp2px(dp: Int): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                Resources.getSystem().displayMetrics
            ).toInt()
        }

        private fun dp2px(dp: Float): Float {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                Resources.getSystem().displayMetrics
            )
        }
    }

    private var mHorizontalSpace: Int = 0
    private var mVerticalSpace: Int = 0

    //存储所有子View
    private val allViewList = ArrayList<ArrayList<View>>()

    //存储每一行高度
    private val lineHeightList = ArrayList<Int>()


    init {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout)
            mHorizontalSpace =
                a.getDimension(
                    R.styleable.FlowLayout_horizontalSpace, HORIZONTAL_SPACE
                ).toInt()
            mVerticalSpace = a.getDimension(
                R.styleable.FlowLayout_verticalSpace, VERTICAL_SPACE
            ).toInt()
            a.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        allViewList.clear()
        lineHeightList.clear()
        val widthSize = MeasureSpec.getSize(widthMeasureSpec) //获取FlowLayout的测量尺寸
        val widthMode = MeasureSpec.getMode(widthMeasureSpec) //获取FlowLayout的测量模式
        val horizontalPadding = paddingLeft + paddingRight //获取FlowLayout的水平内边距
        val childCount = childCount //获取子View数量
        var widthUsed = 0 //FlowLayout已用宽度
        var heightUsed = 0 //FlowLayout已用高度
        var lineWidthUsed = 0 //记录行已用宽度
        var lineHeight = 0 //记录行高
        var lineViews = ArrayList<View>() //存储一行中的所有子View
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            //测量子View，分别传入子View、FlowLayout的测量规格
            measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            //获取子View测量的宽高
            val childMeasuredWidth = childView.measuredWidth
            val childMeasuredHeight = childView.measuredHeight

            //是否换行
            if (widthMode != MeasureSpec.UNSPECIFIED &&
                horizontalPadding + lineWidthUsed + childMeasuredWidth + mHorizontalSpace > widthSize
            ) {
                allViewList.add(lineViews)
                lineHeightList.add(lineHeight)
                lineViews = ArrayList()
                widthUsed = Math.max(widthUsed, lineWidthUsed)
                heightUsed += lineHeight + mVerticalSpace
                lineWidthUsed = 0
                lineHeight = 0
                measureChildWithMargins(
                    childView,
                    widthMeasureSpec,
                    0,
                    heightMeasureSpec,
                    heightUsed
                )
            }
            lineViews.add(childView)
            lineWidthUsed = lineWidthUsed + childMeasuredWidth + mHorizontalSpace
            lineHeight = Math.max(lineHeight, childMeasuredHeight)

            //处理最后一行
            if (i == childCount - 1) {
                allViewList.add(lineViews)
                lineHeightList.add(lineHeight)
                widthUsed = Math.max(widthUsed, lineWidthUsed)
                heightUsed += lineHeight
            }
        }
        val selfWidth = widthUsed + paddingLeft + paddingRight
        val selfHeight = heightUsed + paddingTop + paddingBottom
        setMeasuredDimension(selfWidth, selfHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val lineCount = allViewList.size
        var left = paddingLeft
        var top = paddingTop
        for (i in 0 until lineCount) {
            val lineViewList = allViewList[i]
            for (childView in lineViewList) {
                val childLeft = left
                val childRight = childLeft + childView.measuredWidth
                val childBottom = top + childView.measuredHeight
                childView.layout(childLeft, top, childRight, childBottom)
                left = childRight + mHorizontalSpace
            }
            val lineHeight = lineHeightList[i]
            top += lineHeight + mVerticalSpace
            left = paddingLeft
        }
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateLayoutParams(p: LayoutParams): LayoutParams {
        return MarginLayoutParams(p)
    }

    override fun checkLayoutParams(p: LayoutParams): Boolean {
        return p is MarginLayoutParams
    }
}