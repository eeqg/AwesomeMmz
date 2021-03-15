package com.example.wp.awesomemmz.skill

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.wp.awesomemmz.R
import kotlinx.android.synthetic.main.activity_bb.*

class BbActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bb)
        Log.d("BbActivity-----", "onCreate")

        btnB2a.setOnClickListener {
            startActivity(Intent(this, AaActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("BbActivity-----", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("BbActivity-----", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("BbActivity-----", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("BbActivity-----", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("BbActivity-----", "onDestroy")
    }
}