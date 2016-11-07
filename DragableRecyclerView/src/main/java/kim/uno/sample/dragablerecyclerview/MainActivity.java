package kim.uno.sample.dragablerecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import kim.uno.kotlin.base.ui.BaseRecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaseRecyclerView recyclerSample = (BaseRecyclerView) findViewById(R.id.recycler_sample);
        recyclerSample.setScrollToTopButton(findViewById(R.id.v_top));
        recyclerSample.setLayoutManager(new LinearLayoutManager(this));

        SampleAdapter adapter = new SampleAdapter();
        recyclerSample.setAdapter(adapter);

        for (int i = 0; i < 20; i++) {
            Sample item = new Sample();
            item.isSwapable = i % 5 != 0;
            item.message = item.isSwapable? "swapable position " + i : "stone position " + i;
            adapter.addItem(item);
        }

        adapter.notifyDataSetChanged();
    }

}
