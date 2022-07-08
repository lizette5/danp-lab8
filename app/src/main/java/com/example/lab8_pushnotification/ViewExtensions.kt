package com.example.lab8_pushnotification

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

fun Int.createBitmap(context: Context): Bitmap{
    return BitmapFactory.decodeResource(context.resources, this)
}