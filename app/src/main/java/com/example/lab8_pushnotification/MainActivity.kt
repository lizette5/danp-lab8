package com.example.lab8_pushnotification

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {
    companion object{
        const val INTENT_REQUEST=0
    }
    val channelId = "notificaion_chanel"
    val channelName = " notification_name"

    val notificacionId=0
    val notificacionId2=1

    private lateinit var notificationCustom2: Notification

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotification()
        buildNotificationStyle5()

        // La campanita
        //automaticamente cuando presiona la notificacion se abre la pantalla principal
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(
                MyFirebaseMessagingService.INTENT_REQUEST,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
        //primer boton previus manual
        val buttonIntent = Intent(this, MainActivity2::class.java)
        buttonIntent.putExtra("EXTRA_ARG", "Boton PREVIOUS presionado")

        val buttonPending = PendingIntent.getActivity(
            this,
            MyFirebaseMessagingService.INTENT_BUTTON,
            buttonIntent,
            PendingIntent.FLAG_ONE_SHOT

        )
        val action = NotificationCompat.Action.Builder(
            android.R.drawable.ic_media_rew,
            "PREVIUS",
            buttonPending
        ).build()
       //segundo boton  next manual
        val buttonIntent2 = Intent(this, MainActivity3::class.java)
        buttonIntent2.putExtra("EXTRA_ARG", "Boton NEXT  presionado")

        val buttonPending2 = PendingIntent.getActivity(
            this,
            MyFirebaseMessagingService.INTENT_BUTTON,
            buttonIntent2,
            PendingIntent.FLAG_ONE_SHOT

        )
        val action2 = NotificationCompat.Action.Builder(
            android.R.drawable.ic_media_ff,
            "NEXT",
            buttonPending2
        ).build()

        //notificacion manual (campanita) que si funciona los  botones
        val notification: Notification = NotificationCompat.Builder(
            applicationContext, channelId
        ).also {
            it.setContentTitle("Ecologia-Manual")
            it.setContentText("Bienvenidos!!!")
            it.setSmallIcon(R.drawable.icono)
            it.setAutoCancel(true)
            it.priority = NotificationCompat.PRIORITY_MAX
            it.setContentIntent(pendingIntent)
            it.addAction(action)
            it.addAction(action2)
            it.setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            it.setOnlyAlertOnce(true)
        }.build()

        val notificationManager = NotificationManagerCompat.from(this)
        val buton = findViewById<Button>(R.id.buttonNoti)
        buton.setOnClickListener{
            notificationManager.notify(notificacionId,notification)
        }
        val buton2 =findViewById<Button>(R.id.buttonNoti2)
        buton2.setOnClickListener {
            notificationManager.notify(notificacionId2,notificationCustom2 )
        }
    }



    // La bocina
    //notificacion con imagen creada manualmente (bocina )
    private fun buildNotificationStyle5(){
        val myBitmap = R.drawable.tema.createBitmap(this)
        val intent = Intent()
        val pendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(INTENT_REQUEST, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        notificationCustom2 = NotificationCompat.Builder(this, channelId).also {
            it.setSmallIcon(R.drawable.ic_notification)
            it.setContentTitle("Ecologiando")
            it.setContentText("Accion")
            it.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            it.addAction(R.drawable.ic_previous, "Previous", pendingIntent)
            it.addAction(R.drawable.ic_pause, "Pause", pendingIntent)
            it.addAction(R.drawable.ic_next, "Next", pendingIntent)
            it.setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView()
            )
            it.setLargeIcon(myBitmap)
        }.build()
    }

    //ambas notificaiones
    private fun createNotification() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val importance =NotificationManager.IMPORTANCE_HIGH

            val channel =NotificationChannel(channelId,channelName,importance).apply {
                lightColor=Color.RED
                enableLights(true)
            }
            val manager=getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager

            manager.createNotificationChannel(channel)
        }

    }
    fun Int.createBitmap(context: Context): Bitmap {
        return BitmapFactory.decodeResource(context.resources, this)
    }
}