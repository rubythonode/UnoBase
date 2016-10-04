package kim.uno.kotlin.base.ui

import android.view.View
import android.view.ViewGroup
import kim.uno.kotlin.base.item.RecyclerItem

abstract class ScrollChangedViewHolder<T : RecyclerItem> : BaseViewHolder<T> {

    constructor(adapter: BaseRecyclerAdapter<*>, itemView: View) : super(adapter, itemView) { }

    constructor(adapter: BaseRecyclerAdapter<*>, parent: ViewGroup, resId: Int) : super(adapter, parent, resId) { }

    open fun onScrollChanged(position: Float, dx: Int, dy: Int) { }

}