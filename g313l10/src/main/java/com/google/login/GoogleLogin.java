package com.google.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.manage.RCEnum;

public class GoogleLogin implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {
    private Activity mainActivity;
    private GoogleSignInClient mGoogleApiClient;
    private GoogleLoginListener loginListener;
    private String TAG = "GoogleLogin";
    public GoogleLogin(Activity activity) {
        mainActivity = activity;
        init();
    }
    private void init()
    {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = GoogleSignIn.getClient(mainActivity, gso);
    }
    public void Login(GoogleLoginListener loginListener)
    {
        this.loginListener = loginListener;
        Intent signInIntent = mGoogleApiClient.getSignInIntent();
        mainActivity.startActivityForResult(signInIntent, RCEnum.E_GOOGLE_SIGN_IN);
    }
    public void LoginOut()
    {
        mGoogleApiClient.signOut();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "google登录-->onConnected,bundle==" + bundle);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "google登录-->onConnectionSuspended,i==" + i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "google登录-->onConnectionFailed,connectionResult==" + connectionResult);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RCEnum.E_GOOGLE_SIGN_IN) {
            Log.i(TAG, "requestCode==" + requestCode + ",resultCode==" + resultCode + ",data==" + data);
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        Log.i(TAG, "handleSignInResult:" + completedTask);
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.i(TAG, "成功用户名是:" + account.getId());
            loginListener.LoginSucces(account);
        } catch (ApiException e) {
            e.printStackTrace();
            Log.i(TAG, "没有成功" + e.toString());
            loginListener.LoginError(e);
        }
    }
}
