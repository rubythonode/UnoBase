package kim.uno.sample.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.jetbrains.annotations.Nullable;

import kim.uno.kotlin.base.network.Params;
import kim.uno.kotlin.base.network.volley.VolleyObjectListener;
import kim.uno.kotlin.base.network.volley.VolleyRequester;
import kim.uno.kotlin.base.util.ProgressUtil;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    int cntRetrofit, cntVolley;
    long msRetrofit, msVolley;

    Button btRetrofit, btVolley;
    TextView tvAvgRetrofit, tvAvgVolley;

    RelativeLayout rlResult, rlResultClose;
    TextView tvResult;

    String resultRetrofit = "";
    String resultVolley = "";

    SampleService sampleService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        requestByRetrofit();
    }

    private void initView() {
        btRetrofit = (Button) findViewById(R.id.bt_retrofit);
        btRetrofit.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(resultRetrofit)) {
                tvResult.setText(resultRetrofit);
                rlResult.setVisibility(View.VISIBLE);
            }
        });

        btVolley = (Button) findViewById(R.id.bt_volley);
        btVolley.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(resultVolley)) {
                tvResult.setText(resultVolley);
                rlResult.setVisibility(View.VISIBLE);
            }
        });

        tvAvgRetrofit = (TextView) findViewById(R.id.tv_avg_retrofit);
        tvAvgVolley = (TextView) findViewById(R.id.tv_avg_volley);

        rlResult = (RelativeLayout) findViewById(R.id.rl_result);
        rlResultClose = (RelativeLayout) findViewById(R.id.rl_result_close);
        rlResultClose.setOnClickListener(v -> rlResult.setVisibility(View.GONE));

        tvResult = (TextView) findViewById(R.id.tv_result);
    }

    private void requestByRetrofit() {
        if (!ProgressUtil.isShowing()) {
            ProgressUtil.show(this, "RETROFIT", false);
        }

        final long start = System.currentTimeMillis();

        if (sampleService == null) sampleService = SampleService.retrofit.create(SampleService.class);
        Call<Sample> call = sampleService.request("test", new Params().add("ab", "1").add("abb", "2"));

        call.enqueue(new ObjectCallBack<Sample>() {
            @Override
            public void onResponse(Call<Sample> call, Response<Sample> response, Throwable t) {
                super.onResponse(call, response, t);
                long currentDuration = System.currentTimeMillis() - start;
                long totalDuration = (msRetrofit * cntRetrofit++) + currentDuration;
                msRetrofit = totalDuration / cntRetrofit;
                tvAvgRetrofit.setText(cntRetrofit + "회 평균 " + msRetrofit + "ms");
                resultRetrofit +=  cntRetrofit + "회 " + currentDuration + "ms\n";

//                if (t != null) {
//                    ToastUtil.show(MainActivity.this, t.getMessage());
//                } else if (response.isSuccessful()) {
//                    ToastUtil.show(MainActivity.this, response.body().message);
//                } else {
//                    ToastUtil.show(MainActivity.this, "서버 오류");
//                }

                if (cntRetrofit < 30) {
                    ProgressUtil.show(MainActivity.this, "RETROFIT (" + cntRetrofit + "/30)" , false);
                    requestByRetrofit();
                } else {
                    ProgressUtil.show(MainActivity.this, "VOLLEY", false);
                    requestByVolley();
                }
            }
        });
    }

    private void requestByVolley() {
        final long start = System.currentTimeMillis();

        VolleyRequester<Sample> requester = new VolleyRequester(Sample.class, Request.Method.GET, "https://coocha-48979.firebaseapp.com/test", new VolleyObjectListener<Sample>() {
            @Override
            public void onResponse(@Nullable Sample response, @Nullable VolleyError error) {
                long currentDuration = System.currentTimeMillis() - start;
                long totalDuration = (msVolley * cntVolley++) + currentDuration;
                msVolley = totalDuration / cntVolley;
                tvAvgVolley.setText(cntVolley + "회 평균 " + msVolley + "ms");
                resultVolley +=  cntVolley + "회 " + currentDuration + "ms\n";

//                if (error != null) {
//                    ToastUtil.show(MainActivity.this, error.getMessage());
//                } else if (response != null) {
//                    ToastUtil.show(MainActivity.this, response.message);
//                } else {
//                    ToastUtil.show(MainActivity.this, "서버 오류");
//                }

                if (cntVolley < 30) {
                    ProgressUtil.show(MainActivity.this, "VOLLEY (" + cntVolley + "/30)" , false);
                    requestByVolley();
                } else {
                    ProgressUtil.dismiss();
                }
            }
        });

        requester.setParams(new Params().add("ab", "1").add("abb", "2"));
        requester.setShouldCache(false);
        requester.apply(this);
    }

}
