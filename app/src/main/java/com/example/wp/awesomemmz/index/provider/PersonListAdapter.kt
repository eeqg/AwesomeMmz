package com.example.wp.awesomemmz.index.provider

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wp.awesomemmz.R
import com.example.wp.awesomemmz.databinding.ItemPersonListBinding

/**
 * Created by wp on 2020/8/21.
 */
class PersonListAdapter : RecyclerView.Adapter<PersonListAdapter.ViewHolder>() {
    private var context: Context? = null
    private var dataList: List<PersonInfo>? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val dataBinding: ItemPersonListBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_person_list, p0,
                false)
        return ViewHolder(dataBinding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataBinding = DataBindingUtil.getBinding<ItemPersonListBinding>(holder.itemView)
        dataBinding?.let {
            personInfo = getItem(position)
        }
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

    private fun getItem(position: Int): PersonInfo? {
        return dataList?.get(position)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
}