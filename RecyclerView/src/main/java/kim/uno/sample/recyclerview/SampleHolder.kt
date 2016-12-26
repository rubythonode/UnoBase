package kim.uno.sample.recyclerview

import android.view.ViewGroup
import kim.uno.kotlin.base.ui.BaseRecyclerAdapter
import kim.uno.kotlin.base.ui.BaseViewHolder
import kim.uno.kotlin.base.util.DisplayUtil
import kotlinx.android.synthetic.main.item_sample.view.*

class SampleHolder(adapter: BaseRecyclerAdapter<*>, parent: ViewGroup) : BaseViewHolder<Sample>(adapter, parent, R.layout.item_sample) {

    var scrollMargin: Int = 0

    init {
        scrollMargin = Math.abs((itemView.iv_content.layoutParams as ViewGroup.MarginLayoutParams).topMargin)

        val gridWidth = DisplayUtil.getWidth(context) * 0.7f - itemView.paddingLeft - itemView.paddingRight
        val gridHeight = gridWidth * (452f / 640f)

        itemView.rl_content.layoutParams.width = gridWidth.toInt()
        itemView.rl_content.layoutParams.height = gridHeight.toInt()
        itemView.iv_content.layoutParams.width = gridWidth.toInt()
        itemView.iv_content.layoutParams.height = gridHeight.toInt()
    }

    override fun onBindView(item: Sample?, position: Int) {
        super.onBindView(item, position)
        itemView.tv_content.text = item?.message
    }

}