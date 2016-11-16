package kim.uno.sample.recyclerview;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kim.uno.kotlin.base.ui.BaseRecyclerAdapter;
import kim.uno.kotlin.base.ui.BaseViewHolder;
import kim.uno.kotlin.base.util.DisplayUtil;

public class MarginOffsetHolder extends BaseViewHolder<Sample> {

    boolean isHorizontal;
    int scrollMargin = DisplayUtil.getPixelFromDp(getContext(), 120);

    RelativeLayout rlContent;
    ImageView ivContent;
    TextView tvContent;

    public MarginOffsetHolder(@NotNull BaseRecyclerAdapter adapter, @NotNull ViewGroup parent) {
        super(adapter, parent, R.layout.item_sample);
        rlContent = (RelativeLayout) itemView.findViewById(R.id.rl_content);
        ivContent = (ImageView) itemView.findViewById(R.id.iv_content);
        tvContent = (TextView) itemView.findViewById(R.id.tv_content);

        isHorizontal = getAdapter().isHorizontal();
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) ivContent.getLayoutParams();
        if (isHorizontal) {
            params.leftMargin = -scrollMargin;
            params.rightMargin = -scrollMargin;
            int width = DisplayUtil.getWidth(getContext()) / 2;
            rlContent.getLayoutParams().width = width;
            rlContent.getLayoutParams().height = width;
        } else {
            params.topMargin = -scrollMargin;
            params.bottomMargin = -scrollMargin;
            int width = DisplayUtil.getWidth(getContext());
            rlContent.getLayoutParams().width = width;
            rlContent.getLayoutParams().height = width;
        }
    }

    @Override
    public void onBindView(@Nullable Sample item, int position) {
        super.onBindView(item, position);
        Glide.with(getContext()).load(getContext().getString(R.string.sample_resource)).into(ivContent);
    }

    @Override
    public void onScrollChanged(float position, int dx, int dy) {
        super.onScrollChanged(position, dx, dy);
        if (isHorizontal) {
            ivContent.setTranslationX(scrollMargin - (scrollMargin * position));
            tvContent.setTranslationX(dx);
        } else {
            ivContent.setTranslationY(scrollMargin - (scrollMargin * position));
            tvContent.setTranslationY(dy);
        }
    }

}