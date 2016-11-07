package kim.uno.kotlin.base.ui

import android.support.v4.view.MotionEventCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.MotionEvent
import kim.uno.kotlin.base.item.RecyclerItem
import java.util.*

abstract class DragRecyclerAdapter<T : RecyclerItem>() : BaseRecyclerAdapter<T>() {

    var swapTopLimit: Int = 0
    var swapBottomLimit: Int = 0
        get() {
            if (field <= 0) this.swapBottomLimit = itemCount - 1
            return field
        }
    var startDragPosition: Int = 0
        private set

    protected var itemTouchHelper: ItemTouchHelper? = null

    override var recyclerView: RecyclerView?
        get() = super.recyclerView
        set(value) {
            super.recyclerView = value
            itemTouchHelper = ItemTouchHelper(DragHelperCallback(this))
            itemTouchHelper?.attachToRecyclerView(recyclerView)
        }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is BaseDragView) {
            holder.getHandleView()?.setOnTouchListener { view, motionEvent ->
                if (holder.isSwapable() && MotionEventCompat.getActionMasked(motionEvent) == MotionEvent.ACTION_DOWN) {
                    startDrag(holder)
                }
                false
            }
        }
    }

    fun startDrag(holder: BaseViewHolder<T>) {
        itemTouchHelper?.startDrag(holder)
        startDragPosition = holder.adapterPosition
        if (holder is BaseDragView) {
            holder.onDragStateChanged(true)
        }
    }

    fun onItemDismiss(position: Int) {
        if (itemCount > position) getItems()?.removeAt(position)
        notifyItemRemoved(position)
    }

    fun onItemSwap(source: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        if (source is BaseDragView && source.isSwapable() && target is BaseDragView && target.isSwapable()) {
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
