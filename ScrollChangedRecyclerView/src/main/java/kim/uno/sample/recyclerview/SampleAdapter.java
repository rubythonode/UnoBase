package kim.uno.sample.recyclerview;

import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import kim.uno.kotlin.base.ui.BaseRecyclerAdapter;
import kim.uno.kotlin.base.ui.BaseViewHolder;

public class SampleAdapter extends BaseRecyclerAdapter {

    @NotNull
    @Override
    public BaseViewHolder onCreateNewHolder(@NotNull ViewGroup parent, int type) {

        // focus resize transition test
        return new FocusResizeHolder(this, parent);

        // imageView margin changed transition test
//        return new MarginOffsetHolder(this, parent);
    }

}