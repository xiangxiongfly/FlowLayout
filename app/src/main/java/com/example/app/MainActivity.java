package com.example.app;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private FlowLayout tagLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tagLayout = findViewById(R.id.tagLayout);
    }

    public void addTag(View view) {
        MyTextView childView = new MyTextView(MainActivity.this);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.rightMargin = 20;
        lp.leftMargin = 20;
        lp.topMargin = 10;
        lp.bottomMargin = 10;
        childView.setLayoutParams(lp);
        tagLayout.addView(childView);
    }
}