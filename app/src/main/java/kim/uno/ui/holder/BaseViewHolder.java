package kim.uno.ui.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kim.uno.ui.adapter.BaseRecyclerAdapter;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseRecyclerAdapter mAdapter;
    public Context mContext;

    public BaseViewHolder(BaseRecyclerAdapter adapter, ViewGroup parent, int resId) {
        this(adapter, LayoutInflater.from(adapter.getContext()).inflate(resId, parent, false));
    }

    public BaseViewHolder(BaseRecyclerAdapter adapter, View itemView) {
        super(itemView);
        mAdapter = adapter;
        mContext = mAdapter.getContext();
    }

    public void onBindView(final T item, final int position) { }

}