package com.congdy.cscrollmenu

import android.graphics.Color

/**
 * 请描述使用该类使用方法！！！
 *
 * @author 陈聪 2018-01-17 18:39
 */
object UIUtils {
    /**
     * 设置透明度
     */
    fun setColorAlpha(color: Int, alpha: Byte): Int {
        return Color.argb(alpha.toInt(), Color.red(color), Color.green(color), Color.blue(color))
    }

    /**
     * 颜色变化切换效果计算
     */
    fun blendColors(color1: Int, color2: Int, ratio: Float): Int {
        val inverseRation = 1f - ratio
        val r = Color.red(color1) * ratio + Color.red(color2) * inverseRation
        val g = Color.green(color1) * ratio + Color.green(color2) * inverseRation
        val b = Color.blue(color1) * ratio + Color.blue(color2) * inverseRation
        return Color.rgb(r.toInt(), g.toInt(), b.toInt())
    }
}