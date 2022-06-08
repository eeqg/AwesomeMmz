package com.example.wp.awesomemmz.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.wp.awesomemmz.R
import kotlinx.android.synthetic.main.activity_notification.*
import java.lang.Exception

class NotificationActivity : AppCompatActivity() {
    
    private val nManager by lazy { getSystemService(NOTIFICATION_SERVICE) as NotificationManager }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        
        btn1.setOnClickListener { sendNotification(1) }
        btn2.setOnClickListener { sendNotification(2) }
    }
    
    private fun sendNotification(type: Int) {
        var nId = 1
        var soundUri: Uri? = null
        var channelId = "id01"
        var channelName = ""
        when (type) {
            1 -> {
                channelId = "id01"
                channelName = "channel 01"
                soundUri = Uri.parse("android.resource://com.example.wp.awesomemmz/raw/new_order")
            }
            2 -> {
                channelId = "id02"
                channelName = "channel 02"
                soundUri =
                    Uri.parse("android.resource://com.example.wp.awesomemmz/raw/new_msg_en_us")
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //NotificationChannel
            //val channelId = "id01"
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
                    .apply {
                        description = "描述123456"
                        //sound
                        setSound(soundUri, null)
                        //vibration
                        enableVibration(true)
                        vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 100)
                        //light
                        enableLights(true)
                        lightColor = Color.GREEN
                    }

//            val channel = try {
//                nManager.getNotificationChannel(channelId).apply {
//                    setSound(soundUri, null)
//                }
//            } catch (e: Exception) {
//                NotificationChannel(channelId, "channel 01", NotificationManager.IMPORTANCE_DEFAULT)
//                    .apply {
//                        description = "描述"
//                        //sound
//                        setSound(soundUri, null)
//                        //vibration
//                        enableVibration(true)
//                        vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 100)
//                        //light
//                        enableLights(true)
//                        lightColor = Color.GREEN
//                    }
//            }
            
//            try {
//                nManager.deleteNotificationChannel(channelId)
//            } catch (e: Exception) {
//            }
//            val channel =
//                NotificationChannel(channelId, "channel 01", NotificationManager.IMPORTANCE_DEFAULT)
//                    .apply {
//                        description = "描述"
//                        //sound
//                        setSound(soundUri, null)
//                        //vibration
//                        enableVibration(true)
//                        vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 100)
//                        //light
//                        enableLights(true)
//                        lightColor = Color.GREEN
//                    }
            
            nManager.createNotificationChannel(channel)
            
            val notification = Notification.Builder(this, channelId)
                .setContentTitle("Title $type")
                .setContentText("content...")
                //.setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                //.setSound(Uri.parse("android.resource://com.example.wp.awesomemmz/raw/new_msg"))
                //.setContentIntent()
                .setAutoCancel(true)
                .build()
            nManager.notify(1, notification)
        } else {
            val notification = Notification.Builder(this)
                .setContentTitle("Title $type")
                .setContentText("content...")
                //.setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                //.setSound(Uri.parse("android.resource://com.example.wp.awesomemmz/raw/new_msg"))
                //.setContentIntent()
                .setAutoCancel(true)
                .build()
            nManager.notify(1, notification)
        }
    }
}