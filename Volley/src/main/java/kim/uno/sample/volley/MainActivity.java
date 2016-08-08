package kim.uno.sample.volley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;

import kim.uno.kotlin.base.network.Params;
import kim.uno.kotlin.base.network.volley.VolleyObjectListener;
import kim.uno.kotlin.base.network.volley.VolleyRequester;
import kim.uno.kotlin.base.util.LogUtil;
import kim.uno.kotlin.base.util.ToastUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogUtil.setLogEnable(true);

        VolleyObjectListener<Sample> listener = (response, error) -> ToastUtil.show(this, (error != null ? error.toString() : response.message));
        VolleyRequester request = new VolleyRequester(Sample.class, Request.Method.POST, "https://coocha-48979.firebaseapp.com/test/", listener);

        request.setParams(new Params().add("test1","1").add("test2", "2").add("test3","셋"));
        request.apply(this);
    }

}
