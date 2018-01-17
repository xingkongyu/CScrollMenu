package com.congdy.cscrollmenu

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.congdy.cscrollmenu.ShowActivity.Companion.isFill
import com.congdy.cscrollmenu.ShowActivity.Companion.isFixation
import com.congdy.cscrollmenu.ShowActivity.Companion.isIconShow
import com.congdy.cscrollmenu.ShowActivity.Companion.isRelevance
import com.congdy.cscrollmenu.ShowActivity.Companion.isUseNetpic
import com.congdy.cscrollmenu.ShowActivity.Companion.leftOffset
import com.congdy.cscrollmenu.ShowActivity.Companion.picPosition
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAllState()
    }

    /**
     * 获取xSlidingTabLayout的设置属性
     */
    private fun getAllState() {
        //是否关联viewpager
        rg_relevance.setOnCheckedChangeListener { _, rbId -> isRelevance = rbId == R.id.rb_relevance }
        //是否横向铺满
        rg_fill.setOnCheckedChangeListener { _, rbId -> isFill = rbId == R.id.rb_fill }
        //是否固定菜单条目宽度
        rg_fixation.setOnCheckedChangeListener { _, rbId -> isFixation = rbId == R.id.rb_fixation }
        //是否插入菜单图标
        rg_icon_show.setOnCheckedChangeListener { _, rbId ->
            isIconShow = rbId == R.id.rb_icon_show
            ll_use_icon.visibility = if (isIconShow) View.VISIBLE else View.GONE
        }
        //是否使用网络图片
        rg_use_netpic.setOnCheckedChangeListener { _, rbId ->
            isUseNetpic = rbId == R.id.rb_use_netpic
            ll_use_netpic.visibility = if (isUseNetpic) View.VISIBLE else View.GONE
        }
        //设置菜单切换距离左边距离
        rg_checked_distance.setOnCheckedChangeListener { _, rbId ->
            leftOffset = when (rbId) {
                R.id.rb_distance_1 -> -100
                R.id.rb_distance_2 -> -130
                R.id.rb_distance_3 -> -160
                R.id.rb_distance_4 -> -190
                else -> 100
            }
        }
        //显示菜单图标位置
        rg_icon_position.setOnCheckedChangeListener { _, rbId ->
            picPosition = when (rbId) {
                R.id.rb_icon_1 -> 0
                R.id.rb_icon_2 -> 1
                R.id.rb_icon_3 -> 2
                R.id.rb_icon_4 -> 3
                else -> 0
            }
        }
        btn_go.setOnClickListener {
            startActivity(Intent(this,ShowActivity::class.java))
        }
    }
}
