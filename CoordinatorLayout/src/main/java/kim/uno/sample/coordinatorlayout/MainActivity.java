package kim.uno.sample.coordinatorlayout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import kim.uno.kotlin.base.ui.BaseRecyclerView;

public class MainActivity extends AppCompatActivity {

    View vHeader;
    int headerHeight;

    RelativeLayout rlBackground;
    BottomSheetBehavior bottomSheetBehavior;
    ImageView ivContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaseRecyclerView recyclerSample = (BaseRecyclerView) findViewById(R.id.recycler_sample);
        recyclerSample.setScrollToTopButton(findViewById(R.id.rl_scroll_to_top));
        recyclerSample.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        SampleAdapter adapter = new SampleAdapter();
        recyclerSample.setAdapter(adapter);

        for (int i = 0; i < 20; i++) {
            Sample item = new Sample();
            item.message = "item position " + i;
            item.image = "http://www.dzimg.com/Dahong/201611/D697788_l.jpg";
            adapter.addItem(item);
        }

        adapter.notifyDataSetChanged();

        vHeader = findViewById(R.id.v_header);

        rlBackground = (RelativeLayout) findViewById(R.id.rl_bottom_sheet_bg);
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.rl_bottom_sheet));
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                rlBackground.setAlpha(slideOffset);
            }
        });

        ivContent = (ImageView) findViewById(R.id.iv_content);


        vHeader.post(() -> headerHeight = vHeader.getMeasuredHeight());

        recyclerSample.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) dy *= 0.5f;
                float translationY = Math.max(-headerHeight, Math.min(0, vHeader.getTranslationY() - dy));

//                View v = recyclerSample != null && recyclerSample.getChildCount() > 0 ? recyclerSample.getChildAt(0) : null;
//                if (v != null && recyclerSample.getLayoutManager().getPosition(v) == 0 && v.getTop() > -headerHeight) {
//                    translationY = 0;
//                }

                vHeader.setTranslationY(translationY);
            }
        });

    }

    public void show(Sample item) {
        if (item == null) return;
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        Glide.with(this).load(item.image).into(ivContent);
    }

    @Override
    public void onBackPressed() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }
}
