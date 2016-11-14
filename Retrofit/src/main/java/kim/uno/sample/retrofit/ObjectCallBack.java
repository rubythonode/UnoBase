package kim.uno.sample.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ObjectCallBack<T> implements Callback<T> {

    @Override
    final public void onResponse(Call<T> call, Response<T> response) {
        onResponse(call, response, null);
    }

    @Override
    final public void onFailure(Call<T> call, Throwable t) {
        onResponse(call, null, t);
    }
    
    public void onResponse(Call<T> call, Response<T> response, Throwable t) {
        
    }
}
