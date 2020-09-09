package com.example.wp.awesomemmz.skill

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.example.wp.awesomemmz.R
import com.example.wp.awesomemmz.databinding.ActivityRecyclerSwitchBinding
import com.example.wp.resource.widget.SpaceItemDecoration
import java.util.*

class RecyclerSwitchActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityRecyclerSwitchBinding
    private val dataList = mutableListOf<String>()

    private var adapter: SwitchAdapter? = null
    private var linearManager: LinearLayoutManager? = null
    private var gridManager: GridLayoutManager? = null
    private var linearItemDecoration: SpaceItemDecoration? = null
    private var gridItemDecoration: SpaceItemDecoration? = null

    init {
        initData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_recycler_switch)

        adapter = SwitchAdapter(dataList)
        linearManager = LinearLayoutManager(this@RecyclerSwitchActivity)
        gridManager = GridLayoutManager(this@RecyclerSwitchActivity, 2)
        linearItemDecoration = SpaceItemDecoration(this, 12, SpaceItemDecoration.VERTICAL, true)
        gridItemDecoration = SpaceItemDecoration(this, 12, SpaceItemDecoration.GRID, false)
        dataBinding.run {
            recyclerView.addItemDecoration(linearItemDecoration!!)
            recyclerView.layoutManager = linearManager
            recyclerView.adapter = adapter

            ivSwitch.setOnClickListener {
                changeMode()
            }
        }
    }

    private fun changeMode() {
        dataBinding.run {
            var firstPosition = 0
            val layoutManager = recyclerView.layoutManager
            if (layoutManager == linearManager) {
                firstPosition = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                mode = 1
                adapter!!.setViewType(1)
                gridManager!!.scrollToPositionWithOffset(firstPosition, -30)
                recyclerView.layoutManager = gridManager
                recyclerView.removeItemDecoration(linearItemDecoration!!)
                recyclerView.addItemDecoration(gridItemDecoration!!)
            } else if (layoutManager == gridManager) {
                firstPosition = (layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                mode = 0
                adapter!!.setViewType(0)
                linearManager!!.scrollToPositionWithOffset(firstPosition, -30)
                recyclerView.layoutManager = linearManager
                recyclerView.removeItemDecoration(gridItemDecoration!!)
                recyclerView.addItemDecoration(linearItemDecoration!!)
            }

            //recyclerView.scrollToPosition(firstPosition)
            adapter!!.notifyDataSetChanged()
        }
    }

    private fun initData() {
        for (i in 1..80) {
            dataList.add(String.format(Locale.CHINA, "item %02d", i))
        }
    }
}