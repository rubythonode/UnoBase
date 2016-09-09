package kim.uno.sample.dragablerecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import kim.uno.kotlin.base.ui.BaseViewHolder;
import kim.uno.kotlin.base.ui.DragRecyclerAdapter;

public class SampleAdapter extends DragRecyclerAdapter {

    public SampleAdapter(@NotNull Context context, @NotNull RecyclerView recyclerView) {
        super(context, recyclerView);
    }

    @NotNull
    @Override
    public BaseViewHolder onCreateNewHolder(@NotNull ViewGroup parent, int type) {
        return new SampleHolder(this, parent);
    }

}