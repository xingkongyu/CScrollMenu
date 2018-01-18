package com.congdy.cscrollmenu

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.util.AttributeSet
import android.util.SparseArray
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import java.lang.Exception
import java.util.*


/**
 *滑动菜单
 */
class XSlidingTabLayout(context: Context?, attrs: AttributeSet?, defStyle: Int) : HorizontalScrollView(context, attrs, defStyle) {
    var currentPosition: Int = 0
        private set

    private var mTitleOffset: Int

    private var mTabViewLayoutId: Int = 0
    private var mTabViewTextViewId: Int = 0
    private var mDistributeEvenly: Boolean = false
    private var isSeletedBlod: Boolean = false

    private var mViewPager: ViewPager? = null
    private val mContentDescriptions = SparseArray<String>()
    private var mViewPagerPageChangeListener: ViewPager.OnPageChangeListener? = null
    private val mTabStrip: XSlidingTabStrip

    private var menus: ArrayList<XMenuModel>? = ArrayList()
    private var positionOffset: Int = -200


    /**
     * 此方式用于与viewpager解耦，仅仅用于显示菜单
     */
    fun setMenus(menus: ArrayList<XMenuModel>?, l: OnClickPositionBack?) {
        this.menus = menus
        if (menus != null) {
            mTabStrip?.removeAllViews()
            for (i in 0 until menus!!.size) {
                var tabView: View? = addTabs(menus[i].title, i, 0)
                tabView!!.setOnClickListener { p0 ->
                    if (p0 != null) {
                        l?.onClickBack(p0, i)
                        changeTagview(i)
                    }
                }
            }
        }
    }

    fun changeTagview(i: Int) {
        mTabStrip.onViewPagerPageChanged(i, 0f)
        scrollToTab(i, positionOffset)
        for (i in 0 until mTabStrip.childCount) {
            mTabStrip.getChildAt(i).isSelected = true
        }

        val lastView = mTabStrip.getChildAt(currentPosition) as TextView
        setDrawbleLeft(lastView, currentPosition, false)
        setTxtSizeColor(lastView, currentPosition, false)

        val selectedChild = mTabStrip.getChildAt(i) as TextView
        setDrawbleLeft(selectedChild, i, true)
        setTxtSizeColor(selectedChild, i, true)
        currentPosition = i
    }

    /**
     * Allows complete control over the colors drawn in the tab layout. Set with
     */
    interface TabColorizer {

        /**
         * @return return the color of the indicator used when `position` is selected.
         */
        fun getIndicatorColor(position: Int): Int
    }


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {

        // Disable the Scroll Bar
        isHorizontalScrollBarEnabled = false
        // Make sure that the Tab Strips fills this View
        isFillViewport = true

        mTitleOffset = (TITLE_OFFSET_DIPS * resources.displayMetrics.density).toInt()

        mTabStrip = XSlidingTabStrip(context!!)
        addView(mTabStrip, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
    }

    /**
     * 设置正常情况下的菜单颜色
     */
    fun setCustomTabColorizer(tabColorizer: TabColorizer) {
        mTabStrip.setCustomTabColorizer(tabColorizer)
    }


    fun setPositionOffset(offset: Int) {
        TITLE_OFFSET_DIPS = offset
        mTitleOffset = (TITLE_OFFSET_DIPS * resources.displayMetrics.density).toInt()
    }

    /**
     * 设置菜单是否横向铺开展示（适用于菜单数据比较少，不足以铺满整个横屏的情况）
     */
    fun setDistributeEvenly(distributeEvenly: Boolean) {
        mDistributeEvenly = distributeEvenly
    }

    /**
     * 设置选中状态是否粗体显示
     */
    fun setIsSeletedBlod(isSeletedBlod: Boolean) {
        this.isSeletedBlod = isSeletedBlod
    }

    fun setInnerGravity(gravity: Int) {
        mTabStrip.gravity = if (gravity == null || gravity == 0) {
            Gravity.LEFT
        } else {
            gravity
        }
    }

    /**
     * Sets the colors to be used for indicating the selected tab. These colors are treated as a
     * circular array. Providing one color will mean that all tabs are indicated with the same color.
     */
    fun setSelectedIndicatorColors(vararg colors: Int) {
        mTabStrip.setSelectedIndicatorColors(*colors)
    }

    /**
     * required to set any [ViewPager.OnPageChangeListener] through this method. This is so
     * that the layout can update it's scroll position correctly.
     *
     * @see ViewPager.setOnPageChangeListener
     */
    fun setOnPageChangeListener(listener: ViewPager.OnPageChangeListener) {
        mViewPagerPageChangeListener = listener
    }

    /**
     * Set the custom layout to be inflated for the tab views.
     *
     * @param layoutResId Layout id to be inflated
     * @param textViewId id of the [TextView] in the inflated view
     */
    fun setCustomTabView(layoutResId: Int, textViewId: Int) {
        mTabViewLayoutId = layoutResId
        mTabViewTextViewId = textViewId
    }

    /**
     * 设置绑定的viewpager，绑定之后会根据viewpager的页数数据和标题数据进行菜单导航数据的初始化
     */
    fun setViewPager(viewPager: ViewPager?) {
        mTabStrip.removeAllViews()
        mViewPager = viewPager
        this.menus = (viewPager?.adapter as XPagerAdapter).getMenus()
        if (viewPager != null) {
            viewPager.setOnPageChangeListener(InternalViewPagerListener())
            populateTabStrip()
        }
    }

    /**
     * Create a default view to be used for tabs. This is called if a custom tab view is not set via
     * 设置一个默认的滑动菜单的菜单选项的字条目（在没有指定的情况下默认使用此控件）
     * [.setCustomTabView].
     */
    protected fun createDefaultTabView(context: Context): TextView {
        val textView = TextView(context)
        textView.gravity = Gravity.CENTER
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TAB_VIEW_TEXT_SIZE_SP.toFloat())
        textView.typeface = Typeface.DEFAULT
        val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.topMargin = 6
        params.bottomMargin = 6
        textView.layoutParams = params

        val outValue = TypedValue()
        getContext().theme.resolveAttribute(android.R.attr.selectableItemBackground,
                outValue, true)
        textView.setBackgroundResource(outValue.resourceId)
        textView.setAllCaps(true)

        val padding = (TAB_VIEW_PADDING_DIPS * resources.displayMetrics.density).toInt()
        textView.setPadding(padding, padding, padding, padding)

        return textView
    }

