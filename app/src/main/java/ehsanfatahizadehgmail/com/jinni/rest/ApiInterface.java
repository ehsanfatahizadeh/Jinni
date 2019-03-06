package ehsanfatahizadehgmail.com.jinni.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("bazaryaabi_users_all_users.php")
    Call<String> getHome();

    @FormUrlEncoded
    @POST("bazaryaabi_users_add_user.php")
    Call<String> add_user(@Field("name") String name, @Field("tell_user") String tell_user, @Field("collection") String collection , @Field("commission") String commission, @Field("card_num") String card_num);


}
