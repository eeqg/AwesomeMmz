package com.example.wp.awesomemmz.skill;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.wp.awesomemmz.APP;
import com.example.wp.awesomemmz.R;
import com.example.wp.resource.utils.LogUtils;

public class SchemeTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme_test);

        findViewById(R.id.btnImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("xl://goods:8888/goodsDetail?goodsId=10011002"));
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("wp_awesome://image/imageDetail?id=10011002"));
                    startActivity(intent);
                }catch (Exception e){
                
                }
            }
        });

        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void onPictureSelect(int p) {
                APP.toast("----"+p);
            }
        }, "android");
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // Uri uri = Uri.parse(url);
//                LogUtils.d("-----url = " + url);
//                if (url.contains("wp_awesome")) {
//                    int index = url.indexOf("wp_awesome");
//                    String substring = url.substring(index);
//                    LogUtils.d("-----substring = " + substring);
//                    view.loadUrl(substring);
//                } else {
//                    view.loadUrl(url);
//                }
//                return true;
//            }
//        });
        webView.loadUrl("file:///android_asset/html/scheme_test.html");
    }
}
