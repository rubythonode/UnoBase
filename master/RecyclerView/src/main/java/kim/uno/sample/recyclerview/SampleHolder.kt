package kim.uno.sample.recyclerview

import android.view.ViewGroup
import android.widget.TextView

import kim.uno.kotlin.base.ui.BaseRecyclerAdapter
import kim.uno.kotlin.base.ui.BaseViewHolder
import kim.uno.kotlin.base.util.ToastUtil

class SampleHolder(adapter: BaseRecyclerAdapter<*>, parent: ViewGroup) : BaseViewHolder<Sample>(adapter, parent, R.layout.item_sample) {

    internal var tvSample: TextView

    init {
        tvSample = itemView.findViewById(R.id.tv_sample) as TextView

        itemView.setOnClickListener { view -> ToastUtil.show(context, "getAdapterPosition -> " + getItem()?.message) }
    }

    override fun onBindView(item: Sample?, position: Int) {
        super.onBindView(item, position)
        tvSample.text = item?.message
    }

}