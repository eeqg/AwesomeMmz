package com.example.wp.awesomemmz.javaz

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wp.awesomemmz.R
import com.example.wp.awesomemmz.javaz.dummy.DummyContent
import com.example.wp.resource.utils.LaunchUtil

/**
 * A fragment representing a list of Items.
 */
class JavazFragment : Fragment() {

    private var columnCount = 1

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
                JavazFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_javaz, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyItemRecyclerViewAdapter(DummyContent.ITEMS).apply {
                    setOnItemClickListener(object : MyItemRecyclerViewAdapter.OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {
                            viewDetail(getItem(position).classPath)
                        }
                    })
                }
            }
        }
        return view
    }

    private fun viewDetail(className: String) {
        if (TextUtils.isEmpty(className)) {
            return
        }
        try {
            val activityClass = Class.forName(className)
            LaunchUtil.launchActivity(context, activityClass as Class<out Activity>?)
            // startActivity(new Intent(getContext(), activityClass));
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }
}