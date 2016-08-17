package com.zczczy.leo.fuwuwangapp.rest;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

import com.alipay.sdk.app.PayTask;
import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.AdvertModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.GoodsTypeModel;
import com.zczczy.leo.fuwuwangapp.model.LotteryConfig;
import com.zczczy.leo.fuwuwangapp.model.NewBanner;
import com.zczczy.leo.fuwuwangapp.model.PayResult;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.tools.Constants;

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

import java.util.ArrayList;
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
        afterGetAdvertByKbn(myRestClient.getAdvertByKbn(Constants.NORMAL_CATEGORY));
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
     * 21首页弹出广告
     */
    @Background
    public void getHomePupAd() {
        afterGetHomePupAd(myRestClient.getAdvertByKbn(Constants.HOME_PUP_AD));
    }

    @UiThread
    void afterGetHomePupAd(BaseModelJson<List<AdvertModel>> bmj) {
        if (bmj != null && bmj.Successful) {
            app.setHomePupAd(bmj.Data);
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
        afterGetHomeGoodsTypeList(myRestClient.getGoodsType(Constants.NORMAL_CATEGORY));
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
        afterGetServiceAd(myRestClient.getAdvertByKbn(Constants.SERVICE_CATEGORY));
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
        afterGetServiceGoodsTypeList(myRestClient.getGoodsType(Constants.SERVICE_CATEGORY));
    }

    @UiThread
    void afterGetServiceGoodsTypeList(BaseModelJson<List<GoodsTypeModel>> bmj) {
        if (bmj != null && bmj.Successful) {
            app.setServiceGoodsTypeModelList(bmj.Data);
            for (GoodsTypeModel firstCategory : app.getServiceGoodsTypeModelList()) {
                if (firstCategory.ChildGoodsType == null) {
                    firstCategory.ChildGoodsType = new ArrayList<>();
                }
                GoodsTypeModel secondCategory = new GoodsTypeModel();
                secondCategory.GoodsTypeName = "全部";
                secondCategory.GoodsTypeId = firstCategory.GoodsTypeId;
                secondCategory.GoodsTypePid = firstCategory.GoodsTypeId + "";
                firstCategory.ChildGoodsType.add(0, secondCategory);
            }
        } else {
            bmj = new BaseModelJson<>();
        }
        bus.post(bmj);
    }


    @Background
    public void aliPay(String payInfo, Activity activity, String orderId) {
        PayTask aliPay = new PayTask(activity);
        afterAliPay(aliPay.pay(payInfo, true), activity, orderId);
    }

    @UiThread
    void afterAliPay(String result, Activity activity, String orderId) {
        AndroidTool.dismissLoadDialog();
        PayResult payResult = new PayResult(result);
        payResult.setOrderId(orderId);


        /**
         * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
         * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
         * docType=1) 建议商户依赖异步通知
         */
        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
        String resultStatus = payResult.getResultStatus();
        bus.post(payResult);
    }

}
