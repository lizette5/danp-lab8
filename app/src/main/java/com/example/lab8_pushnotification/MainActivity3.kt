package com.example.lab8_pushnotification

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val imagen = findViewById<ImageView>(R.id.imageView3)
        imagen.visibility

        val extra: String? = intent.getStringExtra("EXTRA_ARG")

        extra?.let {
            val textView = findViewById<TextView>(R.id.textView)
            val textView3 = findViewById<TextView>(R.id.textView3)
            textView3.text=textView3.text.toString()
            val imagen = findViewById<ImageView>(R.id.imageView3)
            imagen.setImageResource(R.drawable.planeta)
            textView.text = it
        }
    }
}