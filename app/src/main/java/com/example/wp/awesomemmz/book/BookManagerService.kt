package com.example.wp.awesomemmz.book

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.wp.resource.utils.LogUtils

/**
 * remote service.
 */

class BookManagerService : Service() {

    private val bookList: MutableList<Book> = mutableListOf()

    override fun onCreate() {
        super.onCreate()
        LogUtils.d("----BookManagerService--create..")

        bookList.add(Book("1", "Android xxx"))
    }

    private val stub = object : IBookManager.Stub() {
        override fun listBooks(): MutableList<Book> {
            return bookList
        }

        override fun addBook(book: Book?) {
            if (book != null) bookList.add(book)
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return stub
    }
}
