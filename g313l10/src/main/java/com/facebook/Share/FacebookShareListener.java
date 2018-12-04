package com.facebook.Share;

import com.facebook.FacebookException;
import com.facebook.share.Sharer;

/**
 * Created by Administrator on 2018/12/3.
 */

public interface FacebookShareListener {
    void ShareSucces(Sharer.Result result);
    void ShareCancel();
    void ShareError(FacebookException error);
    void ShareShowError();
}
