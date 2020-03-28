package com.example.wp.awesomemmz.skill

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.wp.awesomemmz.R
import kotlinx.android.synthetic.main.activity_coordinator_behavior3.*

class CoordinatorBehavior3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator_behavior3)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ColorItemListAdapter(this)
        recyclerView.adapter = adapter
    }
}
