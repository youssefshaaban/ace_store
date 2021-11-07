package com.example.mvvm_template.ui.component.main.web_view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.mvvm_template.R
import com.example.mvvm_template.core.common.BaseActivity
import com.example.mvvm_template.databinding.ActivityWebViewBinding

class WebViewActivity : BaseActivity<ActivityWebViewBinding>() {
    val type:Int by lazy {
        intent.getIntExtra("type_load",1)
    }
    val data:String? by lazy {
        intent.getStringExtra("data")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewDataBinding().appBar.back.setOnClickListener { onBackPressed() }
        getViewDataBinding().appBar.title.text=intent.getStringExtra("title")
        getViewDataBinding().webView.webViewClient=MyBrowser()
        if (type==1){
            getViewDataBinding().webView.loadUrl(data!!)
        }else{
            getViewDataBinding().webView.loadData(data!! ,"text/html", "UTF-8")
        }

    }

    private class MyBrowser : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }
    override fun getLayoutId(): Int {
        return R.layout.activity_web_view
    }


    companion object{
        fun getIntent(context: Context):Intent= Intent(context, WebViewActivity::class.java)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && getViewDataBinding().webView.canGoBack()) {
            getViewDataBinding().webView.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event)
    }
}