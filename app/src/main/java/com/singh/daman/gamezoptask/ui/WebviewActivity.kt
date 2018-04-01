package com.singh.daman.gamezoptask.ui

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.webkit.WebView
import com.singh.daman.gamezoptask.R
import android.webkit.WebViewClient
import android.widget.Toast


class WebviewActivity : AppCompatActivity() {
    companion object {
        val intentKey : String = "INTENT_KEY"
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        val url = intent.extras.get(intentKey)
        val webView = findViewById<WebView>(R.id.webview)
        webView.webViewClient = MyBrowser()
        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptEnabled = true
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webView.loadUrl("file:///" + url)
        print("file:///" + url)
        Toast.makeText(this, "This is webview", Toast.LENGTH_LONG).show()
    }

    private inner class MyBrowser : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }
}
