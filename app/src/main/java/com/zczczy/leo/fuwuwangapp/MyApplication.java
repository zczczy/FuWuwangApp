package com.zczczy.leo.fuwuwangapp;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EApplication;

/**
 * Created by Leo on 2016/4/27.
 */
@EApplication
public class MyApplication extends Application {

    public static final String URL = "http://124.254.56.58:8007/";

    public static final String LOTTERYDIST = "LotteryDist";

    public static final String DETAILPAGE = "DetailPage/";

    public static final Integer PAGECOUNT = 10;

    @AfterInject
    void afterInject(){
        CrashReport.initCrashReport(getApplicationContext(), "900019033", false);
    }

}
