package kim.uno.kotlin.base.ui

import android.support.v4.view.MotionEventCompat
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import kim.uno.kotlin.base.item.RecyclerItem

abstract class DragViewHolder<T : RecyclerItem> : BaseViewHolder<T>, View.OnTouchListener {

    constructor(adapter: BaseRecyclerAdapter<*>, parent: ViewGroup, resId: Int) : super(adapter, parent, resId) {
    }

    constructor(adapter: BaseRecyclerAdapter<*>, itemView: View) : super(adapter, itemView) {
    }

    override fun onBindView(item: T?, position: Int) {
        super.onBindView(item, position)
        if (getHandleView() != null) {
            getHandleView()!!.setOnTouchListener(if (isSwapable()) this else null)
        }
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN && adapter is DragRecyclerAdapter) {
            (adapter as DragRecyclerAdapter<T>).startDrag(this)
        }
        return false
    }

    open fun onSelectedChanged(isSelected: Boolean) {

    }

    abstract fun getHandleView(): View?

    open fun isSwapable(): Boolean = true

}