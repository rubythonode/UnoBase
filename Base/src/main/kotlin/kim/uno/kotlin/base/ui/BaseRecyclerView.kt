package kim.uno.kotlin.base.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import kim.uno.kotlin.base.util.LogUtil

open class BaseRecyclerView : RecyclerView {

    enum class FlingGravity {
        START, CENTER, END
    }

    private var scrollToTopButton: View? = null
    var flingEnable: Boolean = false
    var flingGravity: FlingGravity = FlingGravity.START

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    init {
        addOnScrollListener(object: OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                adapter.notifyScrollChanged(dx, dy)
            }
        })
    }

    override fun onScrollChanged(nx: Int, ny: Int, ox: Int, oy: Int) {
        super.onScrollChanged(nx, ny, ox, oy)
        invalidateScrollToTopButton()
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            adapter.notifyScrollChanged(0, 0)
        }
    }

    // scroll to top button setting automatically
    open fun setScrollToTopButton(view: View) {
        scrollToTopButton = view
        scrollToTopButton?.setOnClickListener { scrollToTop(true) }
        invalidateScrollToTopButton()
    }

    open fun scrollToTop(isSmoothScroll: Boolean) = if (isSmoothScroll) smoothScrollToPosition(0) else scrollToPosition(0)

    open fun setFlingEnable(enable: Boolean, gravity: FlingGravity = FlingGravity.START) {
        flingEnable = enable
        flingGravity = gravity
    }

    fun invalidateScrollToTopButton() {
        if (scrollToTopButton == null) return

        var isVisible = false
        if (childCount > 0) {
            val view = getChildAt(0)
            val position = layoutManager.getPosition(view)
            isVisible = position != 0
        }

        setVisibilityScrollToTopButton(isVisible)
    }

    open fun setVisibilityScrollToTopButton(isVisible: Boolean) {
        if (scrollToTopButton == null) return
        var indicator = scrollToTopButton as View

        try {
            if (isVisible && indicator.visibility == View.VISIBLE) return
            if (!isVisible && indicator.visibility != View.VISIBLE) return

            if (isVisible) {
                indicator.visibility = View.VISIBLE
                indicator.alpha = 0f
                indicator.animate().alpha(1f).setDuration(150).setListener(null)
            } else {
                indicator.animate().alpha(0f).setDuration(150).setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        indicator.visibility = View.GONE
                    }
                })
            }
        } catch (e: Exception) {
            LogUtil.e(e)
        }
    }

    fun setAdapter(adapter: BaseRecyclerAdapter<*>) {
        super.setAdapter(adapter as Adapter<*>)
        adapter.recyclerView = this
    }

    override fun getAdapter(): BaseRecyclerAdapter<*> {
        return super.getAdapter() as BaseRecyclerAdapter<*>
    }

    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        if (flingEnable && layoutManager is LinearLayoutManager) {
            val layoutManager = layoutManager as LinearLayoutManager
            val firstIndex = layoutManager.findFirstVisibleItemPosition()
            val lastIndex = layoutManager.findLastVisibleItemPosition()
            val firstView = layoutManager.findViewByPosition(firstIndex)
            val lastView = layoutManager.findViewByPosition(lastIndex)

            // 횡스크롤
            if (layoutManager.orientation == RecyclerView.HORIZONTAL) {

                var offset = 0
                when(flingGravity) {
                    FlingGravity.START -> offset = 0
                    FlingGravity.CENTER -> offset = (measuredWidth - lastView.width) / 2
                    FlingGravity.END -> offset = measuredWidth - lastView.width
                }

                val frontMargin = offset
                val endMargin = offset + firstView.width
                if (velocityX > 0) smoothScrollBy(lastView.left - frontMargin, 0)
                else smoothScrollBy(-(endMargin - firstView.right), 0)
            } else {

                var offset = 0
                when(flingGravity) {
                    FlingGravity.START -> offset = 0
                    FlingGravity.CENTER -> offset = (measuredHeight - lastView.height) / 2
                    FlingGravity.END -> offset = measuredHeight - lastView.height
                }

                val frontMargin = offset
                val endMargin = offset + firstView.height
                if (velocityY > 0) smoothScrollBy(0, lastView.top - frontMargin)
                else smoothScrollBy(0, -(endMargin - firstView.bottom))
            }

            return true
        } else {
            return super.fling(velocityX, velocityY)
        }
    }

}