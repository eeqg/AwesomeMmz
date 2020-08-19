package com.example.wp.awesomemmz.other

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wp.awesomemmz.R
import com.example.wp.awesomemmz.databinding.FragmentOtherBinding
import com.example.wp.awesomemmz.index.IndexAdapter
import com.example.wp.awesomemmz.index.bean.ClassInfoBean
import com.example.wp.resource.utils.LaunchUtil
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter

/**
 * Created by wp on 2020/6/30.
 */
class OtherFragment : Fragment() {
    private lateinit var dataBinding: FragmentOtherBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_other, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.recyclerView.setLayoutManager(LinearLayoutManager(context))
        val indexAdapter = IndexAdapter(context)
        dataBinding.recyclerView.adapter = indexAdapter

        indexAdapter.addAll(getData())
        indexAdapter.notifyDataSetChanged()

        indexAdapter.setOnItemClickListener(RecyclerArrayAdapter.OnItemClickListener { position ->
            val className = indexAdapter.getItem(position).classPath
            if (TextUtils.isEmpty(className)) {
                return@OnItemClickListener
            }
            try {
                val activityClass = Class.forName(className)
                LaunchUtil.launchActivity(context, activityClass as Class<out Activity>?)
                // startActivity(new Intent(getContext(), activityClass));
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }
        })
    }

    private fun getData(): ArrayList<ClassInfoBean> {
        return arrayListOf(
                ClassInfoBean("百度OCR", BdOcrActivity::class.java.name)
        )
    }
}