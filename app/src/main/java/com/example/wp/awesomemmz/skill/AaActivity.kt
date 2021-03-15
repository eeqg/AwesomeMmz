package com.example.wp.awesomemmz.skill

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.wp.awesomemmz.R
import kotlinx.android.synthetic.main.activity_aa.*
import java.text.SimpleDateFormat
import java.util.*

class AaActivity : AppCompatActivity() {
    private val displayBuffer = StringBuilder("")
    private val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.CHINA)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aa)

        btnA2b.setOnClickListener {
            startActivity(Intent(this, BbActivity::class.java))
        }

        display("onCreate")
    }

    override fun onRestart() {
        super.onRestart()
        display("onRestart")
    }

    override fun onStart() {
        super.onStart()
        display("onStart")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        display("onNewIntent") // onStart -> onNewIntent
    }

    override fun onResume() {
        super.onResume()
        display("onResume")
    }

    override fun onPause() {
        super.onPause()
        display("onPause")
    }

    override fun onStop() {
        super.onStop()
        display("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        display("onDestroy")
    }

    private fun display(text: String) {
        Log.d("AaActivity-----", text)
        displayBuffer.append(text).append("--").append(dateFormat.format(Date())).append("\n")
        tvDisplay.text = displayBuffer.toString()
    }
}