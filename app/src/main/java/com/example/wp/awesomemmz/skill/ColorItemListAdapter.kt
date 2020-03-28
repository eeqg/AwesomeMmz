package com.example.wp.awesomemmz.skill

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wp.awesomemmz.R
import com.example.wp.resource.utils.ColorUtil

/**
 * Created by wp on 2020/3/28.
 */
class ColorItemListAdapter(context: Context) : RecyclerView.Adapter<ColorItemListAdapter.ItemViewHolder>() {

    private var mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view = mInflater.inflate(R.layout.item_color_list, p0, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: ItemViewHolder, p1: Int) {
        holder.view.setBackgroundColor(ColorUtil.generateBeautifulColor())
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var view: View = itemView.findViewById(R.id.view)
    }
}