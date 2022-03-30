package com.example.paimasapp.data

import android.view.View
import androidx.viewpager2.widget.ViewPager2

private const val MIN_SCALE = 0.85f
private const val MIN_ALPHA = 0.5f

class ZoomOutCardTransformer : ViewPager2.PageTransformer {

    /**
     * Apply a property transformation to the given page.
     *
     * @param page Apply the transformation to this page
     * @param position Position of page relative to the current front-and-center
     * position of the pager. 0 is front and center. 1 is one full
     * page position to the right, and -2 is two pages to the left.
     * Minimum / maximum observed values depend on how many pages we keep
     * attached, which depends on offscreenPageLimit.
     *
     * @see .setOffscreenPageLimit
     */
    override fun transformPage(page: View, position: Float) {
        page.apply {
            val pWidth = width
            val pHeight = height
            when {
                position < -1 -> {
                    alpha = 0f
                }

                position <= 1 -> {
                    val scale = Math.max(MIN_SCALE, 1 - Math.abs(position))
                    val vMargin = pHeight * (1 - scale) / 2
                    val hMargin = pWidth * (1 - scale) / 2
                    translationX = if (position < 0) {
                        hMargin - vMargin / 2
                    } else {
                        hMargin + vMargin / 2
                    }

                    scaleX = scale
                    scaleY = scale
                    alpha = (MIN_ALPHA + (((scale - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
                }

                else -> {
                    alpha = 0f
                }
            }
        }
    }
}