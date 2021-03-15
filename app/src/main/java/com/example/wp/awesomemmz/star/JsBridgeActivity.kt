package com.example.wp.awesomemmz.star

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.JavascriptInterface
import com.example.wp.awesomemmz.APP
import com.example.wp.awesomemmz.R
import com.example.wp.resource.utils.LogUtils
import com.github.lzyzsd.jsbridge.BridgeHandler
import com.github.lzyzsd.jsbridge.CallBackFunction
import com.github.lzyzsd.jsbridge.DefaultHandler
import kotlinx.android.synthetic.main.activity_js_bridge.*

class JsBridgeActivity : AppCompatActivity() {

    private var function: CallBackFunction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_js_bridge)

        webView.setDefaultHandler(MyHandlerCallback())
        webView.loadUrl("file:///android_asset/html/jsbridge_test.html")

        //注册一个java函数，来实现js回调的handler
        webView.registerHandler("submitFromWeb",
                BridgeHandler { data, function ->
                    LogUtils.d("-----$data")
                    APP.toast(data)
                    //Android返回给JS的消息
                    function.onCallBack("给js的回调信息:1111")
                })

        //打开本地文件
        webView.registerHandler("openFile") { data, function ->
            openFile()
            this@JsBridgeActivity.function = function
        }

//        webView.addJavascriptInterface(object : Any() {
//            @JavascriptInterface
//            fun startFunction() {
//                runOnUiThread { promptMessage("show") }
//            }
//        }, "android")

        //给js发消息
        webView.send("hello")
    }

    //接收js给android发的消息
    inner class MyHandlerCallback : DefaultHandler() {
        override fun handler(data: String?, function: CallBackFunction?) {
//            super.handler(data, function)
            LogUtils.d("-----DefaultHandler--$data")
            APP.toast(data)
            function?.onCallBack("收到JS消息回复。")
        }
    }

    fun callToJs(view: View) {
        webView.callHandler("functionInJs", "android调用Js") {
            LogUtils.d("调用js成功: $it")
            APP.toast("调用js成功: $it")
        }
    }

    private fun openFile() {
        val chooserIntent = Intent(Intent.ACTION_GET_CONTENT);
        chooserIntent.setType("image/*");
        startActivityForResult(chooserIntent, 11);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 11) {
            data?.let {
                val result = it.data
                LogUtils.d("-----${result?.toString()}")
                function?.onCallBack(result?.toString())
            }
        }
    }
}
