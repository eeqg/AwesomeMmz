package com.example.wp.awesomemmz.book

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import com.example.wp.awesomemmz.APP
import com.example.wp.resource.utils.LogUtils

/**
 * remote service.
 */

class BookManagerService : Service() {

    private val bookList: MutableList<Book> = mutableListOf()

    override fun onCreate() {
        super.onCreate()
        LogUtils.d("----BookManagerService--created..")

        bookList.add(Book("1", "Android xxx"))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtils.d("----BookManagerService--started..")
        return super.onStartCommand(intent, flags, startId)
    }

    private val stub = object : IBookManager.Stub() {
        override fun listBooks(): MutableList<Book> {
            LogUtils.d("----BookManagerService--listBooks: thread, ${Thread.currentThread().name}")//binder线程
            return bookList
        }

        override fun addBook(book: Book?) {
            LogUtils.d("----BookManagerService--addBook: $book")
            if (book != null) bookList.add(book)
            //exception: 不是运行在mainThread.
            Toast.makeText(this@BookManagerService, "add book", Toast.LENGTH_LONG).show()
        }
    }

    override fun onBind(intent: Intent): IBinder {
        LogUtils.d("----BookManagerService--onBind..")
        return stub
    }
}
