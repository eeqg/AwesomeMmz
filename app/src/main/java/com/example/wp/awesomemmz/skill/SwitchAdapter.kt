package com.example.wp.awesomemmz.skill

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.wp.awesomemmz.R
import com.example.wp.awesomemmz.databinding.ItemNormalListBinding

/**
 * Created by wp on 2020/9/8.
 */
class SwitchAdapter(dataList: MutableList<String>) : RecyclerView.Adapter<SwitchAdapter.ViewHolder>() {

    private val mList = dataList

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//                .inflate(R.layout.item_normal_list, parent, false)
        val dataBinding = DataBindingUtil.inflate<ItemNormalListBinding>(LayoutInflater.from(parent.context),
                R.layout.item_normal_list, parent, false)
        return ViewHolder(dataBinding.root)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val dataBinding: ItemNormalListBinding? = DataBindingUtil.findBinding(p0.itemView)
        dataBinding?.tvTitle?.text = mList[p1]
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }
}