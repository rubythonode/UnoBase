package kim.uno.kotlin.base.ui

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

class FlipRecyclerView : BaseRecyclerView {

    private var targetWidth: Int = 0
    private var targetHeight: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        post {
            targetWidth = measuredWidth
            targetHeight = measuredHeight
        }
    }

    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        if (layoutManager is LinearLayoutManager) {
            val layoutManager = layoutManager as LinearLayoutManager
            val firstVisibleView = layoutManager.findFirstVisibleItemPosition()
            val lastVisibleView = layoutManager.findLastVisibleItemPosition()
            val firstView = layoutManager.findViewByPosition(firstVisibleView)
            val lastView = layoutManager.findViewByPosition(lastVisibleView)

            // 횡스크롤
            if (layoutManager.orientation == RecyclerView.HORIZONTAL) {
                val frontMargin = (targetWidth - lastView.width) / 2
                val endMargin = (targetWidth - firstView.width) / 2 + firstView.width
                val scrollDistanceFront = lastView.left - frontMargin
                val scrollDistanceEnd = endMargin - firstView.right
                if (velocityX > 0) smoothScrollBy(scrollDistanceFront, 0)
                else smoothScrollBy(-scrollDistanceEnd, 0)
            } else {
                val frontMargin = (targetHeight - lastView.height) / 2
                val endMargin = (targetHeight - firstView.height) / 2 + firstView.height
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
