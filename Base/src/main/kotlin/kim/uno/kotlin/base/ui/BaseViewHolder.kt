package kim.uno.kotlin.base.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kim.uno.kotlin.base.item.RecyclerItem

abstract class BaseViewHolder<T : RecyclerItem>(open val adapter: BaseRecyclerAdapter<*>, itemView: View) : RecyclerView.ViewHolder(itemView) {

    constructor(adapter: BaseRecyclerAdapter<*>, parent: ViewGroup, resId: Int) : this(adapter, LayoutInflater.from(adapter.recyclerView!!.context).inflate(resId, parent, false))

    open fun onBindView(item: T?, position: Int) { }

    protected val context: Context = adapter.recyclerView!!.context
    open fun getItem(): T? = adapter.getItem(adapterPosition) as T?
    open fun onScrollChanged(position: Float, dx: Int, dy: Int) { }

}