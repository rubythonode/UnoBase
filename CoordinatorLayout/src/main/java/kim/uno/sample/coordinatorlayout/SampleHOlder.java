package kim.uno.sample.coordinatorlayout;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kim.uno.kotlin.base.ui.BaseRecyclerAdapter;
import kim.uno.kotlin.base.ui.BaseViewHolder;
import kim.uno.kotlin.base.util.DisplayUtil;

public class SampleHolder extends BaseViewHolder<Sample> {

    boolean isHorizontal;
    int scrollMargin = DisplayUtil.getPixelFromDp(getContext(), 0);

    ImageView ivContent;
    TextView tvContent;

    public SampleHolder(@NotNull BaseRecyclerAdapter adapter, @NotNull ViewGroup parent) {
        super(adapter, parent, R.layout.item_sample);
        ivContent = (ImageView) itemView.findViewById(R.id.iv_content);
        tvContent = (TextView) itemView.findViewById(R.id.tv_content);

        isHorizontal = getAdapter().isHorizontal();
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) ivContent.getLayoutParams();
        if (isHorizontal) {
            params.leftMargin = -scrollMargin;
            params.rightMargin = -scrollMargin;
            int width = DisplayUtil.getWidth(getContext()) / 2;
            itemView.getLayoutParams().width = width;
            itemView.getLayoutParams().height = width;
        } else {
            params.topMargin = -scrollMargin;
            params.bottomMargin = -scrollMargin;
            int width = DisplayUtil.getWidth(getContext());
            itemView.getLayoutParams().width = width;
            itemView.getLayoutParams().height = width;
        }

        itemView.setOnClickListener(v -> {
            if (getContext() instanceof MainActivity) {
                ((MainActivity) getContext()).show(getItem());
            }
        });
    }

    @Override
    public void onBindView(@Nullable Sample item, int position) {
        super.onBindView(item, position);
        Glide.with(getContext()).load(item.image).into(ivContent);
    }

    @Override
    public void onScrollChanged(float position, int dx, int dy) {
        super.onScrollChanged(position, dx, dy);
//        if (isHorizontal) {
//            ivContent.setTranslationX(scrollMargin - (scrollMargin * position));
//            tvContent.setTranslationX(dx);
//        } else {
//            ivContent.setTranslationY(scrollMargin - (scrollMargin * position));
//            tvContent.setTranslationY(dy);
//        }
    }

}