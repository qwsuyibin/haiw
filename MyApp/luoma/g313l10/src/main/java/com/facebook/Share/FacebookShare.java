package com.facebook.Share;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.MessageDialog;
import com.facebook.share.widget.ShareDialog;

/**
 * Created by Administrator on 2018/12/3.
 */

public class FacebookShare {
    private Activity mainActivity;
    private CallbackManager callbackManagerfx;
    private CallbackManager callbackManagerFriend;
    private FacebookShareListener shareListener;
    private FacebookShareFriendListener friendShareListener;
    public FacebookShare(Activity activity)
    {
        mainActivity = activity;
        init();
    }
    private void init()
    {
    }
    public void Share(FacebookShareListener sListener,String contentUrl)
    {
        this.shareListener = sListener;
        callbackManagerfx = CallbackManager.Factory.create();
        ShareDialog shareDialog = new ShareDialog(mainActivity);
        shareDialog.registerCallback(callbackManagerfx,creatSharerCallback());
        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(contentUrl))
                .build();
        shareDialog.show(linkContent);
    }
    public void Share(FacebookShareListener sListener,byte[] arg)
    {
        this.shareListener = sListener;
        callbackManagerfx = CallbackManager.Factory.create();
        ShareDialog shareDialog = new ShareDialog(mainActivity);
        shareDialog.registerCallback(callbackManagerfx,creatSharerCallback());
        Bitmap image = BitmapFactory.decodeByteArray(arg, 0, arg.length);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        if (shareDialog.canShow(content))
            shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
        else
        {
            callbackManagerfx = null;
            shareListener.ShareShowError();
        }
    }
    public void ShareFriend(FacebookShareFriendListener sfListener,byte[] arg) {

        this.friendShareListener = sfListener;
        callbackManagerFriend = CallbackManager.Factory.create();
        MessageDialog messageDialog = new MessageDialog(mainActivity);
        messageDialog.registerCallback(callbackManagerFriend,creatFriendSharerCallback() );
        Bitmap image = BitmapFactory.decodeByteArray(arg, 0, arg.length);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        if (messageDialog.canShow(content))
            messageDialog.show(content);
        else
        {
            callbackManagerFriend = null;
            friendShareListener.ShareShowError();
        }

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (callbackManagerfx != null)
            callbackManagerfx.onActivityResult(requestCode, resultCode, data);
        if (callbackManagerFriend != null)
            callbackManagerFriend.onActivityResult(requestCode, resultCode, data);
    }
    private FacebookCallback<Sharer.Result> creatSharerCallback()
    {
        return new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                callbackManagerfx = null;
                shareListener.ShareSucces(result);
            }

            @Override
            public void onCancel() {
                callbackManagerfx = null;
                shareListener.ShareCancel();
            }

            @Override
            public void onError(FacebookException error) {
                callbackManagerfx = null;
                shareListener.ShareError(error);
            }
        };
    }
    private FacebookCallback<Sharer.Result> creatFriendSharerCallback()
    {
        return new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                callbackManagerFriend = null;
                friendShareListener.ShareSucces(result);
            }

            @Override
            public void onCancel() {
                callbackManagerFriend = null;
                friendShareListener.ShareCancel();
            }

            @Override
            public void onError(FacebookException error) {
                callbackManagerFriend = null;
                friendShareListener.ShareError(error);
            }
        };
    }
}
