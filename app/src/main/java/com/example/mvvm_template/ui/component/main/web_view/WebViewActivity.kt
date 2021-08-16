package com.example.mvvm_template.ui.component.main.web_view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.mvvm_template.R
import com.example.mvvm_template.databinding.ActivityWebViewBinding
import com.example.mvvm_template.ui.base.BaseActivity


class WebViewActivity : BaseActivity<ActivityWebViewBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewDataBinding().appBar.back.setOnClickListener { onBackPressed() }
        getViewDataBinding().appBar.title.text=intent.getStringExtra("title")
        getViewDataBinding().webView.webViewClient=MyBrowser()
        getViewDataBinding().webView.loadUrl(intent.getStringExtra("url"))
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

    override fun observeViewModel() {

    }

    companion object{
        fun getIntent(context: Context):Intent= Intent(context, WebViewActivity::class.java)
    }
}