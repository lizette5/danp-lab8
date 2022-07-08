package com.example.lab8_pushnotification

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val imagen = findViewById<ImageView>(R.id.imageView2)
        imagen.visibility

        val extra:String?=intent.getStringExtra("EXTRA_ARG")

        extra?.let {
            val textView=findViewById<TextView>(R.id.textView)
            textView.text=it
            val imagen = findViewById<ImageView>(R.id.imageView2)
            imagen.setImageResource(R.drawable.informacion)

        }
    }
}