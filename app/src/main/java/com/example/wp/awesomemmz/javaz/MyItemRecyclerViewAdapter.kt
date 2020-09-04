package com.example.wp.awesomemmz.javaz

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.wp.awesomemmz.R
import com.example.wp.awesomemmz.index.bean.ClassInfoBean

import com.example.wp.awesomemmz.javaz.dummy.DummyContent.DummyItem
import java.util.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(private val values: List<ClassInfoBean>)
    : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        itemClickListener = listener
    }

    fun getItem(position: Int): ClassInfoBean {
        return values[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_index_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = String.format(Locale.CHINA, "%02d", position + 1)
        holder.contentView.text = item.title

        itemClickListener?.let { listener ->
            holder.itemView.setOnClickListener { listener.onItemClick(it, position) }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.tvIndex)
        val contentView: TextView = view.findViewById(R.id.tvTitle)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}