package kim.uno.sample.volley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;

import kim.uno.kotlin.base.network.Params;
import kim.uno.kotlin.base.network.volley.VolleyObjectListener;
import kim.uno.kotlin.base.network.volley.VolleyRequester;
import kim.uno.kotlin.base.util.LogUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogUtil.setLogEnable(true);

        VolleyObjectListener<Sample> listener = (response, error) -> {
            if (error != null) Log.i("uno", "onCreate: " + error.toString());
            Log.i("uno", "onCreate: " + response);
        };

        String url = "https://m.coocha.co.kr/mart/healthcheck.do";
//        String url = "http://m.coocha.co.kr/mart/healthcheck.do?mdtype_cg_cid=A&siteid=1&app_version=4.85&mdid=71c69c7857453aa32cca0ee9c9f351af&brows_version=7.0";
        VolleyRequester request = new VolleyRequester(Sample.class, Request.Method.POST, url, listener);
        Params params = new Params();
        params.put("siteid", "1");
        params.put("mdid", "71c69c7857453aa32cca0ee9c9f351af");
        params.put("brows_version", android.os.Build.VERSION.RELEASE);
        params.put("mdtype_cg_cid", "A");
        params.put("app_version", "7.0");
        request.setParams(params);
        request.apply(this);

//        ImageView iv = (ImageView) findViewById(R.id.iv_test);
////        Glide.with(this).load("http://동네언니.com/web/product/medium/201608/84_shop1_338756_dburl.png").into(iv);
////        Glide.with(this).load("http://%EB%8F%99%EB%84%A4%EC%96%B8%EB%8B%88.com/web/product/medium/201608/84_shop1_338756_dburl.png").into(iv);
////        Glide.with(this).load("http://xn--950b44amx563c.com/web/product/medium/201608/84_shop1_338756_dburl.png").into(iv);
//
//        String url = "http://동네언니.com/web/product/medium/201608/84_shop1_338756_dburl.png";
//        String protocol = url.split("://")[0] + "://";
//        url = url.replace(protocol, "");
//        String punyUrl = null;
//
//        for (String s : url.split("\\.")) {
//            String uni = IDN.toASCII(s);
//            punyUrl = punyUrl == null ? uni : punyUrl + "." + uni;
//        }
//
//        url = protocol + punyUrl;
//
//        Log.i("uno", "onCreate: " + protocol + punyUrl);
//        Glide.with(this).load(protocol + punyUrl).into(new SimpleTarget<GlideDrawable>() {
//            @Override
//            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                Log.i("uno", "onResourceReady: ");
//                iv.setImageDrawable(resource);
//            }
//
//            @Override
//            public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                super.onLoadFailed(e, errorDrawable);
//                Log.i("uno", "onLoadFailed: " + e.toString());
//            }
//        });

    }

}
