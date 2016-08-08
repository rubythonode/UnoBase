package kim.uno.kotlin.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.android.volley.Request;

import kim.uno.kotlin.base.network.Params;
import kim.uno.kotlin.base.network.volley.VolleyObjectListener;
import kim.uno.kotlin.base.network.volley.VolleyRequester;
import kim.uno.kotlin.base.ui.BaseRecyclerView;
import kim.uno.kotlin.base.util.LogUtil;

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
            adapter.addItem(new Sample());
        }

        adapter.notifyDataSetChanged();

        LogUtil.setLogEnable(true);

        VolleyObjectListener<Sample> listener = (response, error) -> Log.i("uno", "onResponse: " + response.message);
        VolleyRequester request = new VolleyRequester(Sample.class, Request.Method.POST, "https://coocha-48979.firebaseapp.com/test/", listener);

        request.setParams(new Params().add("test1","1").add("test2", "2").add("test3","셋"));
        request.apply(this);

    }

}
