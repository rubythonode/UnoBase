package kim.uno.kotlin.base.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

open class ScrollChangedRecyclerView : BaseRecyclerView {

    private var needNotifyScrollChanged: Boolean = false
    private val listener = object: OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (needNotifyScrollChanged) (adapter as ScrollChangedAdapter<*>).notifyScrollChanged(dx, dy)
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (needNotifyScrollChanged) (adapter as ScrollChangedAdapter<*>).notifyScrollChanged(0, 0)
            }
        }
    }

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
        addOnScrollListener(listener)
    }

    override fun onDetachedFromWindow() {
        removeOnScrollListener(listener)
        super.onDetachedFromWindow()
    }

    override fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        super.setAdapter(adapter)
        if (adapter is ScrollChangedAdapter<*>) {
            needNotifyScrollChanged = true
            adapter.parent = this
        } else {
            needNotifyScrollChanged = false
        }
    }

}
