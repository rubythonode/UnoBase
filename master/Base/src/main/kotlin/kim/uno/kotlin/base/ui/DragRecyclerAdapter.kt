package kim.uno.kotlin.base.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import kim.uno.kotlin.base.item.RecyclerItem
import java.util.*

abstract class DragRecyclerAdapter<T : RecyclerItem>(context: Context, recyclerView: RecyclerView) : BaseRecyclerAdapter<T>(context) {

    var swapTopLimit: Int = 0
    var swapBottomLimit: Int = 0
        get() {
            if (field <= 0) this.swapBottomLimit = itemCount - 1
            return field
        }
    var startDragPosition: Int = 0
        private set

    protected var itemTouchHelper: ItemTouchHelper

    init {
        itemTouchHelper = ItemTouchHelper(DragHelperCallback(this))
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    fun startDrag(holder: BaseViewHolder<T>) {
        itemTouchHelper.startDrag(holder)
        startDragPosition = holder.adapterPosition
        if (holder is DragViewHolder) {
            (holder as DragViewHolder).onSelectedChanged(true)
        }
    }

    fun onItemDismiss(position: Int) {
        if (itemCount > position) getItems()?.removeAt(position)
        notifyItemRemoved(position)
    }

    fun onItemSwap(source: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        if (source is DragViewHolder<*> && (source as DragViewHolder<*>).isSwapable()
                && target is DragViewHolder<*> && (target as DragViewHolder<*>).isSwapable()) {
            val sourcePosition = source.adapterPosition
            val targetPosition = target.adapterPosition
            val from = if (sourcePosition < targetPosition) sourcePosition else targetPosition
            val to = if (sourcePosition > targetPosition) sourcePosition else targetPosition
            if (from != to) {
                Collections.swap(getItems(), from, to)
                notifyItemMoved(from, to)
                for (i in 0..to - from - 1 - 1) {
                    notifyItemMoved(from, to - 1)
                }
                return true
            }
        }

        return false
    }

}
