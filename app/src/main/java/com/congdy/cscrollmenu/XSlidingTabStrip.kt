/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.congdy.cscrollmenu

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.LinearLayout
import com.congdy.cscrollmenu.UIUtils.blendColors
import com.congdy.cscrollmenu.UIUtils.setColorAlpha


/**
 * 滑动菜单的字条目自定义布局
 */
internal class XSlidingTabStrip @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

    /**底部的滑块厚度*/
    private val mBottomBorderThickness: Int
    /**底部滑块的画笔*/
    private val mBottomBorderPaint: Paint
    /**选中底部的滑块厚度*/
    private val mSelectedIndicatorThickness: Int
    /**选中底部滑块的画笔*/
    private val mSelectedIndicatorPaint: Paint
    /**选中底部滑块的颜色*/
    private val mDefaultBottomBorderColor: Int
    /**选中底部滑块的位置*/
    private var mSelectedPosition: Int = 0
    /**选中底部滑块的偏移量*/
    private var mSelectionOffset: Float = 0.toFloat()

    private var mCustomTabColorizer: XSlidingTabLayout.TabColorizer? = null
    private val mDefaultTabColorizer: SimpleTabColorizer

    init {
        setWillNotDraw(false)

        val density = resources.displayMetrics.density

        val outValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.colorForeground, outValue, true)
        val themeForegroundColor = outValue.data

        mDefaultBottomBorderColor = setColorAlpha(themeForegroundColor,
                DEFAULT_BOTTOM_BORDER_COLOR_ALPHA)

        mDefaultTabColorizer = SimpleTabColorizer()
        mDefaultTabColorizer.setIndicatorColors(DEFAULT_SELECTED_INDICATOR_COLOR)

        mBottomBorderThickness = (DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS * density).toInt()
        mBottomBorderPaint = Paint()
        mBottomBorderPaint.color = mDefaultBottomBorderColor

        mSelectedIndicatorThickness = (SELECTED_INDICATOR_THICKNESS_DIPS * density).toInt()
        mSelectedIndicatorPaint = Paint()
    }

    /**
     *设置正常菜单条目的颜色
     */
    fun setCustomTabColorizer(customTabColorizer: XSlidingTabLayout.TabColorizer) {
        mCustomTabColorizer = customTabColorizer
        invalidate()
    }
    /**
     *设置选中菜单条目的颜色
     */
    fun setSelectedIndicatorColors(vararg colors: Int) {
        // Make sure that the custom colorizer is removed
        mCustomTabColorizer = null
        mDefaultTabColorizer.setIndicatorColors(*colors)
        invalidate()
    }

    /**
     * viewpager切换的时候的选中位置和偏移量的变化
     */
    fun onViewPagerPageChanged(position: Int, positionOffset: Float) {
        mSelectedPosition = position
        mSelectionOffset = positionOffset
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        val height = height
        val childCount = childCount
        val tabColorizer = if (mCustomTabColorizer != null)
            mCustomTabColorizer
        else
            mDefaultTabColorizer

        // Thick colored underline below the current selection
        if (childCount > 0) {
            val selectedTitle = getChildAt(mSelectedPosition)
            var left = selectedTitle.left
            var right = selectedTitle.right
            var color = tabColorizer!!.getIndicatorColor(mSelectedPosition)

            if (mSelectionOffset > 0f && mSelectedPosition < getChildCount() - 1) {
                val nextColor = tabColorizer.getIndicatorColor(mSelectedPosition + 1)
                if (color != nextColor) {
                    color = blendColors(nextColor, color, mSelectionOffset)
                }

                // Draw the selection partway between the tabs
                val nextTitle = getChildAt(mSelectedPosition + 1)
                left = (mSelectionOffset * nextTitle.left + (1.0f - mSelectionOffset) * left).toInt()
                right = (mSelectionOffset * nextTitle.right + (1.0f - mSelectionOffset) * right).toInt()
            }

            mSelectedIndicatorPaint.color = color

            canvas.drawRect(left.toFloat(), (height - mSelectedIndicatorThickness).toFloat(), right.toFloat(),
                    height.toFloat(), mSelectedIndicatorPaint)
        }

        // Thin underline along the entire bottom edge
        canvas.drawRect(0f, (height - mBottomBorderThickness).toFloat(), width.toFloat(), height.toFloat(), mBottomBorderPaint)
    }

    private class SimpleTabColorizer : XSlidingTabLayout.TabColorizer {
        private var mIndicatorColors: IntArray? = null

        override fun getIndicatorColor(position: Int): Int {
            return mIndicatorColors!![position % mIndicatorColors!!.size]
        }

        internal fun setIndicatorColors(vararg colors: Int) {
            mIndicatorColors = colors
        }
    }

    companion object {

        private val DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 0
        private val DEFAULT_BOTTOM_BORDER_COLOR_ALPHA: Byte = 0x26
        private val SELECTED_INDICATOR_THICKNESS_DIPS = 2
        private val DEFAULT_SELECTED_INDICATOR_COLOR = -0xb77f

    }
}
