package com.example.wp.awesomemmz.skill

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.example.wp.awesomemmz.R
import com.example.wp.awesomemmz.databinding.ActivityRecyclerSwitchBinding
import java.util.*

class RecyclerSwitchActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityRecyclerSwitchBinding
    private val dataList = mutableListOf<String>()

    private var adapter: SwitchAdapter? = null

    init {
        initData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_recycler_switch)

        dataBinding.run {
            ivSwitch.setOnClickListener {
                if(mode != 0) setLinearMode() else setGridMode()
            }
        }
        setLinearMode()
    }

    private fun setLinearMode() {
        dataBinding.run {
            mode = 0
            recyclerView.layoutManager = LinearLayoutManager(this@RecyclerSwitchActivity)
            adapter = SwitchAdapter(dataList)
            recyclerView.adapter = adapter
        }
    }

    private fun setGridMode() {
        dataBinding.run {
            mode = 1
            recyclerView.layoutManager = GridLayoutManager(this@RecyclerSwitchActivity, 2)
            adapter = SwitchAdapter(dataList)
            recyclerView.adapter = adapter
        }
    }

    private fun initData() {
        for (i in 1..30) {
            dataList.add(String.format(Locale.CHINA, "item %02d", i))
        }
    }
}