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

    private var scrollToTopButton: View? = null
    var enableFling: Boolean = false

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
        if (enableFling && layoutManager is LinearLayoutManager) {
            val layoutManager = layoutManager as LinearLayoutManager
            val firstVisibleView = layoutManager.findFirstVisibleItemPosition()
            val lastVisibleView = layoutManager.findLastVisibleItemPosition()
            val firstView = layoutManager.findViewByPosition(firstVisibleView)
            val lastView = layoutManager.findViewByPosition(lastVisibleView)

            // 횡스크롤
            if (layoutManager.orientation == RecyclerView.HORIZONTAL) {
                val frontMargin = (measuredWidth - lastView.width) / 2
                val endMargin = (measuredWidth - firstView.width) / 2 + firstView.width
                val scrollDistanceFront = lastView.left - frontMargin
                val scrollDistanceEnd = endMargin - firstView.right
                if (velocityX > 0) smoothScrollBy(scrollDistanceFront, 0)
                else smoothScrollBy(-scrollDistanceEnd, 0)
            } else {
                val frontMargin = (measuredHeight - lastView.height) / 2
                val endMargin = (measuredHeight - firstView.height) / 2 + firstView.height
                val scrollDistanceFront = lastView.top - frontMargin
                val scrollDistanceEnd = endMargin - firstView.bottom
                if (velocityY > 0) smoothScrollBy(0, scrollDistanceFront)
                else smoothScrollBy(0, -scrollDistanceEnd)
            }

            return true
        } else {
            return super.fling(velocityX, velocityY)
        }
    }

}