    /**
     * 计算并配置相关导航菜单显示
     */
    private fun populateTabStrip() {
        if (mViewPager != null) {
            val adapter = mViewPager!!.adapter
            val tabClickListener = TabClickListener()

            for (i in 0 until adapter.count) {
                var tabView: View? = addTabs(adapter.getPageTitle(i) as String, i, mViewPager!!.currentItem)
                tabView!!.setOnClickListener(tabClickListener)
            }
        }

    }

    /**
     * 导航菜单点击选择之后的回调
     *用与没有绑定viewpager的情况
     */
    interface OnClickPositionBack {
        fun onClickBack(view: View, position: Int)
    }

    /**
     * 添加菜单条目到导航菜单容器里面
     */
    private fun addTabs(menu: String, i: Int, selectedPosition: Int): View? {
        var tabView: View? = null
        var tabTitleView: TextView? = null

        if (mTabViewLayoutId != 0) {
            // If there is a custom tab view layout id set, try and inflate it
            tabView = LayoutInflater.from(context).inflate(mTabViewLayoutId, mTabStrip, false)
            tabTitleView = tabView!!.findViewById<View>(mTabViewTextViewId) as TextView
        }

        if (tabView == null) {
            tabView = createDefaultTabView(context)
        }

        if (tabTitleView == null && TextView::class.java.isInstance(tabView)) {
            tabTitleView = tabView as TextView?
        }

        if (mDistributeEvenly) {
            val lp = tabView.layoutParams as LinearLayout.LayoutParams
            lp.width = 0
            lp.weight = 1f
        }

        tabTitleView!!.text = menu
        setDrawbleLeft(tabTitleView, i, false)
        setTxtSizeColor(tabTitleView, i, false)
        val desc = mContentDescriptions.get(i, null)
        if (desc != null) {
            tabView.contentDescription = desc
        }
        mTabStrip.addView(tabView)

        if (i == selectedPosition) {
            tabView.isSelected = true
            setDrawbleLeft(tabTitleView, i, true)
            setTxtSizeColor(tabTitleView, i, true)
            currentPosition = i
        }
        return tabView
    }

    /**
     * 设置菜单条目字体大小和颜色
     */
    private fun setTxtSizeColor(tabTitleView: TextView, position: Int, b: Boolean) {
        if (menus == null) return
        if (b) {
            tabTitleView.setTextColor(resources.getColor(menus!![position].selected_color))
            tabTitleView.textSize = menus!![position].selectedSize
            if (isSeletedBlod)
                tabTitleView.paint.isFakeBoldText = true
        } else {
            tabTitleView.setTextColor(resources.getColor(menus!![position].normal_color))
            tabTitleView.textSize = menus!![position].normalSize
            if (isSeletedBlod)
                tabTitleView.paint.isFakeBoldText = false
        }
    }

