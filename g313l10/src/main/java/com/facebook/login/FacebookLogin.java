package com.facebook.login;

import android.app.Activity;
import android.content.Intent;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.Arrays;

public class FacebookLogin {
    private Activity mainActivity;
    private CallbackManager callbackManager;
    private FacebookLoginListener loginListener;
    public FacebookLogin(Activity activity) {
        mainActivity = activity;
        init();
    }
    private void init()
    {
    }
    public void Login(FacebookLoginListener loginListener)
    {
        this.loginListener = loginListener;
        LoginManager.getInstance().logInWithReadPermissions(mainActivity, Arrays.asList("public_profile", "user_friends"));
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, creatCallback());
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (callbackManager != null)
            callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    private FacebookCallback<LoginResult> creatCallback()
    {
        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                callbackManager = null;
                loginListener.LoginSucces(loginResult);
            }
            @Override
            public void onCancel() {
                callbackManager = null;
                loginListener.LoginCancel();
            }
            @Override
            public void onError(FacebookException exception) {
                callbackManager = null;
                loginListener.LoginError(exception);
            }
        };
    }
    public void LoginOut()
    {
        LoginManager.getInstance().logOut();
    }
}
