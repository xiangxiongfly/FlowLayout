package com.example.app

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import java.util.*

class MyTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    companion object {
        private const val TEXT_SIZE = 16F //文字大小
        private const val CORNER_RADIUS = 12F //圆角
        private const val X_PADDING = 32 //水平padding
        private const val Y_PADDING = 16 //垂直padding
        private val TEXTS = arrayOf(
            "北京",
            "北京",
            "广州",
            "上海",
            "武汉",
            "湖北",
            "成都",
            "四川",
            "杭州",
            "江苏",
            "苏州",
            "江苏苏州",
            "乌鲁木齐",
            "西藏拉萨",
            "新疆乌鲁木齐",
            "北京",
            "广州",
            "广东广州",
            "深圳",
            "广东深圳",
            "湖北武汉",
            "成都",
            "四川成都",
            "杭州",
            "江苏杭州",
            "苏州",
            "江苏苏州",
            "乌鲁木齐",
            "西藏拉萨",
            "新疆乌鲁木齐"
        )
        private val COLORS = intArrayOf(
            Color.parseColor("#E91E63"),
            Color.parseColor("#673AB7"),
            Color.parseColor("#3F51B5"),
            Color.parseColor("#2196F3"),
            Color.parseColor("#009688"),
            Color.parseColor("#FF9800"),
            Color.parseColor("#FF5722"),
            Color.parseColor("#795548")
        )
    }

    private val random = Random()
    private var paint: Paint

    init {
        text = TEXTS[random.nextInt(TEXTS.size)]
        setTextColor(Color.WHITE)
        textSize = TEXT_SIZE
        setPadding(X_PADDING, Y_PADDING, X_PADDING, Y_PADDING)
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = COLORS[random.nextInt(COLORS.size)]
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            CORNER_RADIUS,
            CORNER_RADIUS,
            paint
        )
        super.onDraw(canvas)
    }
}