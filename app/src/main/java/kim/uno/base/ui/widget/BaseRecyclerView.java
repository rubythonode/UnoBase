package kim.uno.base.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import kim.uno.base.ui.adapter.BaseRecyclerAdapter;
import kim.uno.base.util.LogUtil;

public class BaseRecyclerView extends RecyclerView {

    private View mTopIndicator;

    public BaseRecyclerView(Context context) {
        super(context);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setTopIndicator(View view) {
        mTopIndicator = view;
        mTopIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollToTop(true);
            }
        });
        updateTopIndicator();
    }

    public void scrollToTop(boolean isSmoothScroll) {
        if (isSmoothScroll) smoothScrollToPosition(0);
        else scrollToPosition(0);
//        setTopIndicatorVisibility(false);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        updateTopIndicator();
    }

    public void updateTopIndicator() {
        if (mTopIndicator == null) return;

        boolean isVisible = false;
        if (getChildCount() > 0) {
            View view = getChildAt(0);
            int position = getLayoutManager().getPosition(view);
            isVisible = position != 0 || view.getTop() < getPaddingTop();
        }

        setTopIndicatorVisibility(isVisible);
    }

    public void setTopIndicatorVisibility(boolean isVisible) {
        try {
            if (isVisible && mTopIndicator.getVisibility() == VISIBLE) return;
            if (!isVisible && mTopIndicator.getVisibility() != VISIBLE) return;

            if (isVisible) {
                mTopIndicator.setVisibility(View.VISIBLE);
                mTopIndicator.setAlpha(0f);
                mTopIndicator.animate()
                        .alpha(1f)
                        .setDuration(150)
                        .setListener(null);
            } else {
                mTopIndicator.animate()
                        .alpha(0f)
                        .setDuration(150)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                mTopIndicator.setVisibility(View.GONE);
                            }
                        });
            }
        } catch (Exception e) { LogUtil.e(e); }
    }

    @Override
    public boolean isAttachedToWindow() {
        if (Build.VERSION.SDK_INT >= 19) {
            return super.isAttachedToWindow();
        } else {
            return getHandler() != null;
        }
    }

    @Override
    public BaseRecyclerAdapter getAdapter() {
        return (BaseRecyclerAdapter) super.getAdapter();
    }
}
