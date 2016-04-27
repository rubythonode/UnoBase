package kim.uno.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import kim.uno.item.RecyclerItem;
import kim.uno.ui.holder.BaseViewHolder;

public abstract class BaseRecyclerAdapter<T extends RecyclerItem> extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private ArrayList<T> mItems;

    public BaseRecyclerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        return onCreateNewHolder(parent, type);
    }

    public abstract BaseViewHolder onCreateNewHolder(ViewGroup parent, int type);

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position) != null ? getItem(position) : null, position);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position) != null ? getItem(position).getViewType() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public T getItem(int position) {
        return getItemCount() > position ? mItems.get(position) : null;
    }

    public ArrayList<T> getItems() {
        return mItems;
    }

    public void setItems(ArrayList<T> items) {
        setItems(items, true);
    }

    public void setItems(ArrayList<T> items, boolean notify) {
        mItems = items;
        if (notify) {
            notifyDataSetChanged();
        }
    }

    public void addItems(ArrayList<T> items) {
        addItems(items, true);
    }

    public void addItems(ArrayList<T> items, boolean notify) {
        if (mItems == null) mItems = new ArrayList<>();
        ArrayList<T> mergeItems = new ArrayList<>(mItems);
        mergeItems.addAll(items);
        mItems = mergeItems;
        if (notify) {
            if (mItems.size() - items.size() == 0) {
                notifyDataSetChanged();
            } else {
                notifyItemRangeInserted(mItems.size() - items.size(), mItems.size());
            }
        }
    }

    public void addItem(T item) {
        if (mItems == null) mItems = new ArrayList<>();
        ArrayList<T> mergeItems = new ArrayList<>(mItems);
        mergeItems.add(item);
        mItems = mergeItems;
    }

    public void clear() {
        if (mItems != null) mItems.clear();
    }
}
