package kim.uno.sample.volley;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.net.IDN;

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

        ImageView iv = (ImageView) findViewById(R.id.iv_test);
//        Glide.with(this).load("http://동네언니.com/web/product/medium/201608/84_shop1_338756_dburl.png").into(iv);
//        Glide.with(this).load("http://%EB%8F%99%EB%84%A4%EC%96%B8%EB%8B%88.com/web/product/medium/201608/84_shop1_338756_dburl.png").into(iv);
//        Glide.with(this).load("http://xn--950b44amx563c.com/web/product/medium/201608/84_shop1_338756_dburl.png").into(iv);

        String url = "http://동네언니.com/web/product/medium/201608/84_shop1_338756_dburl.png";
        String protocol = url.split("://")[0] + "://";
        url = url.replace(protocol, "");
        String punyUrl = null;

        for (String s : url.split("\\.")) {
            String uni = IDN.toASCII(s);
            punyUrl = punyUrl == null ? uni : punyUrl + "." + uni;
        }

        url = protocol + punyUrl;

        Log.i("uno", "onCreate: " + protocol + punyUrl);
        Glide.with(this).load(protocol + punyUrl).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                Log.i("uno", "onResourceReady: ");
                iv.setImageDrawable(resource);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                Log.i("uno", "onLoadFailed: " + e.toString());
            }
        });

    }

}
