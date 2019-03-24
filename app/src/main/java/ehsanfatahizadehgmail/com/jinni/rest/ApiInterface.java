package ehsanfatahizadehgmail.com.jinni.rest;


import java.util.List;

import ehsanfatahizadehgmail.com.jinni.models.CategoriesList;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiInterface {

    @FormUrlEncoded
    @POST("send-tell-for-verify.php")
    Call<String> sendTell(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("send-code-verify.php")
    Call<String> sendVerifyCode(@Field("mobile") String mobile , @Field("code") String code);

    @FormUrlEncoded
    @POST("send-info-seller.php")
    Call<String> sendInfo(@Field("mobile") String mobile, @Field("collection") String collection, @Field("manager") String manager, @Field("state") String state, @Field("city") String city, @Field("senf") String senf , @Field("pish_tell") String pish_tell , @Field("phone_sabet") String phone_sabet , @Field("address") String address, @Field("image") String image);

    @FormUrlEncoded
    @POST("add-category.php")
    Call<List<CategoriesList>> newCategory(@Field("name") String name , @Field("mobile") String mobile , @Field("parent_category") String parent_category );

    @GET("get-categories.php")
    Call<List<CategoriesList>> getCategories(@Query("mobile") String mobile , @Query("parent_category") String parent_category );



}
