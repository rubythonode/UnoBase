package kim.uno.kotlin.base.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import kim.uno.kotlin.base.util.logE

open class BaseRecyclerView : RecyclerView {

    private var mTopIndicator: View? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    fun setTopIndicator(view: View) {
        mTopIndicator = view
        mTopIndicator!!.setOnClickListener { scrollToTop(true) }
        updateTopIndicator()
    }

    fun scrollToTop(isSmoothScroll: Boolean) = if (isSmoothScroll) smoothScrollToPosition(0) else scrollToPosition(0)

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        updateTopIndicator()
    }

    fun updateTopIndicator() {
        if (mTopIndicator == null) return

        var isVisible = false
        if (childCount > 0) {
            val view = getChildAt(0)
            val position = layoutManager.getPosition(view)
            isVisible = position != 0 || view.top < paddingTop
        }

        setTopIndicatorVisibility(isVisible)
    }

    fun setTopIndicatorVisibility(isVisible: Boolean) {
        try {
            if (isVisible && mTopIndicator!!.visibility == View.VISIBLE) return
            if (!isVisible && mTopIndicator!!.visibility != View.VISIBLE) return

            if (isVisible) {
                mTopIndicator!!.visibility = View.VISIBLE
                mTopIndicator!!.alpha = 0f
                mTopIndicator!!.animate().alpha(1f).setDuration(150).setListener(null)
            } else {
                mTopIndicator!!.animate().alpha(0f).setDuration(150).setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        mTopIndicator!!.visibility = View.GONE
                    }
                })
            }
        } catch (e: Exception) {
            logE(e)
        }

    }

    override fun isAttachedToWindow(): Boolean = if (Build.VERSION.SDK_INT >= 19) super.isAttachedToWindow() else handler != null

    override fun getAdapter(): BaseRecyclerAdapter<*> {
        return super.getAdapter() as BaseRecyclerAdapter<*>
    }

}