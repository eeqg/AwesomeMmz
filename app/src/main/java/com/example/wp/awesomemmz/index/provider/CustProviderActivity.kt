package com.example.wp.awesomemmz.index.provider

import android.database.ContentObserver
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.example.wp.awesomemmz.APP
import com.example.wp.awesomemmz.R
import com.example.wp.awesomemmz.databinding.ActivityCustProviderBinding
import com.example.wp.resource.common.RxTimerHelper
import com.example.wp.resource.utils.LogUtils

class CustProviderActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityCustProviderBinding
    private lateinit var cpAdapter: CustProviderAdapter
    private lateinit var listAdapter: PersonListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_cust_provider)

        cpAdapter = CustProviderAdapter(this)
        dataBinding.run {
            btnAdd.setOnClickListener { add() }
            btnGet.setOnClickListener { queryBy() }
            btnDel.setOnClickListener { deleteBy() }

            recyclerView.layoutManager = LinearLayoutManager(this@CustProviderActivity)
            listAdapter = PersonListAdapter(this@CustProviderActivity)
            recyclerView.adapter = listAdapter
        }

        getDataList()

        contentResolver.registerContentObserver(CustProviderAdapter.CONTENT_URI,
                true,
                object : ContentObserver(Handler()) {
                    override fun onChange(selfChange: Boolean) {
                        LogUtils.d("-----onChange()---$selfChange")
                        getDataList()
                    }
                })
    }

    private fun getDataList() {
        RxTimerHelper.get(this).doInBackground(object : RxTimerHelper.Callback<List<PersonInfo>> {
            override fun onStart() {

            }

            override fun doInBackground(): List<PersonInfo> {
                return cpAdapter.getAllPersons()
            }

            override fun onResult(result: List<PersonInfo>?) {
                listAdapter.setDataList(result)
                listAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun add() {
        val id = dataBinding.etId.text.toString()
        val name = dataBinding.etName.text.toString()
        val sex = dataBinding.etSex.text.toString()
        val age = dataBinding.etAge.text.toString()
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(sex) || TextUtils.isEmpty(age)) {
            APP.toast("信息填写不完整")
            return
        }
        if (TextUtils.isEmpty(id))
            cpAdapter.insertPerson(PersonInfo(-1, name, sex, age))
        else
            cpAdapter.updatePerson(PersonInfo(id.toLong(), name, sex, age))
    }

    private fun queryBy() {
        var info: PersonInfo? = null
        val ids = dataBinding.etIds.text.toString()
        if (TextUtils.isEmpty(ids)) return
        when (dataBinding.rg.checkedRadioButtonId) {
            R.id.rbId -> {
                info = cpAdapter.getPersonById(ids.toLong())
            }
            R.id.rbPos -> {
                info = cpAdapter.getPersonByPosition(ids.toInt())
            }
        }

        APP.toast("---${info}")
    }

    private fun deleteBy() {
        var result: Boolean? = null
        val ids = dataBinding.etIds.text.toString()
        if (TextUtils.isEmpty(ids)) return
        when (dataBinding.rg.checkedRadioButtonId) {
            R.id.rbId -> {
                result = cpAdapter.deletePersonById(ids.toLong())
            }
            R.id.rbPos -> {
                //result = cpAdapter.deletePersonByPos(ids.toLong())
            }
        }

        APP.toast("---${result}")
    }
}