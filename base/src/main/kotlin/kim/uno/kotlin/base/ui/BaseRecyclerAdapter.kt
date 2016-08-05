package kim.uno.kotlin.base.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kim.uno.kotlin.base.item.RecyclerItem
import java.util.*

abstract class BaseRecyclerAdapter<T : RecyclerItem>(val context: Context) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    private var mItems: ArrayList<T>? = null

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): BaseViewHolder<T> = onCreateNewHolder(parent, type)

    abstract fun onCreateNewHolder(parent: ViewGroup, type: Int): BaseViewHolder<T>

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.onBindView(getItem(position), position)
    }

    override fun getItemViewType(position: Int): Int = getItem(position)?.getViewType()?: 0

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemCount(): Int = mItems?.size?: 0

    fun getItem(position: Int): T? = mItems?.getOrNull(position)

    fun setItems(items: ArrayList<T>) = setItems(items, true)

    open fun setItems(items: ArrayList<T>, notify: Boolean) {
        mItems = items
        if (notify) {
            notifyDataSetChanged()
        }
    }

    fun addItems(items: ArrayList<T>) = addItems(items, Integer.MAX_VALUE)

    fun addItems(items: ArrayList<T>, notify: Boolean) = addItems(items, Integer.MAX_VALUE, notify)

    fun addItems(items: ArrayList<T>, position: Int) = addItems(items, position, true)

    open fun addItems(items: ArrayList<T>, position: Int, notify: Boolean) {
        if (mItems == null) mItems = ArrayList<T>()

        val mergeItems = ArrayList(mItems?: ArrayList<T>())
        mergeItems.addAll(if (position < 0) 0 else if (position > itemCount) itemCount else position, items)
        mItems = mergeItems
        if (notify) {
            notifyItemRangeInserted(itemCount - items.size, itemCount)
        }
    }

    fun addItem(item: T) = addItem(item, Integer.MAX_VALUE)

    open fun addItem(item: T, position: Int) {
        if (mItems == null) mItems = ArrayList<T>()
        val mergeItems = ArrayList(mItems?: ArrayList<T>())
        mergeItems.add(if (position < 0) 0 else if (position > itemCount)  itemCount else position, item)
        mItems = mergeItems
    }

    fun clear() {
        mItems?.clear()
    }

}
