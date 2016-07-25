package kim.uno.kotlin.base.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kim.uno.kotlin.base.item.RecyclerItem
import java.util.*

abstract class BaseRecyclerAdapter<T : RecyclerItem>(val context: Context) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    private var mItems: ArrayList<T> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): BaseViewHolder<T> = onCreateNewHolder(parent, type)

    abstract fun onCreateNewHolder(parent: ViewGroup, type: Int): BaseViewHolder<T>

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.onBindView(getItem(position), position)
    }

    override fun getItemViewType(position: Int): Int = getItem(position)!!.getViewType()

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemCount(): Int = mItems!!.size

    fun getItem(position: Int): T? = mItems.getOrNull(position)

    fun setItems(items: ArrayList<T>, notify: Boolean = true) {
        mItems = items
        if (notify) {
            notifyDataSetChanged()
        }
    }

    fun addItems(items: ArrayList<T>, position: Int = Integer.MAX_VALUE, notify: Boolean = true) {
        if (mItems == null) mItems = ArrayList<T>()

        val mergeItems = ArrayList(mItems!!)
        mergeItems.addAll(if (position < 0) 0 else if (position > mItems!!.size) mItems!!.size else position, items)
        mItems = mergeItems
        if (notify) {
            notifyItemRangeInserted(mItems!!.size - items.size, mItems!!.size)
        }
    }

    fun addItem(item: T, position: Int = Integer.MAX_VALUE) {
        if (mItems == null) mItems = ArrayList<T>()
        val mergeItems = ArrayList(mItems!!)
        mergeItems.add(if (position < 0) 0 else if (position > mItems!!.size)  mItems!!.size else position, item)
        mItems = mergeItems
    }

    fun clear() {
        if (mItems != null) mItems!!.clear()
    }

}
