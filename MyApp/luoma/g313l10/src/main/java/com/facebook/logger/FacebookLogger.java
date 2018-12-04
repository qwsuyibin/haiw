package com.facebook.logger;

import android.app.Activity;
import android.os.Bundle;

import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by Administrator on 2018/12/3.
 */

public class FacebookLogger {
    private Activity mainActivity;
    private AppEventsLogger fblogger;
    public FacebookLogger(Activity activity) {
        mainActivity = activity;
        init();
    }
    private void init()
    {
        fblogger = AppEventsLogger.newLogger(mainActivity);
    }
    public void Logger(String name)
    {
        fblogger.logEvent(name);
    }
    public void Logger(String name,Bundle param)
    {
        fblogger.logEvent(name,param);
    }
    /**
     项目中常用点击充值的记录
     */
    public void LoggerClickPay(String name,String pid,String pmoney)
    {
        Bundle parameters = new Bundle();
        parameters.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, pmoney);
        parameters.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, "1");
        parameters.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, pid);
        fblogger.logEvent(name,parameters);
    }
    /**
     项目中常用充值结束的记录
     */
    public void LoggerPayOver(String name,String pid,String pmoney,String currency)
    {
        Bundle parameters = new Bundle();
        parameters.putString(AppEventsConstants.EVENT_PARAM_NUM_ITEMS,"1");
        parameters.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, currency);
        parameters.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, "1");
        parameters.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, pid);
        fblogger.logEvent(name,Double.valueOf(pmoney),parameters);
    }
}
