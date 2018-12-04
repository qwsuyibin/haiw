package com.google.pay;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.google.pay.googlepay.IabHelper;
import com.google.pay.googlepay.IabResult;
import com.google.pay.googlepay.Inventory;
import com.google.pay.googlepay.Purchase;
import com.manage.RCEnum;

public class GooglePay {
    private Activity mainActivity;
    private IabHelper mHelper;
    private String base64EncodedPublicKey = "";
    private Inventory unusedInv;
    private String TAG = "GooglePay";
    private GooglePayListener payListener;
    public GooglePay(Activity activity,GooglePayListener plistener) {
        payListener = plistener;
        mainActivity = activity;
        init();
    }
    private void init()
    {
        ApplicationInfo ai = null;
        try {
            ai = mainActivity.getPackageManager().getApplicationInfo(mainActivity.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            base64EncodedPublicKey = bundle.getString("publickey");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if(base64EncodedPublicKey == "")
        {
            Log.i(TAG,"base64EncodedPublicKey未配置");
            return;
        }
        GooglePayOnCreate();
    }
    public void Pay(String pid)
    {
        strarPay(pid,"");
    }
    public void Pay(String pid,String extra)
    {
        strarPay(pid,extra);
    }
    private void strarPay(String pid,String extra) {
        try {
            mHelper.launchPurchaseFlow(mainActivity, pid, RCEnum.E_GOOGLE_PAY, mPurchaseFinishedListener, extra);
        } catch (Exception e) {
        }
    }

    public void GooglePayOnCreate() {
        try {
            mHelper = new IabHelper(mainActivity, base64EncodedPublicKey);
            mHelper.enableDebugLogging(true);
            mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                @Override
                public void onIabSetupFinished(IabResult result) {
                    if (!result.isSuccess()) {
                        // 设备不支持billing支付功能
                        Log.i(TAG,"GooglePay设备不支持billing支付功能: " + result);
                        return;
                    }
                    Log.i(TAG,"GooglePay Setup successful. Querying inventory.");
                    try {
                        //必须查询是否有未消费的订单
                        mHelper.queryInventoryAsync(mGotInventoryListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Error e) {
            e.printStackTrace();
        }
    }

    private IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            if (result.isFailure()) {
                //购买商品失败
                Log.i(TAG,"GooglePay BuyError=========================>" + result.isFailure());
                return;
            }
            Log.i(TAG,"GooglePay =========================>" + purchase.getSku());
            try {
                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
            } catch (IabHelper.IabAsyncInProgressException e) {
                // TODO Auto-generated catchblock
                e.printStackTrace();
            }

            //购买成功，开始消费
            Log.i(TAG,"GooglePay 购买成功");
            payListener.PayOver(purchase);
        }
    };

    //消耗完成的回调
    private IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.i(TAG,"GooglePay Consumption finished.Purchase: " + purchase + ", result: " + result);
            if (result.isSuccess()) {
                //消费成功
                Log.i(TAG,"GooglePay Consumptionsuccessful. Provisioning.");
            } else {
                //消费失败
                Log.i(TAG,"GooglePay Error whileconsuming: " + result);
            }
        }
    };
    //查询未完成订单的回调
    private IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            if (result.isFailure()) {
                //查询库存失败
                Log.i(TAG,"GooglePay Failed to queryinventory: " + result);
                return;
            }
            unusedInv = inventory;
        }
    };
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mHelper.handleActivityResult(requestCode, resultCode, data);
    }
}
