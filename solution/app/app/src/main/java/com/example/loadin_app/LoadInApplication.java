package com.example.loadin_app;

import android.app.Application;

import odu.edu.loadin.common.User;

public class LoadInApplication extends Application {

    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
