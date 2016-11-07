package kim.uno.sample.dragablerecyclerview;

import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import kim.uno.kotlin.base.ui.BaseViewHolder;
import kim.uno.kotlin.base.ui.DragRecyclerAdapter;

public class SampleAdapter extends DragRecyclerAdapter {

    public SampleAdapter() {
        super();
    }

    @NotNull
    @Override
    public BaseViewHolder onCreateNewHolder(@NotNull ViewGroup parent, int type) {
        return new SampleHolder(this, parent);
    }

}