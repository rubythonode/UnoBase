package kim.uno.sample.recyclerview;

import android.content.Context;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import kim.uno.kotlin.base.ui.BaseViewHolder;
import kim.uno.kotlin.base.ui.ScrollChangedAdapter;

public class SampleAdapter extends ScrollChangedAdapter {

    public SampleAdapter(@NotNull Context context) {
        super(context);
    }

    @NotNull
    @Override
    public BaseViewHolder onCreateNewHolder(@NotNull ViewGroup parent, int type) {
        return new SampleHolder(this, parent);
    }

}