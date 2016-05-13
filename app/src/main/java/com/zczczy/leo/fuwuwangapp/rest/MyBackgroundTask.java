package com.zczczy.leo.fuwuwangapp.rest;

import android.content.Context;
import android.net.ConnectivityManager;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.AdvertModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.GoodsTypeModel;
import com.zczczy.leo.fuwuwangapp.model.LotteryConfig;
import com.zczczy.leo.fuwuwangapp.model.NewBanner;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

/**
 * Created by Leo on 2016/4/29.
 */
@EBean
public class MyBackgroundTask {

    @RootContext
    Context context;

    @StringRes
    String no_net;

    @SystemService
    ConnectivityManager connManager;

    @RestService
    MyDotNetRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    @App
    MyApplication app;

    @Bean
    OttoBus bus;

    @Pref
    MyPrefs_ pre;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    /**
     * 获取首页banner
     */
    @Background
    public void getHomeBanner() {
        afterGetHomeBanner(myRestClient.getHomeBanner());
    }

    @UiThread
    void afterGetHomeBanner(BaseModelJson<List<NewBanner>> bmj) {
        if (bmj != null && bmj.Successful) {
            app.setNewBannerList(bmj.Data);
        } else {
            bmj = new BaseModelJson<>();
        }
        bus.post(bmj);
    }

    /**
     * 获取首页广告
     */
    @Background
    public void getAdvertByKbn() {
        afterGetAdvertByKbn(myRestClient.getAdvertByKbn(MyApplication.NORMAL_CATEGORY));
    }

    @UiThread
    void afterGetAdvertByKbn(BaseModelJson<List<AdvertModel>> bmj) {
        if (bmj != null && bmj.Successful) {
            app.setAdvertModelList(bmj.Data);
        } else {
            bmj = new BaseModelJson<>();
        }
        bus.post(bmj);
    }

    /**
     * 获取首页中奖通知
     */
    @Background
    public void getLotteryConfigInfo() {
        afterGetLotteryConfigInfo(myRestClient.getLotteryConfigInfo());
    }

    @UiThread
    void afterGetLotteryConfigInfo(BaseModelJson<LotteryConfig> bmj) {
        if (bmj != null && bmj.Successful) {
            app.setLotteryConfig(bmj.Data);
        } else {
            bmj = new BaseModelJson<>();
        }
        bus.post(bmj);
    }

    /**
     * 获取首页类别
     */
    @Background
    public void getHomeGoodsTypeList() {
        afterGetHomeGoodsTypeList(myRestClient.getGoodsType(MyApplication.NORMAL_CATEGORY));
    }

    @UiThread
    void afterGetHomeGoodsTypeList(BaseModelJson<List<GoodsTypeModel>> bmj) {
        if (bmj != null && bmj.Successful) {
            app.setGoodsTypeModelList(bmj.Data);
        } else {
            bmj = new BaseModelJson<>();
        }
        bus.post(bmj);
    }

    /**
     * 获取服务页面广告
     */
    @Background
    public void getServiceAd() {
        afterGetServiceAd(myRestClient.getAdvertByKbn(MyApplication.SERVICE_CATEGORY));
    }

    @UiThread
    void afterGetServiceAd(BaseModelJson<List<AdvertModel>> bmj) {
        if (bmj != null && bmj.Successful) {
            app.setServiceAdvertModelList(bmj.Data);
        } else {
            bmj = new BaseModelJson<>();
        }
        bus.post(bmj);
    }


    /**
     * 获取服务类别
     */
    @Background
    public void getServiceGoodsTypeList() {
        afterGetServiceGoodsTypeList(myRestClient.getGoodsType(MyApplication.SERVICE_CATEGORY));
    }

    @UiThread
    void afterGetServiceGoodsTypeList(BaseModelJson<List<GoodsTypeModel>> bmj) {
        if (bmj != null && bmj.Successful) {
            app.setServiceGoodsTypeModelList(bmj.Data);
        } else {
            bmj = new BaseModelJson<>();
        }
        bus.post(bmj);
    }
}
