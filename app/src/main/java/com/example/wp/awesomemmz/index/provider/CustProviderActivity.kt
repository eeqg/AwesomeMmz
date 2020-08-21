package com.example.wp.awesomemmz.index.provider

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.wp.awesomemmz.R
import com.example.wp.awesomemmz.databinding.ActivityCustProviderBinding

class CustProviderActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityCustProviderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_cust_provider)

        dataBinding.run {
            btnAdd.setOnClickListener {  }


        }
    }

    private fun add(){

    }
}