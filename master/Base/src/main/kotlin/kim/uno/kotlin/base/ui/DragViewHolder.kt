package kim.uno.kotlin.base.ui

import android.support.v4.view.MotionEventCompat
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import kim.uno.kotlin.base.item.RecyclerItem

abstract class DragViewHolder<T : RecyclerItem> : BaseViewHolder<T>, View.OnTouchListener {

    constructor(adapter: BaseRecyclerAdapter<T>, parent: ViewGroup, resId: Int) : super(adapter, parent, resId) {
    }

    constructor(adapter: BaseRecyclerAdapter<T>, itemView: View) : super(adapter, itemView) {
    }

    override fun onBindView(item: T?, position: Int) {
        super.onBindView(item, position)
        if (handleView != null) {
            handleView!!.setOnTouchListener(if (isSwapable) this else null)
        }
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN && adapter is DragRecyclerAdapter) {
            (adapter as DragRecyclerAdapter<T>).startDrag(this)
        }
        return false
    }

    fun onSelectedChanged(isSelected: Boolean) {
    }

    val handleView: View?
        get() = null

    val isSwapable: Boolean
        get() = true

}