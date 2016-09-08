package kim.uno.sample.recyclerview;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kim.uno.kotlin.base.ui.BaseRecyclerAdapter;
import kim.uno.kotlin.base.ui.ScrollChangedViewHolder;
import kim.uno.kotlin.base.util.DisplayUtil;

public class SampleHolder extends ScrollChangedViewHolder<Sample> {

    int scrollMargin;

    RelativeLayout rlContent;
    ImageView ivContent;
    TextView tvContent;

    public SampleHolder(@NotNull BaseRecyclerAdapter adapter, @NotNull ViewGroup parent) {
        super(adapter, parent, R.layout.item_sample);
        rlContent = (RelativeLayout) itemView.findViewById(R.id.rl_content);
        ivContent = (ImageView) itemView.findViewById(R.id.iv_content);
        tvContent = (TextView) itemView.findViewById(R.id.tv_content);

        scrollMargin = Math.abs(((ViewGroup.MarginLayoutParams) ivContent.getLayoutParams()).topMargin);
        rlContent.getLayoutParams().height = DisplayUtil.getWidth(getContext()) - itemView.getPaddingLeft() - itemView.getPaddingRight();
    }

    @Override
    public void onBindView(@Nullable Sample item, int position) {
        super.onBindView(item, position);
        Glide.with(getContext()).load(getContext().getString(R.string.sample_resource)).into(ivContent);
    }

    @Override
    public void onScrollChanged(float position, int dx, int dy) {
        super.onScrollChanged(position, dx, dy);
        ivContent.setTranslationY(scrollMargin - (scrollMargin * position));
        tvContent.setTranslationY(dy);
    }

}