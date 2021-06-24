package com.example.cekongkir.data.service;

import com.example.cekongkir.data.model.city.ResponseCity;
import com.example.cekongkir.data.model.cost.ResponseCost;
import com.example.cekongkir.utils.Constants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiEnpoint {
    @GET("city")
    Call<ResponseCity> getCity(@Header("key") String API_KEY);

    @FormUrlEncoded
    @POST("cost")
    Call<ResponseCost> postCost(
        @Field("origin") String origin,
        @Field("destination") String destination,
        @Field("weight") int weight,
        @Field("courier") String courier,
        @Header("key") String API_KEY
    );
}
