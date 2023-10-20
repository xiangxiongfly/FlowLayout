package com.example.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var tagLayout: FlowLayout
    private lateinit var tagLayout2: FlowLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tagLayout = findViewById(R.id.tagLayout)
        tagLayout2 = findViewById(R.id.tagLayout2)
    }

    fun addTag(view: View) {
        val textView = MyTextView(this)
        val textView2 = MyTextView(this)
        tagLayout.addView(textView)
        tagLayout2.addView(textView2)
    }
}