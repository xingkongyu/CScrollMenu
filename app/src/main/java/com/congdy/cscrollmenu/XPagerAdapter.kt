package com.congdy.cscrollmenu

import android.os.Parcelable
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View

import java.util.ArrayList

/**
 * viewpager的适配器
 *
 * @author 陈聪
 * @time 2018/1/17:下午3:55
 */
class XPagerAdapter : PagerAdapter {

    var titles: ArrayList<XMenuModel>? = null
    /** 装有内容页面的list  */
    var mListViews: List<View>

    constructor(mListViews: ArrayList<View>, menuBeas: ArrayList<XMenuModel>) {
        this.mListViews = mListViews// 接受list
        this.titles = menuBeas// 接受list
    }

    constructor(mListViews: List<View>) {
        this.mListViews = mListViews// 接受list
    }

    override fun destroyItem(arg0: View?, arg1: Int, arg2: Any?) {
        (arg0 as ViewPager).removeView(mListViews[arg1])// 移除不需要的页面
    }

    override fun finishUpdate(arg0: View?) {}

    override fun getCount(): Int {
        return mListViews.size// 页面的数量
    }

    override fun instantiateItem(arg0: View?, arg1: Int): Any {
        (arg0 as ViewPager).addView(mListViews[arg1], 0)// 添加页面
        return mListViews[arg1]
    }

    override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
        return arg0 === arg1
    }

    override fun restoreState(arg0: Parcelable?, arg1: ClassLoader?) {}

    override fun saveState(): Parcelable? {
        return null
    }

    override fun startUpdate(arg0: View?) {}
    override fun getPageTitle(position: Int): CharSequence {
        return if (titles == null) "" else titles!![position].title
    }

    fun getMenus():ArrayList<XMenuModel>?{
        return titles
    }
}
