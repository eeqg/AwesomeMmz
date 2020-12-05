package com.example.wp.awesomemmz.skill

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wp.awesomemmz.R
import com.example.wp.awesomemmz.common.GlideImageLoader
import com.example.wp.awesomemmz.databinding.ItemGridListBinding
import com.example.wp.awesomemmz.databinding.ItemNormalListBinding
import com.example.wp.resource.utils.LogUtils

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

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        LogUtils.d("-----onCreateViewHolder: ")
        return if (viewType == 0) {
            val dataBinding = DataBindingUtil.inflate<ItemNormalListBinding>(LayoutInflater.from(parent.context),
                    R.layout.item_normal_list, parent, false)
            ViewHolder(dataBinding.root)
        } else {
            val dataBinding = DataBindingUtil.inflate<ItemGridListBinding>(LayoutInflater.from(parent.context),
                    R.layout.item_grid_list, parent, false)
            ViewHolder(dataBinding.root)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    private val thumb4 = "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-03_12-52-08.jpg"

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        LogUtils.d("-----onBindViewHolder: position = $p1")
        val dataBinding: ViewDataBinding? = DataBindingUtil.findBinding(p0.itemView)
        if (dataBinding is ItemNormalListBinding) {
            GlideImageLoader.getInstance().load(dataBinding.ivPicture, thumb4)
            dataBinding.tvTitle.text = mList[p1]
        } else if (dataBinding is ItemGridListBinding) {
            GlideImageLoader.getInstance().load(dataBinding.ivPicture, thumb4)
            dataBinding.tvTitle.text = mList[p1]
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }
}