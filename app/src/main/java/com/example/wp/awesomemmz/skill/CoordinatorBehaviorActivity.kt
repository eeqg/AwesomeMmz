package com.example.wp.awesomemmz.skill

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import com.example.wp.awesomemmz.R
import com.example.wp.resource.utils.LaunchUtil
import kotlinx.android.synthetic.main.activity_coordinator_behavior.*

class CoordinatorBehaviorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator_behavior)

        btn.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_MOVE) {
                v.x = event.rawX - v.width / 2;
                v.y = event.rawY - v.height / 2;
            }
            true
        }
        btn2.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_MOVE) {
                v.x = event.rawX - v.width / 2;
                v.y = event.rawY - v.height / 2;
            }
            true
        }

        btnStyle1.setOnClickListener { LaunchUtil.launchActivity(this, CoordinatorBehavior2Activity::class.java) }
        btnStyle2.setOnClickListener { LaunchUtil.launchActivity(this, CoordinatorBehavior3Activity::class.java) }
    }
}
