package com.google.login;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
/**
 * Created by Administrator on 2018/11/30.
 */

public interface GoogleLoginListener {
    void LoginSucces(GoogleSignInAccount account);
    void LoginError(ApiException error);
}
