package com.example.wp.awesomemmz.skill

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wp.awesomemmz.R
import com.example.wp.awesomemmz.databinding.ItemGridListBinding
import com.example.wp.awesomemmz.databinding.ItemNormalListBinding

/**
 * Created by wp on 2020/9/8.
 */
class SwitchAdapter(dataList: MutableList<String>) : RecyclerView.Adapter<SwitchAdapter.ViewHolder>() {
    private val mList = dataList
    private var viewType = 0

    fun setViewType(type: Int) {
        this.viewType = type
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        if (viewType == 0) {
            val dataBinding = DataBindingUtil.inflate<ItemNormalListBinding>(LayoutInflater.from(parent.context),
                    R.layout.item_normal_list, parent, false)
            return ViewHolder(dataBinding.root)
        } else {
            val dataBinding = DataBindingUtil.inflate<ItemGridListBinding>(LayoutInflater.from(parent.context),
                    R.layout.item_grid_list, parent, false)
            return ViewHolder(dataBinding.root)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val dataBinding: ViewDataBinding? = DataBindingUtil.findBinding(p0.itemView)
        if (dataBinding is ItemNormalListBinding) {
            (dataBinding as ItemNormalListBinding).tvTitle.text = mList[p1]
        } else if (dataBinding is ItemGridListBinding) {
            (dataBinding as ItemGridListBinding).tvTitle.text = mList[p1]
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }
}