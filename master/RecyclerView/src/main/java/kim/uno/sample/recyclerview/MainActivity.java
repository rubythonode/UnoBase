package kim.uno.sample.recyclerview;

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
        recyclerSample.setTopIndicator(findViewById(R.id.v_top));
        recyclerSample.setLayoutManager(new LinearLayoutManager(this));

        SampleAdapter adapter = new SampleAdapter(this);
        recyclerSample.setAdapter(adapter);

        for (int i = 0; i < 20; i++) {
            adapter.addItem(new Sample("sample position " + (i + 1)));
        }

        adapter.notifyDataSetChanged();
    }

}
