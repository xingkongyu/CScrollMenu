package com.congdy.cscrollmenu

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_show.*
import java.util.ArrayList

/**
 * 请描述使用该类使用方法！！！
 *
 * @author 陈聪 2018-01-17 15:56
 */
class ShowActivity : AppCompatActivity() {
    companion object {
        var isRelevance: Boolean = true
        var isFill: Boolean = true
        var isFixation: Boolean = true
        var isIconShow: Boolean = false
        var isUseNetpic: Boolean = false
        var leftOffset: Int = -100
        var picPosition: Int = 0
    }

    private val mens = ArrayList<XMenuModel>()
    private val showViews = ArrayList<View>()
    private var normal_icon: Int = R.drawable.normal
    private var selected_icon: Int = R.drawable.selected

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        if (!isIconShow) {
            normal_icon = 0
            selected_icon = 0
        }
        mens.add(XMenuModel("家具", 0, normal_icon, selected_icon, "", "", 15f, 15f, R.color.black_66, R.color.red_ff5303))
        mens.add(XMenuModel("数码", 1, normal_icon, selected_icon, "", "", 15f, 15f, R.color.black_66, R.color.blue_003399))
        mens.add(XMenuModel("书籍", 2, normal_icon, selected_icon, "", "", 15f, 15f, R.color.black_66, R.color.yellow_ffcc00))
        mens.add(XMenuModel("推荐尚品", 3, normal_icon, selected_icon, "", "", 15f, 15f, R.color.black_66, R.color.green_006600))
        mens.add(XMenuModel("猜你喜欢", 4, normal_icon, selected_icon, "", "", 15f, 15f, R.color.black_66, R.color.green_99cc33))
        mens.add(XMenuModel("我的选择", 5, normal_icon, selected_icon, "", "", 15f, 15f, R.color.black_66, R.color.red_ff6666))
        for (i in mens.indices) {
            var view = View.inflate(this, R.layout.view_content, null)
            var txt = view.findViewById<TextView>(R.id.txt)
            txt.text = mens[i].title
            showViews.add(view)
        }
        initCategoryMenu()
        setCategorys()
    }

    /**
     * 初始化分类菜单显示
     */
    private fun initCategoryMenu() {
        xsliding.setDistributeEvenly(isFill)
        xsliding.isFillViewport = isFixation
        xsliding.setIsSeletedBlod(true)
        xsliding.setCustomTabColorizer(object : XSlidingTabLayout.TabColorizer {
            override fun getIndicatorColor(position: Int): Int {
                return resources.getColor(mens[position].selected_color)
            }
        })
    }

    /**
     * 获取到分类菜单之后设置数据
     */
    private fun setCategorys() {
        if (isRelevance) {//关联viewpager
            vpager.adapter = XPagerAdapter(showViews, mens)
            vpager.addOnPageChangeListener(MyOnPageChangeListener())// 设置viewpager的监听事件
            xsliding.setViewPager(vpager)
        } else {
            if (mens != null && mens.size > 0) {
                xsliding.setMenus(mens, object : XSlidingTabLayout.OnClickPositionBack {
                    override fun onClickBack(view: View, position: Int) {
                    }
                })
            }
        }
    }

    /**
     * 用于监听页面的滑动
     */
    inner class MyOnPageChangeListener : ViewPager.OnPageChangeListener {

        override fun onPageSelected(arg0: Int) {
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            if (position < mens.size - 1)
                ll_content.setBackgroundColor(UIUtils.blendColors(resources.getColor(mens[position + 1].selected_color), resources.getColor(mens[position].selected_color), positionOffset))
        }

        override fun onPageScrollStateChanged(arg0: Int) {}
    }

}