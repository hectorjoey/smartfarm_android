package hector.developers.smartfarm.Api;

import android.database.Observable;

import java.util.List;

import hector.developers.smartfarm.model.FarmImplements;
import hector.developers.smartfarm.model.Products;
import hector.developers.smartfarm.model.Users;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Api {
    @FormUrlEncoded
    @POST("users")
    Call<ResponseBody> createUser(
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("phone") String phone,
            @Field("email") String email,
            @Field("password") String password,
            @Field("userType") String userType,
            @Field("state") String state
    );

    @FormUrlEncoded
    @POST("products")
    Call<Products> createProduct(
            @Field("productName") String productName,
            @Field("quantity") String quantity,
            @Field("price") String price,
            @Field("state") String state,
            @Field("lga") String lga,
            @Field("location") String location,
            @Field("productCategory") String productCategory,
            @Field("userId") Long userId);


    //the signIn call
    @FormUrlEncoded
    @POST("login")
    Call<Users> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("products")
    Call<List<Products>> getProducts();

    @GET("users")
    Call<List<Users>> getUsers();

    @FormUrlEncoded
    @POST("implement")
    Call<FarmImplements> createFarmImplement(
            @Field("implementName") String implementName,
            @Field("size") String size,
            @Field("price") String price,
            @Field("state") String state,
            @Field("lga") String lga,
            @Field("location") String location,
            @Field("userId") Long userId);

    @GET("implements")
    Call<List<FarmImplements>> getFarmImplements();

    //fetching user health data
    @GET("implement/{userId}")
    Call<List<FarmImplements>> getImplementByUserId(@Path("userId") Long userId);

    @GET("products/{userId}")
    Call<List<Products>> getProductsByUserId(@Path("userId") Long userId);

    @PATCH("product/{id}")
    Call<Products> updateProducts(@Path("id") long id, @Body Products products);

    @DELETE("product/{id}")
    Call<Products> deleteProduct(@Path("id") long id);

//    @Multipart
//    @PUT("product/{id}")
//    Call<Products> updateProduct(@Path("id") long id, @Body Products products); @Multipart

    @Multipart
    @PUT("product/{id}")
    Call<Products> updateProduct(@Path("id") Long productId,
                                 @Part("productName") RequestBody productName,
                                 @Part("price") RequestBody price,
                                 @Part("quantity") RequestBody quantity,
                                 @Part("productCategory") RequestBody productCategory
    );
}

