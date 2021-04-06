package com.example.loadin_app.data.services;


import java.util.List;
import java.util.concurrent.CompletableFuture;

import odu.edu.loadin.common.Feedback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface RetroFeedbackService {

    @GET("/feedbackservice/feedback/{userID}")
    @Headers( "accept: application/json" )
    CompletableFuture<DataWrapper<Feedback>> getFeedback(@Path("userID") int userID);

    @POST("/feedbackservice/feedback")
    @Headers({
            "accept: application/json",
            "contentType: application/json"
    })
    CompletableFuture<DataWrapper<Feedback>> addFeedback(@Body DataWrapper<Feedback> feedBack);

}

