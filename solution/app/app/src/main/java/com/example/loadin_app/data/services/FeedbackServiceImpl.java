package com.example.loadin_app.data.services;

import odu.edu.loadin.common.Feedback;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class FeedbackServiceImpl extends LoadInServiceWithAuthImpl {
    RetroFeedbackService retroService;

    public FeedbackServiceImpl(String username, String password){
        this(BaseServiceUrlProvider.Config.LOCAL, username, password);
    }
    public FeedbackServiceImpl(BaseServiceUrlProvider.Config config, String username, String password){
        super(config, username, password);
        retroService = retrofitContext.create(RetroFeedbackService.class);
    }
    public Feedback getFeedback(int userID) throws ExecutionException, InterruptedException
    {
        CompletableFuture<DataWrapper<Feedback>> newFeedBack = retroService.getFeedback(userID);
        return newFeedBack.get().Data;
    }
    public Feedback addFeedback(Feedback feedback) throws ExecutionException, InterruptedException {
        DataWrapper<Feedback> wrapper = new DataWrapper<>();
        wrapper.Data = feedback;
        CompletableFuture<DataWrapper<Feedback>> addPromise = retroService.addFeedback(wrapper);
        Feedback result = addPromise.get().Data;
        return result;
    }


}
