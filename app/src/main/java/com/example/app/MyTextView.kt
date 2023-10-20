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
            "北京", "上海", "天津", "深圳", "广州", "南京", "武汉", "乌鲁木齐",
            "湖北武汉", "湖南长沙", "江苏南京", "浙江杭州", "江西南昌", "山东济南"
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
        if (text.isEmpty()) {
            text = TEXTS[random.nextInt(TEXTS.size)] //文字
        }
        setTextColor(Color.WHITE) //文字颜色
        textSize = TEXT_SIZE //字体大小
        setPadding(X_PADDING, Y_PADDING, X_PADDING, Y_PADDING) //内边距
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