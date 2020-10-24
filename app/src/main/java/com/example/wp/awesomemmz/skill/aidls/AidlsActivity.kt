package com.example.wp.awesomemmz.skill.aidls

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.support.v7.app.AppCompatActivity
import com.example.wp.awesomemmz.APP
import com.example.wp.awesomemmz.R
import com.example.wp.awesomemmz.book.IBookManager
import com.example.wp.resource.utils.LogUtils
import com.example.wp.awesomemmz.book.Book
import kotlinx.android.synthetic.main.activity_aidls.*
import java.util.*
import kotlin.math.roundToInt
import kotlin.random.Random

class AidlsActivity : AppCompatActivity() {
    private var serviceConnected = false
    private var iBookService: IBookManager? = null

    private var messengerRegistered = false
    private var mMessenger: Messenger? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aidls)

        btnStartService.setOnClickListener {
            startRemoteService()
        }
        btnAddBook.setOnClickListener { addBook() }
        btnListBook.setOnClickListener { listBook() }

        btnBindMessenger.setOnClickListener { bindMessengerService() }
    }

    private fun startRemoteService() {
        //startService(Intent(this, BookManagerService::class.java))

        val intent = Intent("com.example.wp.awesomemmz.BookManagerService")
        intent.`package` = "com.example.wp.awesomemmz"
        startService(intent)

        //bindRemoteService()
    }

    private fun addBook() {
        bindRemoteService()
        if (serviceConnected) {
            try {
                val id = Random.nextInt(1000)
                iBookService!!.addBook(Book(id.toString(), "book-$id"))
            } catch (e: RemoteException) {

            }
        }
    }

    private fun listBook() {
        bindRemoteService()
        if (serviceConnected) {
            try {
                val listBooks = iBookService!!.listBooks()
                LogUtils.d("-----$listBooks")
                APP.toast("$listBooks")
            } catch (e: RemoteException) {

            }
        }
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            iBookService = IBookManager.Stub.asInterface(service)
            serviceConnected = true
            APP.toast("remote service connected..")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            serviceConnected = false
        }
    }

    private fun bindRemoteService() {
        if (serviceConnected) return
        val intent = Intent("com.example.wp.awesomemmz.BookManagerService")
        intent.`package` = "com.example.wp.awesomemmz"
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    // - - - - - - - - - -- - - - - -- - - -- - -- - -- - -
    private val messengerConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            messengerRegistered = true
            mMessenger = Messenger(service)
            val msg = Message.obtain(null, 111)
            msg.data = Bundle().apply { putString("msg", "msg from client...111") }

            //reply
            msg.replyTo = Messenger(MessengerHandler())

            try {
                mMessenger!!.send(msg)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
            APP.toast("remote service connected..")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            messengerRegistered = false
        }
    }

    private fun bindMessengerService() {
        val intent = Intent("com.example.wp.awesomemmz.MessengerService")
        intent.`package` = "com.example.wp.awesomemmz"
        bindService(intent, messengerConnection, Context.BIND_AUTO_CREATE)
    }

    private class MessengerHandler : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                1112 -> {
                    val message = msg.data.getString("reply")
                    LogUtils.d("client receive: $message")
                    APP.toast("client receive: $message}")
                }
                else -> super.handleMessage(msg)
            }
        }
    }

    override fun onDestroy() {
        //unbind service.
        if (serviceConnected) unbindService(serviceConnection)
        if (messengerRegistered) unbindService(messengerConnection)

        super.onDestroy()
    }
}