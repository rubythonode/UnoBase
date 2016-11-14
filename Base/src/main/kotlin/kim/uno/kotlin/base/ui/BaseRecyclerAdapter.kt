package kim.uno.kotlin.base.ui

import android.support.v4.util.ArrayMap
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.ViewGroup
import kim.uno.kotlin.base.item.RecyclerItem
import java.util.*

abstract class BaseRecyclerAdapter<T : RecyclerItem>() : RecyclerView.Adapter<BaseViewHolder<T>>() {

    open var recyclerView: RecyclerView? = null
    private var mItems: ArrayList<T>? = null
    private val attachedHolders = ArrayMap<Int, BaseViewHolder<T>>()

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): BaseViewHolder<T> = onCreateNewHolder(parent, type)

    abstract fun onCreateNewHolder(parent: ViewGroup, type: Int): BaseViewHolder<T>

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.onBindView(getItem(position), position)
    }

    override fun getItemViewType(position: Int): Int = getItem(position)?.getViewType()?: 0

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemCount(): Int = mItems?.size?: 0

    fun getItem(position: Int): T? = mItems?.getOrNull(position)

    fun getItems(): ArrayList<T>? = mItems

    fun setItems(items: ArrayList<T>?) {
        mItems = items
        notifyDataSetChanged()
    }

    fun addItems(items: ArrayList<T>?) = addItems(items, Integer.MAX_VALUE)

    fun addItems(items: ArrayList<T>?, anim: Boolean) = addItems(items, Integer.MAX_VALUE, anim)

    fun addItems(items: ArrayList<T>?, position: Int) = addItems(items, position, true)

    open fun addItems(items: ArrayList<T>?, position: Int, anim: Boolean) {
        if (items?.size?: 0 == 0) return
        val mergeItems = ArrayList(mItems?: ArrayList<T>())
        mergeItems.addAll(Math.max(0, Math.min(position, itemCount)), items!!)
        mItems = mergeItems
        if (anim) {
            notifyItemRangeInserted(itemCount - items.size, itemCount)
        }
    }

    fun addItem(item: T?) = addItem(item, Integer.MAX_VALUE)

    open fun addItem(item: T?, position: Int) {
        if (item == null) return
        val mergeItems = ArrayList(mItems?: ArrayList<T>())
        mergeItems.add(Math.max(0, Math.min(position, itemCount)), item)
        mItems = mergeItems
    }

    fun clear() {
        mItems?.clear()
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder<T>) {
        super.onViewAttachedToWindow(holder)
        attachedHolders.put(holder.adapterPosition, holder)
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder<T>) {
        super.onViewDetachedFromWindow(holder)
        attachedHolders.values.remove(holder)
    }

    fun notifyScrollChanged(dx: Int, dy: Int) {
        if (attachedHolders.values.size > 0) {
            attachedHolders.values.map {
                val isHorizontal = isHorizontal()
                var parentSize: Float = ((if (isHorizontal) recyclerView?.width else recyclerView?.height) ?: 0).toFloat()
                val scroll = if (isHorizontal) {
                    if (isReverseLayout()) it.itemView.right else it.itemView.left
                } else {
                    if (isReverseLayout()) it.itemView.bottom else it.itemView.top
                }

                it.onScrollChanged(scroll / parentSize, dx, dy)
            }
        }
    }

    fun isHorizontal(): Boolean {
        return recyclerView?.layoutManager?.canScrollHorizontally()?: false
    }

    fun isReverseLayout(): Boolean {
        if (recyclerView?.layoutManager is LinearLayoutManager) {
            return (recyclerView?.layoutManager as LinearLayoutManager).reverseLayout
        } else if (recyclerView?.layoutManager is GridLayoutManager) {
            return (recyclerView?.layoutManager as GridLayoutManager).reverseLayout
        } else if (recyclerView?.layoutManager is StaggeredGridLayoutManager) {
            return (recyclerView?.layoutManager as StaggeredGridLayoutManager).reverseLayout
        } else {
            return false
        }
    }

}
