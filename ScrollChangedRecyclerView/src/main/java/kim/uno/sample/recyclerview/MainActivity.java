package kim.uno.sample.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import kim.uno.kotlin.base.ui.BaseRecyclerView;
import kim.uno.kotlin.base.util.LogUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogUtil.setLogEnable(true);

        BaseRecyclerView recyclerSample = (BaseRecyclerView) findViewById(R.id.recycler_sample);
        recyclerSample.setScrollToTopButton(findViewById(R.id.v_top));
        recyclerSample.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        SampleAdapter adapter = new SampleAdapter();
        recyclerSample.setAdapter(adapter);

        for (int i = 0; i < 20; i++) {
            adapter.addItem(new Sample());
        }

        adapter.notifyDataSetChanged();
    }

}