    /**
     * 设置菜单条目左侧图标的显示
     * @param b 是否选中状态
     */
    private fun setDrawbleLeft(tabTitleView: TextView, position: Int, b: Boolean) {
        if (menus!![position].normal_icon != 0 && menus!![position].selected_icon != 0) {
            val drawable = resources.getDrawable(if (b) menus!![position].selected_icon else menus!![position].normal_icon)
            set2Drawble(tabTitleView, drawable, position)
        } else if (!TextUtils.isEmpty(menus!![position].normal_net_icon) && !TextUtils.isEmpty(menus!![position].selected_net_icon)) {
            ImageLoader.get().loadImageByUri(context, if (b) menus!![position].selected_net_icon else menus!![position].normal_net_icon, object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, glideAnimation: GlideAnimation<in Bitmap>) {
                    val drawable = BitmapDrawable(resource)
                    set2Drawble(tabTitleView, drawable, position)
                }

                override fun onLoadFailed(e: Exception?, errorDrawable: Drawable?) {
                    super.onLoadFailed(e, errorDrawable)
                    val drawable = resources.getDrawable(R.drawable.default_pic)
                    set2Drawble(tabTitleView, drawable, position)
                }
            })
        }
    }

    /**
     * 将图片添加到textview上
     */
    private fun set2Drawble(tabTitleView: TextView, drawable: Drawable, position: Int) {
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, 40, ((drawable.minimumHeight.toFloat() / drawable.minimumWidth) * 40).toInt())
//        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        when (menus!![position].drawable_position) {
            DRAWABLE_LEFT -> tabTitleView.setCompoundDrawables(drawable, null, null, null)
            DRAWABLE_TOP -> tabTitleView.setCompoundDrawables(null, drawable, null, null)
            DRAWABLE_RIGHT -> tabTitleView.setCompoundDrawables(null, null, drawable, null)
            DRAWABLE_BOTTOM -> tabTitleView.setCompoundDrawables(null, null, null, drawable)
            else -> tabTitleView.setCompoundDrawables(drawable, null, null, null)
        }
        tabTitleView.compoundDrawablePadding = (5 * resources.displayMetrics.density).toInt()
    }

    /**
     * 设置当前选中菜单
     */
    fun setCurrent(position: Int) {
        mViewPager!!.currentItem = position
    }

    /**
     * 设置内容描述
     */
    fun setContentDescription(i: Int, desc: String) {
        mContentDescriptions.put(i, desc)
    }

    /**
     * 控件加到window时的回调
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (mViewPager != null) {
            scrollToTab(mViewPager!!.currentItem, positionOffset)
        }
    }

    /**
     * 菜单条目的切换操作
     */
    private fun scrollToTab(tabIndex: Int, positionOffset: Int) {
        val tabStripChildCount = mTabStrip.childCount
        if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
            return
        }

        val selectedChild = mTabStrip.getChildAt(tabIndex)
        if (selectedChild != null) {
            var targetScrollX = selectedChild.left + positionOffset

            if (tabIndex > 0 || positionOffset > 0) {
                // If we're not at the first child and are mid-scroll, make sure we obey the offset
                targetScrollX -= mTitleOffset
            }

            smoothScrollTo(targetScrollX, 0)
        }

    }

    /**
     * 导航菜单与viewpager的联动效果
     */
    private inner class InternalViewPagerListener : ViewPager.OnPageChangeListener {
        private var mScrollState: Int = 0

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            val tabStripChildCount = mTabStrip.childCount
            if (tabStripChildCount == 0 || position < 0 || position >= tabStripChildCount) return

            mTabStrip.onViewPagerPageChanged(position, positionOffset)

            val selectedTitle = mTabStrip.getChildAt(position)
            val extraOffset = if (selectedTitle != null)
                (positionOffset * selectedTitle.width).toInt()
            else
                0
            scrollToTab(position, extraOffset)

            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener!!.onPageScrolled(position, positionOffset,
                        positionOffsetPixels)
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
            mScrollState = state
            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener!!.onPageScrollStateChanged(state)
            }
        }

        override fun onPageSelected(position: Int) {
            if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                mTabStrip.onViewPagerPageChanged(position, 0f)
                scrollToTab(position, positionOffset)
            }
            for (i in 0 until mTabStrip.childCount) {
                mTabStrip.getChildAt(i).isSelected = position == i
            }
            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener!!.onPageSelected(position)
            }

            val lastView = mTabStrip.getChildAt(currentPosition) as TextView
            setDrawbleLeft(lastView, currentPosition, false)
            setTxtSizeColor(lastView, currentPosition, false)


            val selectedChild = mTabStrip.getChildAt(position) as TextView
            setDrawbleLeft(selectedChild, position, true)
            setTxtSizeColor(selectedChild, position, true)
            currentPosition = position
        }

    }

    /**
     * 导航菜单条目点击事件
     * 在配置了viewpager之后的关联点击事件
     * 如果是自行配置的菜单数据，相关点击事件会进行回调处理
     */
    private inner class TabClickListener : View.OnClickListener {
        override fun onClick(v: View) {
            for (i in 0 until mTabStrip.childCount) {
                if (v === mTabStrip.getChildAt(i)) {
                    if (i != 0) {
                        mViewPager!!.currentItem = i
                    } else {
                        mViewPager!!.currentItem = i
                    }
                    return
                }
            }
        }
    }

    companion object {

        /**标题栏偏移量*/
        private var TITLE_OFFSET_DIPS = 100
        /**标题padding量*/
        private val TAB_VIEW_PADDING_DIPS = 8
        /**标题字体大小*/
        private val TAB_VIEW_TEXT_SIZE_SP = 13
        val DRAWABLE_LEFT = 0
        val DRAWABLE_TOP = 1
        val DRAWABLE_RIGHT = 2
        val DRAWABLE_BOTTOM = 3
    }


}
