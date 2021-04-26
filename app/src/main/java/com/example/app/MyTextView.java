package com.example.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Random;

public class MyTextView extends androidx.appcompat.widget.AppCompatTextView {
    private static final String[] TEXTS = {
            "北京", "北京", "广州",
            "上海", "武汉", "湖北武汉", "成都", "四川成都", "杭州", "江苏杭州", "苏州", "江苏苏州", "乌鲁木齐", "西藏拉萨", "新疆乌鲁木齐", "北京", "广州", "广东广州", "深圳", "广东深圳",
            "湖北武汉", "成都", "四川成都", "杭州", "江苏杭州", "苏州", "江苏苏州", "乌鲁木齐", "西藏拉萨", "新疆乌鲁木齐", "北京", "广州", "广东广州", "深圳", "广东深圳", "上海", "武汉",
            "湖北武汉", "成都", "四川成都", "杭州", "江苏杭州", "苏州", "江苏苏州", "乌鲁木齐", "西藏拉萨", "新疆乌鲁木齐"
    };

    private static final int[] COLORS = {
            Color.parseColor("#E91E63"),
            Color.parseColor("#673AB7"),
            Color.parseColor("#3F51B5"),
            Color.parseColor("#2196F3"),
            Color.parseColor("#009688"),
            Color.parseColor("#FF9800"),
            Color.parseColor("#FF5722"),
            Color.parseColor("#795548")
    };
    //    private static final int[] TEXT_SIZES = {16, 22, 28};
    private static final int[] TEXT_SIZES = {16};

    private static final int CORNER_RADIUS = 12;
    private static final int X_PADDING = 32;
    private static final int Y_PADDING = 16;

    private final Random random = new Random();
    private Paint paint;

    public MyTextView(@NonNull Context context) {
        this(context, null);
    }

    public MyTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setText(TEXTS[random.nextInt(TEXTS.length)]);
        setTextColor(Color.WHITE);
        setTextSize(TEXT_SIZES[random.nextInt(TEXT_SIZES.length)]);
        setPadding(X_PADDING, Y_PADDING, X_PADDING, Y_PADDING);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(COLORS[random.nextInt(COLORS.length)]);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(0F, 0F, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS, paint);
        super.onDraw(canvas);
    }
}
