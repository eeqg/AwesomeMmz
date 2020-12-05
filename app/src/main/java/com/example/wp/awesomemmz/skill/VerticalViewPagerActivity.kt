package com.example.wp.awesomemmz.skill

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.wp.awesomemmz.R
import com.example.wp.resource.widget.VerticalViewPager
import kotlinx.android.synthetic.main.activity_verticall_view_pager.*

class VerticalViewPagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verticall_view_pager)

        initView()
    }

    private fun initView() {
        val urls = resources.getStringArray(R.array.url)
        val images = listOf(*urls)

        //val viewPager1: VerticalViewPager = findViewById(R.id.viewPager1)
        viewPager1.offscreenPageLimit = 5
        viewPager1.adapter = PicturePagerAdapter(images)
    }
}