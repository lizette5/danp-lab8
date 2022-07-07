package com.example.lab8_pushnotification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId = "notificaion_chanel"
const val channelName = " com.example.lab8_pushnotification"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    //generando la notificaicon
    //creando notificacion con su diseño personalizado
    //ver notificacion

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
       if(remoteMessage.getNotification()!=null){
           generateNotification(remoteMessage.notification!!.title!!,remoteMessage.notification!!.body!!)
       }
    }
    @SuppressLint("RemoteViewLayout")
    fun getRemoteView(titulo: String, mensaje: String): RemoteViews {
        val remoteView = RemoteViews("com.example.lab8_pushnotification", R.layout.notification)
        remoteView.setTextViewText(R.id.titulo, titulo)
        remoteView.setTextViewText(R.id.mensaje, mensaje)
        remoteView.setImageViewResource(R.id.logo, R.drawable.icono)

        return remoteView
    }

    fun generateNotification(titulo: String, mensaje: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        //canal de id y nombre

        var builder: NotificationCompat.Builder = NotificationCompat.Builder(
            applicationContext, channelId)
            .setSmallIcon(R.drawable.icono)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(titulo, mensaje))

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel =NotificationChannel(channelId, channelName,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0,builder.build())
    }
}