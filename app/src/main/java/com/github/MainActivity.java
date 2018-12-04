package com.github;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* final SdkManage sdkManage = new SdkManage(this);
        FacebookLogin fblogin = sdkManage.initFaceBookLogin();
        fblogin.Login(new FacebookLoginListener() {
            @Override
            public void LoginSucces(LoginResult account) {
                Log.i("hahahahah", "成功用户名是:" + account.getAccessToken());
            }

            @Override
            public void LoginCancel() {
                Log.i("hahahahah", "失败");
            }

            @Override
            public void LoginError(FacebookException error) {
                Log.i("hahahahah", "错误"+error.toString());
            }
        });*/
    }
}
