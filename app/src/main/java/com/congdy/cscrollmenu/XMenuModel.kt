package com.congdy.cscrollmenu

import java.io.Serializable

/**
 * 菜单数据model，使用的时候，如果是网络获取，可以继承此类进行相关处理
 *
 * @author 陈聪 2018-01-17 16:28
 */
class XMenuModel(var title: String = "",
                 var position: Int = 0,
                 var normal_icon: Int = 0,
                 var selected_icon: Int = 0,
                 var normal_net_icon: String = "",
                 var selected_net_icon: String = "",
                 var normalSize: Float = 15f,
                 var selectedSize: Float = 0f,
                 var normal_color: Int = 0,
                 var selected_color: Int = 0) : Serializable