package com.example.lab8_pushnotification

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.ImageView
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId = "notificaion_chanel"
const val channelName = " com.example.lab8_pushnotification"

class MyFirebaseMessagingService : FirebaseMessagingService() {
    companion object {
        const val INTENT_REQUEST=0
        const val INTENT_BUTTON = 1
    }
    //generando la notificacion
    //creando notificacion con su diseño personalizado
    //ver notificacion

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.getNotification() != null) {
            generateNotification(
                remoteMessage.notification!!.title!!,
                remoteMessage.notification!!.body!!
            )
        }
    }

    @SuppressLint("RemoteViewLayout")
    fun getRemoteView(titulo: String, mensaje: String): RemoteViews {
        val remoteView = RemoteViews("com.example.lab8_pushnotification", R.layout.notification)
        remoteView.setTextViewText(R.id.titulo, titulo)
        remoteView.setTextViewText(R.id.mensaje, mensaje)
        remoteView.setImageViewResource(R.id.logo, R.drawable.icono)
        //imagenes de las vistas
        remoteView.setImageViewResource(R.id.imageView2,R.drawable.informacion)
        remoteView.setImageViewResource(R.id.imageView3,R.drawable.planeta)

        return remoteView
    }

    fun generateNotification(titulo: String, mensaje: String) {

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val intent2 = Intent(this, MainActivity2::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val intent3 = Intent(this, MainActivity3::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val pendingIntent2 = PendingIntent.getActivity(this, 1, intent2, PendingIntent.FLAG_UPDATE_CURRENT)

        val pendingIntent3 = PendingIntent.getActivity(this, 2, intent3, PendingIntent.FLAG_UPDATE_CURRENT)

        //icono en la notificación
        val myBitmap = R.drawable.tema.createBitmap(this)

        //canales para el id y nombre
        var builder: NotificationCompat.Builder = NotificationCompat.Builder(
            applicationContext, channelId
        )
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.icono)
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_previous,"PREVIUS",pendingIntent2)
            .addAction(R.drawable.ic_next,"NEXT",pendingIntent3)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView()
            )
            .setLargeIcon(myBitmap)
        builder.build()
        builder = builder.setContent(getRemoteView(titulo, mensaje))

        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0, builder.build())


    }
    fun Int.createBitmap(context: Context): Bitmap {
        return BitmapFactory.decodeResource(context.resources, this)
    }
}
