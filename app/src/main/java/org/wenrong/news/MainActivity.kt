package org.wenrong.news

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import org.wenrong.news.adapter.ViewPagerAdapter
import org.wenrong.news.base.BaseActivity
import org.wenrong.news.ui.fragments.*
import java.util.*

class MainActivity : BaseActivity() {


    var tabTitles:ArrayList<String>? = null
    var fragments:ArrayList<Fragment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolBar()

        initTitles()

        initFragments()

        initTabLayout()

    }


    private fun initFragments() {

        if(fragments == null){

            fragments = ArrayList<Fragment>();
            fragments?.add(TopNewsFragment());
            fragments?.add(SheHuiNewsFragment());
            fragments?.add(GuoNeiNewsFragment());
            fragments?.add(GuoJiNewsFragment());
            fragments?.add(YuLeNewsFragment());
            fragments?.add(TiYuNewsFragment());
            fragments?.add(JunShiNewsFragment());
            fragments?.add(KeJINewsFragment());
            fragments?.add(CaiJingNewsFragment());
            fragments?.add(ShiShangNewsFragment());

        }


    }


    private fun initTitles() {

        if(tabTitles == null){

            tabTitles = ArrayList<String>()

            tabTitles?.add("头条")
            tabTitles?.add("社会")
            tabTitles?.add("国内")
            tabTitles?.add("国际")
            tabTitles?.add("娱乐")
            tabTitles?.add("体育")
            tabTitles?.add("军事")
            tabTitles?.add("科技")
            tabTitles?.add("财经")
            tabTitles?.add("时尚")

        }

    }

    private fun initToolBar() {

        setSupportActionBar(toolBar)
        toolBar.setTitleTextColor(Color.WHITE)
        toolBar.setTitle("新闻头条")

    }


    private fun initTabLayout() {

        viewPager.adapter = ViewPagerAdapter(supportFragmentManager,fragments,tabTitles)

        for(title in tabTitles!!){

            tabLayout.addTab(tabLayout.newTab().setText(title))

        }

        tabLayout.setupWithViewPager(viewPager)

    }

}
