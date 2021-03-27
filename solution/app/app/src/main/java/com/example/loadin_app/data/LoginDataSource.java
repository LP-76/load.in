package com.example.loadin_app.data;

import android.accounts.AuthenticatorException;

import com.example.loadin_app.data.model.LoggedInUser;
import com.example.loadin_app.data.services.BaseServiceUrlProvider;
import com.example.loadin_app.data.services.UserServiceImpl;

import java.io.IOException;

import odu.edu.loadin.common.User;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<User> login(String username, String password) {

        UserServiceImpl userService = new UserServiceImpl(BaseServiceUrlProvider.getCurrentConfig());
        UserServiceImpl.LoginResult result = null;

        try{
            result = userService.login(username, password);

            switch(result.status){
                case InvalidPassword:
                    return new Result.Error(new AuthenticatorException("Password is incorrect"));

                case NotFound:
                    return new Result.Error(new AuthenticatorException("User not found"));

                case Ok:
                    return new Result.Success<User>(result.userProfile);
            }


        }catch(Exception ex){
            return new Result.Error(new IOException("Error logging in", ex));
        }
//        try {
//            // TODO: handle loggedInUser authentication
//            LoggedInUser fakeUser =
//                    new LoggedInUser(
//                            java.util.UUID.randomUUID().toString(),
//                            "Jane Doe");
//            return new Result.Success<>(fakeUser);
//        } catch (Exception e) {
//            return new Result.Error(new IOException("Error logging in", e));
//        }
        return new Result.Error(new Exception("Unknown status returned"));
    }

    public void logout() {
        // TODO: revoke authentication
    }
}