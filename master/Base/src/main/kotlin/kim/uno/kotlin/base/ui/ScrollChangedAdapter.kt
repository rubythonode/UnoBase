package kim.uno.kotlin.base.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import kim.uno.kotlin.base.item.RecyclerItem
import java.util.*

abstract class ScrollChangedAdapter<T : RecyclerItem>(context: Context) : BaseRecyclerAdapter<T>(context) {

    private val attachedHolders = ArrayList<ScrollChangedViewHolder<T>>()
    var parent : RecyclerView? = null

    override fun onViewAttachedToWindow(holder: BaseViewHolder<T>?) {
        super.onViewAttachedToWindow(holder)
        if (holder is ScrollChangedViewHolder) attachedHolders.add(holder)
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder<T>?) {
        super.onViewDetachedFromWindow(holder)
        attachedHolders.remove(holder)
    }

    fun notifyScrollChanged(dx: Int, dy: Int) {
        if (parent != null && attachedHolders != null && attachedHolders.size > 0) {
            attachedHolders.forEachIndexed { i, holder ->
                val offset = holder?.itemView?.top ?: 0
                val height = (holder?.itemView?.bottom ?: 0) - (holder?.itemView?.top ?: 0)
                val parentHeight: Float = (parent?.height ?: 0).toFloat()
                val scrollFactor : Float = (offset + (height / 2f)) / parentHeight
                val factor = scrollFactor / 0.5f
                holder?.onScrollChanged(factor, dx, dy)
            }
        }
    }

}
