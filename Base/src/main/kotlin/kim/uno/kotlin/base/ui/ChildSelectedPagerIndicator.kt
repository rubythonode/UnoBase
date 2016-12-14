package kim.uno.kotlin.base.ui

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

class ChildSelectedPagerIndicator : LinearLayout, ViewPager.OnPageChangeListener {

    private var viewPager: ViewPager? = null
    private var currentPosition = 0
    private var pageChangeListener: ViewPager.OnPageChangeListener? = null
    private var selectedAnimator: OnSelectedAnimator? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {

    }

    fun setViewPager(viewPager: ViewPager) {
        this.viewPager = viewPager
        this.viewPager?.setOnPageChangeListener(this)
        notifyDataSetChanged()
    }

    fun setOnPageChangeListener(listener: ViewPager.OnPageChangeListener) {
        this.pageChangeListener = listener
    }

    fun setOnSelectedAnimator(animator: OnSelectedAnimator?) {
        selectedAnimator = animator
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        pageChangeListener?.onPageScrolled(position, positionOffset, positionOffsetPixels)
    }

    override fun onPageSelected(position: Int) {
        if (childCount <= position) return
        pageChangeListener?.onPageSelected(position)
        selectedAnim(getChildAt(currentPosition), false)
        selectedAnim(getChildAt(position), true)
        currentPosition = position
    }

    override fun onPageScrollStateChanged(state: Int) {
        pageChangeListener?.onPageScrollStateChanged(state)
    }

    private fun selectedAnim(v: View, isSelected: Boolean) {

        val indicator = (v as ViewGroup).getChildAt(0)

        if (selectedAnimator != null) {
            selectedAnimator?.onSelected(indicator, isSelected)
        } else {

            val anims = AnimatorSet()

            val scale = ValueAnimator.ofFloat(if (isSelected) 0.8f else 1f, if (isSelected) 1f else 0.8f)
            scale.addUpdateListener { animation ->
                val value = animation.animatedValue as Float
                indicator.scaleX = value
                indicator.scaleY = value
            }

            val alpha = ValueAnimator.ofInt(if (isSelected) 127 else 255, if (isSelected) 255 else 127)
            alpha.addUpdateListener { animation ->
                val value = animation.animatedValue as Int
                indicator.background.alpha = value
            }

            anims.playTogether(scale, alpha)
            anims.duration = 200
            anims.start()
        }

    }

    fun notifyDataSetChanged() {
        viewPager?.currentItem = 0
        currentPosition = (viewPager?.currentItem)?: 0
        for (i in 0..childCount - 1) {
            val v = getChildAt(i)
            selectedAnim(v, false)
            v.setOnClickListener {
                viewPager?.currentItem = i
            }
        }
        onPageSelected(currentPosition)
    }

    interface OnSelectedAnimator {
        fun onSelected(v: View, isSelected: Boolean)
    }
}