package kim.uno.base.ui.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.View;

public class FlipRecyclerView extends BaseRecyclerView {

    private int targetWidth;
    private int targetHeight;

    public FlipRecyclerView(Context context) {
        super(context);
        init();
    }

    public FlipRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlipRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        post(new Runnable() {
            @Override
            public void run() {
                targetWidth = getMeasuredWidth();
                targetHeight = getMeasuredHeight();
            }
        });
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        if (getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
            int firstVisibleView = layoutManager.findFirstVisibleItemPosition();
            int lastVisibleView = layoutManager.findLastVisibleItemPosition();
            View firstView = layoutManager.findViewByPosition(firstVisibleView);
            View lastView = layoutManager.findViewByPosition(lastVisibleView);

            // 횡스크롤
            if (layoutManager.getOrientation() == HORIZONTAL) {
                int frontMargin = (targetWidth - lastView.getWidth()) / 2;
                int endMargin = (targetWidth - firstView.getWidth()) / 2 + firstView.getWidth();
                int scrollDistanceFront = lastView.getLeft() - frontMargin;
                int scrollDistanceEnd = endMargin - firstView.getRight() ;
                if (velocityX > 0) smoothScrollBy(scrollDistanceFront, 0);
                else smoothScrollBy(-scrollDistanceEnd, 0);
            } else {
                int frontMargin = (targetHeight - lastView.getHeight()) / 2;
                int endMargin = (targetHeight - firstView.getHeight()) / 2 + firstView.getHeight();
                int scrollDistanceFront = lastView.getTop() - frontMargin;
                int scrollDistanceEnd = endMargin - firstView.getBottom() ;
                if (velocityY > 0) smoothScrollBy(0, scrollDistanceFront);
                else smoothScrollBy(0, -scrollDistanceEnd);
            }

            return true;
        } else {
            return super.fling(velocityX, velocityY);
        }
    }

}
