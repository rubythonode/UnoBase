package kim.uno.sample.recyclerview;

import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kim.uno.kotlin.base.ui.BaseRecyclerAdapter;
import kim.uno.kotlin.base.ui.BaseViewHolder;
import kim.uno.kotlin.base.util.LogUtil;

public class SampleAdapter extends BaseRecyclerAdapter {

    public SampleAdapter() {
        super();
    }

    @NotNull
    @Override
    public BaseViewHolder onCreateNewHolder(@NotNull ViewGroup parent, int type) {
        return new SampleHolder(this, parent);
    }

    @Override
    public void onViewAttachedToWindow(@Nullable BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }
}