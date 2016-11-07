package kim.uno.sample.dragablerecyclerview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kim.uno.kotlin.base.ui.BaseDragView;
import kim.uno.kotlin.base.ui.BaseRecyclerAdapter;
import kim.uno.kotlin.base.ui.BaseViewHolder;

public class SampleHolder extends BaseViewHolder<Sample> implements BaseDragView {

    TextView tvContent;
    View vHandle;

    public SampleHolder(@NotNull BaseRecyclerAdapter adapter, @NotNull ViewGroup parent) {
        super(adapter, parent, R.layout.item_sample);
        tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        vHandle = itemView.findViewById(R.id.v_handle);
    }

    @Override
    public void onBindView(@Nullable Sample item, int position) {
        super.onBindView(item, position);
        tvContent.setText(item.message);
        vHandle.setVisibility(item.isSwapable ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDragStateChanged(boolean isSelected) {
        itemView.setAlpha(isSelected ? 0.8f : 1f);
    }

    @Nullable
    @Override
    public View getHandleView() {
        return vHandle;
    }

    @Override
    public boolean isSwapable() {
        Sample item = getItem();
        return item != null && item.isSwapable;
    }
}