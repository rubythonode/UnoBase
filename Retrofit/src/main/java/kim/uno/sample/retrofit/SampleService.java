package kim.uno.sample.retrofit;

import kim.uno.kotlin.base.network.Params;
import kim.uno.kotlin.base.util.GsonUtil;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface SampleService {

    @GET("{path}")
    Call<Sample> request(@Path("path") String path, @QueryMap Params map);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://coocha-48979.firebaseapp.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonUtil.getGson()))
//            .client(new OkHttpClient().newBuilder().addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)).build())
            .build();



}