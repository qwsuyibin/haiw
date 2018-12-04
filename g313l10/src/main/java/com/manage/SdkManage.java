package com.manage;

import android.app.Activity;
import android.content.Intent;

import com.facebook.Share.FacebookShare;
import com.facebook.logger.FacebookLogger;
import com.facebook.login.FacebookLogin;
import com.google.login.GoogleLogin;
import com.google.pay.GooglePay;
import com.google.pay.GooglePayListener;

/**
 * Created by Administrator on 2018/11/30.
 */

public class SdkManage {
    private Activity mainActivity;
    private GoogleLogin googleLogin;
    private GooglePay googlePay;
    private FacebookLogin facebookLogin;
    private FacebookLogger facebookLogger;
    private FacebookShare facebookShare;
    public SdkManage(Activity activity)
    {
        mainActivity = activity;
    }
    /**GOOGLELOGI
     * 返回登录类 带登录（Login）和退出登录方法（LoginOut）
     * @return GoogleLogin
     */
    public GoogleLogin initGoogleLogin()
    {
        googleLogin = new GoogleLogin(mainActivity);
        return googleLogin;
    }
    /**
     * GooglePay
     * 返回谷歌充值带充值(Pay)
     * @return FacebookShare
     */
    public GooglePay initGooglePay(GooglePayListener plistener)
    {
        googlePay = new GooglePay(mainActivity,plistener);
        return googlePay;
    }
    /**
     * FaceBookLogin
     * 返回登录类 带登录（Login）和退出登录方法（LoginOut）
     * @return FacebookLogin
     */
    public FacebookLogin initFaceBookLogin()
    {
        facebookLogin = new FacebookLogin(mainActivity);
        return facebookLogin;
    }
    /**
     * FacebookLogger
     * 返回脸书记录
     * @return FacebookLogger
     */
    public FacebookLogger initFaceBookLogger()
    {
        facebookLogger = new FacebookLogger(mainActivity);
        return facebookLogger;
    }
    /**
     * FacebookShare
     * 返回脸书分享
     * @return FacebookShare
     */
    public FacebookShare initFaceBookShare()
    {
        facebookShare = new FacebookShare(mainActivity);
        return facebookShare;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(googleLogin != null)
            googleLogin.onActivityResult(requestCode,resultCode,data);
        if(googlePay != null)
            googlePay.onActivityResult(requestCode,resultCode,data);
        if(facebookLogin != null)
            facebookLogin.onActivityResult(requestCode,resultCode,data);
    }
}
