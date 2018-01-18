package com.congdy.cscrollmenu

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.congdy.cscrollmenu.ShowActivity.Companion.gravity
import com.congdy.cscrollmenu.ShowActivity.Companion.isFill
import com.congdy.cscrollmenu.ShowActivity.Companion.isFixation
import com.congdy.cscrollmenu.ShowActivity.Companion.isIconShow
import com.congdy.cscrollmenu.ShowActivity.Companion.isRelevance
import com.congdy.cscrollmenu.ShowActivity.Companion.isUseNetpic
import com.congdy.cscrollmenu.ShowActivity.Companion.leftOffset
import com.congdy.cscrollmenu.ShowActivity.Companion.menu_count
import com.congdy.cscrollmenu.ShowActivity.Companion.normal_net_pic
import com.congdy.cscrollmenu.ShowActivity.Companion.picPosition
import com.congdy.cscrollmenu.ShowActivity.Companion.selected_net_pic
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
        et_normal_pic.setText(normal_net_pic)
        et_selected_pic.setText(selected_net_pic)
        et_menu_count.setText(menu_count.toString())
        et_menu_count.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (!TextUtils.isEmpty(et_menu_count.text.toString().trim()))
                    menu_count = et_menu_count.text.toString().trim().toInt()
            }
        })
        et_normal_pic.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                normal_net_pic = et_normal_pic.text.toString().trim()
            }
        })
        et_selected_pic.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                selected_net_pic = et_selected_pic.text.toString().trim()
            }
        })
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
                R.id.rb_distance_1 -> 100
                R.id.rb_distance_2 -> 130
                R.id.rb_distance_3 -> 160
                R.id.rb_distance_4 -> 190
                else -> 100
            }
        }
        //显示菜单图标位置
        rg_icon_position.setOnCheckedChangeListener { _, rbId ->
            picPosition = when (rbId) {
                R.id.rb_icon_1 -> XSlidingTabLayout.DRAWABLE_LEFT
                R.id.rb_icon_2 -> XSlidingTabLayout.DRAWABLE_TOP
                R.id.rb_icon_3 -> XSlidingTabLayout.DRAWABLE_RIGHT
                R.id.rb_icon_4 -> XSlidingTabLayout.DRAWABLE_BOTTOM
                else -> XSlidingTabLayout.DRAWABLE_LEFT
            }
        }
        //显示菜单位置
        rg_gravity.setOnCheckedChangeListener { _, rbId ->
            gravity = when (rbId) {
                R.id.rb_gravity_left -> Gravity.LEFT
                R.id.rb_gravity_center -> Gravity.CENTER
                R.id.rb_gravity_right -> Gravity.RIGHT
                else -> Gravity.LEFT
            }
        }
        btn_go.setOnClickListener {
            if (!TextUtils.isEmpty(et_menu_count.text.toString().trim()))
                menu_count = et_menu_count.text.toString().trim().toInt()
            else {
                toast("请输入菜单数目")
                return@setOnClickListener
            }
            normal_net_pic = et_normal_pic.text.toString().trim()
            selected_net_pic = et_selected_pic.text.toString().trim()
            startActivity(Intent(this, ShowActivity::class.java))
        }
    }

    fun toast(str:String){
        Toast.makeText(this@MainActivity,str,Toast.LENGTH_SHORT).show()
    }
}
