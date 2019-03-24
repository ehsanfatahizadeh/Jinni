package ehsanfatahizadehgmail.com.jinni.rest;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSetting {

    ApiInterface apiInterface;
    Retrofit retrofit;







    public RetrofitSetting(){

        OkHttpClient client = new OkHttpClient();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://beh-navaz.ir/jinni-api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        apiInterface = retrofit.create(ApiInterface.class);

    }


    public ApiInterface getApiInterface() {
        return apiInterface;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

}
