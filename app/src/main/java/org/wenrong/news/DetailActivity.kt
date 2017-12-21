package org.wenrong.news

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private var url: String? = null
    private var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initData()
        tv_news_title.setText(title)

        webView.loadUrl(url)
        iv_back.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {

                finish()

            }

        })


    }

    private fun initData() {

        url = intent.getStringExtra("url")
        title = intent.getStringExtra("title")

    }
}
