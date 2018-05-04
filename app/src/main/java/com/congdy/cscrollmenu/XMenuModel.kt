package com.congdy.cscrollmenu

import java.io.Serializable

/**
 * 菜单数据model，使用的时候，如果是网络获取，可以继承此类进行相关处理
 *
 * @author 陈聪 2018-01-17 16:28
 */
class XMenuModel(var title: String = "",//菜单名称
                 var position: Int = 0,
                 var drawable_position:Int = XSlidingTabLayout.DRAWABLE_LEFT,//图片标签显示位置
                 var normal_icon: Int = 0,//正常情况本地图片标签
                 var selected_icon: Int = 0,//选中情况本地图片标签
                 var normal_net_icon: String = "",//正常情况网络图片标签
                 var selected_net_icon: String = "",//选中情况网络图片标签
                 var normalSize: Float = 15f,//正常情况菜单size
                 var selectedSize: Float = 0f,//选中情况菜单size
                 var normal_color: Int = 0,//正常情况菜单颜色
                 var selected_color: Int = 0) : Serializable//选中情况菜单颜色