package com.example.wp.awesomemmz.skill

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.wp.awesomemmz.APP
import com.example.wp.awesomemmz.R
import com.example.wp.resource.utils.LogUtils

class SchemeTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scheme_test)
        findViewById<View>(R.id.btnImage).setOnClickListener {
            try {
                // Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("xl://goods:8888/goodsDetail?goodsId=10011002"));
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("wp_awesome://image/imageDetail?id=10011002")
                )
                startActivity(intent)
            } catch (e: Exception) {
            }
        }
        val webView = findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(object : Any() {
            @JavascriptInterface
            fun onPictureSelect(p: Int) {
                APP.toast("----$p")
            }
        }, "android")
        webView.webViewClient = object : WebViewClient() {
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                // Uri uri = Uri.parse(url);
                LogUtils.d("-----url = $url")
//                if (url.contains("wp_awesome")) {
//                    val index = url.indexOf("wp_awesome")
//                    val substring = url.substring(index)
//                    LogUtils.d("-----substring = $substring")
//                    view.loadUrl(substring)
//                    return true //true: 拦截当前url; false: 不拦截, 当前webview自己加载处理url.
//                }
                if (dealUrl(url)) return true//true: 拦截当前url; false: 不拦截, 当前webview自己加载处理url.
                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
        webView.loadUrl("file:///android_asset/html/scheme_test.html")
    }

    private fun dealUrl(url: String): Boolean {
        try {
            if (url.contains("wp_awesome")) {
                val index = url.indexOf("wp_awesome")
                val substring = url.substring(index)
                LogUtils.d("-----substring = $substring")

                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(substring)
                intent.setPackage(packageName)
                startActivity(intent)
                return true
            }
        } catch (e: Exception) {
            //showToast(R.string.tip_error_version)
            Toast.makeText(this, "error...", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
        return false
    }
}