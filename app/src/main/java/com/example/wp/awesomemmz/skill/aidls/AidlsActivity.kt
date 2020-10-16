package com.example.wp.awesomemmz.skill.aidls

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.wp.awesomemmz.R
import com.example.wp.awesomemmz.book.BookManagerService
import kotlinx.android.synthetic.main.activity_aidls.*

class AidlsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aidls)

        btnStartService.setOnClickListener {
            startRemoteService()
        }
        btnAddBook.setOnClickListener { }
        btnListBook.setOnClickListener { }
    }

    private fun startRemoteService() {
        startService(Intent(this, BookManagerService::class.java))
    }

    private fun addBook() {}

    private fun listBook() {}
}