package kim.uno.kotlin.sample;

import android.content.Context;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import kim.uno.kotlin.base.ui.BaseRecyclerAdapter;
import kim.uno.kotlin.base.ui.BaseViewHolder;

public class SampleAdapter extends BaseRecyclerAdapter {

    public SampleAdapter(@NotNull Context context) {
        super(context);
    }

    @NotNull
    @Override
    public BaseViewHolder onCreateNewHolder(@NotNull ViewGroup parent, int type) {
        return new SampleHolder(this, parent);
    }

}