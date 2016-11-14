package kim.uno.sample.recyclerview;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kim.uno.kotlin.base.ui.BaseRecyclerAdapter;
import kim.uno.kotlin.base.ui.BaseViewHolder;
import kim.uno.kotlin.base.util.DisplayUtil;

public class FocusResizeHolder extends BaseViewHolder<Sample> {

    boolean isHorizontal;

    float focusOffset = 0.2f, focusSize, foldFactor = 0.5f;

    RelativeLayout rlContent;
    ImageView ivContent;
    TextView tvContent;

    public FocusResizeHolder(@NotNull BaseRecyclerAdapter adapter, @NotNull ViewGroup parent) {
        super(adapter, parent, R.layout.item_sample);
        rlContent = (RelativeLayout) itemView.findViewById(R.id.rl_content);
        ivContent = (ImageView) itemView.findViewById(R.id.iv_content);
        tvContent = (TextView) itemView.findViewById(R.id.tv_content);

        isHorizontal = getAdapter().isHorizontal();
        if (isHorizontal) {
            int width = DisplayUtil.getWidth(getContext()) / 2;
            rlContent.getLayoutParams().width = width;
            rlContent.getLayoutParams().height = width;
            focusSize = rlContent.getLayoutParams().width + itemView.getPaddingLeft() + itemView.getPaddingRight();
        } else {
            int width = DisplayUtil.getWidth(getContext());
            rlContent.getLayoutParams().width = width;
            rlContent.getLayoutParams().height = (int) (width * 0.85f);
            focusSize = rlContent.getLayoutParams().height + itemView.getPaddingTop() + itemView.getPaddingBottom();
        }
    }

    @Override
    public void onBindView(@Nullable Sample item, int position) {
        super.onBindView(item, position);
        Glide.with(getContext()).load(getContext().getString(R.string.sample_resource)).into(new SimpleTarget<GlideDrawable>(rlContent.getLayoutParams().width, rlContent.getLayoutParams().height) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                ivContent.setImageDrawable(resource);
            }
        });
    }

    @Override
    public void onScrollChanged(float position, int dx, int dy) {
        super.onScrollChanged(position, dx, dy);

        position -= focusOffset;

        float normalize = Math.max(foldFactor, Math.min(1f, 1 - Math.abs(position)));
        tvContent.setAlpha(normalize * 1.5f);
        int computeSize = (int) (focusSize * normalize);
        if (isHorizontal) {
            itemView.getLayoutParams().width = computeSize;
        } else {
            itemView.getLayoutParams().height = computeSize;
        }

        itemView.requestLayout();
    }

}