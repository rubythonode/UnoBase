package kim.uno.kotlin.sample;

import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kim.uno.kotlin.base.item.RecyclerItem;
import kim.uno.kotlin.base.ui.BaseRecyclerAdapter;
import kim.uno.kotlin.base.ui.BaseViewHolder;

public class SampleHolder extends BaseViewHolder {

    TextView tvSample;

    public SampleHolder(@NotNull BaseRecyclerAdapter adapter, @NotNull ViewGroup parent) {
        super(adapter, parent, R.layout.item_sample);
        tvSample = (TextView) itemView.findViewById(R.id.tv_sample);
    }

    @Override
    public void onBindView(@Nullable RecyclerItem item, int position) {
        super.onBindView(item, position);
        tvSample.setText("sample " + position);
    }

}