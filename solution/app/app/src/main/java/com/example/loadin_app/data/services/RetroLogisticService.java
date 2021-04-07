package com.example.loadin_app.data.services;




import com.example.loadin_app.data.services.logisticalthings.Example;
import com.example.loadin_app.data.services.logisticalthings.ResourceSet;
import com.example.loadin_app.data.services.logisticalthings.RouteLeg;

import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface RetroLogisticService {

    @GET
    @Headers( "accept: application/json" )
    Call<Example> getExampleSet (@Url String requestURL);


}