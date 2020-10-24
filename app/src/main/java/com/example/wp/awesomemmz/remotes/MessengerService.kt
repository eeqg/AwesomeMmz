package com.example.wp.awesomemmz.remotes

import android.app.Service
import android.content.Intent
import android.os.*
import com.example.wp.awesomemmz.APP
import com.example.wp.resource.utils.LogUtils

/**
 * Created by wp on 2020/10/22.
 */
internal class MessengerService : Service() {

    private class MessengerHandler : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                111 -> {
                    val message = msg.data.getString("msg")
                    LogUtils.d("server receive: $message")
                    APP.toast("server receive: $message}")

                    //reply
                    val clientMessenger = msg.replyTo
                    val replyMessage = Message.obtain(null, 1112)
                    replyMessage.data = Bundle().apply { putString("reply", "server reply message.") }
                    try {
                        clientMessenger.send(replyMessage)
                    } catch (e: RemoteException) {
                        e.printStackTrace()
                    }
                }
                222 -> {
                }
                else -> super.handleMessage(msg)
            }
        }
    }

    private val mMessenger = Messenger(MessengerHandler())

    override fun onBind(intent: Intent): IBinder? {
        return mMessenger.binder
    }
}