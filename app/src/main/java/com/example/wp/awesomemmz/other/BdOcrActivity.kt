package com.example.wp.awesomemmz.other

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.example.wp.awesomemmz.R
import com.example.wp.awesomemmz.common.GlideImageLoader
import com.example.wp.awesomemmz.databinding.ActivityBdOcrBinding
import com.example.wp.resource.utils.LogUtil
import kotlin.concurrent.thread

class BdOcrActivity : AppCompatActivity() {
    private val BAIDU_APP_KEY = "7I5lxfxrxYoiDbz9S3GK1Rb9"
    private val BAIDU_APP_SECRET = "GhZeMVbUjG0g3ew1djdHrVoam0rjxGTn"

    private val imageUrl = "https://img.alicdn.com/bao/uploaded/i2/2597231641/O1CN01KsXvLX1NzcWEIpPEv_!!0-item_pic.jpg"

    private lateinit var dataBinding: ActivityBdOcrBinding
    private var accessToken: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_bd_ocr)

        getAccessToken()

        dataBinding.run {
            GlideImageLoader.getInstance().load(ivPicture, imageUrl)
            btnWeb.setOnClickListener { recognizeWebPic() }
            btnNum.setOnClickListener { recognizeNumbers() }
        }
    }

    private fun getAccessToken() {
        thread {
            accessToken = BdOcrUtil.getAuth(BAIDU_APP_KEY, BAIDU_APP_SECRET)
            LogUtil.d("-----accessToken = $accessToken")
        }
    }

    private fun recognizeWebPic() {
        thread {
            val url = "https://aip.baidubce.com/rest/2.0/ocr/v1/webimage"
            val str = BdOcrUtil.recognize(accessToken, url, null, imageUrl)
            LogUtil.d("-----str = $str")
            runOnUiThread { dataBinding.tvShow.text = str }
        }
    }

    private fun recognizeNumbers(): String? {
        val url = "https://aip.baidubce.com/rest/2.0/ocr/v1/numbers"
        return BdOcrUtil.recognize(accessToken, url, null, "")
    }
}
