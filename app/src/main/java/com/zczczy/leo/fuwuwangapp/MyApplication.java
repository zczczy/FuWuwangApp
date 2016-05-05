package com.zczczy.leo.fuwuwangapp;

import android.app.Application;

import com.zczczy.leo.fuwuwangapp.model.AdvertModel;
import com.zczczy.leo.fuwuwangapp.model.GoodsTypeModel;
import com.zczczy.leo.fuwuwangapp.model.LotteryConfig;
import com.zczczy.leo.fuwuwangapp.model.NewBanner;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 2016/4/27.
 */
@EApplication
public class MyApplication extends Application {

    public static final String URL = "http://124.254.56.58:8007/";

    public static final String LOTTERYDIST = "LotteryDist";

    public static final String DETAILPAGE = "DetailPage/";

    public static final Integer PAGECOUNT = 10;

    public static final String ANDROID = "1";

    public static final String NORMAL = "1";

    public static final String VIP = "2";

    public static final Integer DUEPAYMENT = 0; //0:待支付
    public static final Integer PAID = 1;   //1：已支付
    public static final Integer CANCEL = 2; //2:已取消,
    public static final Integer SEND = 3; //3：已发货
    public static final Integer CONFIRM = 4; //4:确认收货
    public static final Integer FINISH = 5; //5:交易完成


    private List<AdvertModel> advertModelList;

    private List<NewBanner> newBannerList;

    private LotteryConfig lotteryConfig;

    private List<GoodsTypeModel> goodsTypeModelList;

    private boolean isFirst;

    @AfterInject
    void afterInject() {
//        CrashReport.initCrashReport(getApplicationContext(), "900019033", false);
        advertModelList = new ArrayList<>();
        newBannerList = new ArrayList<>();
        goodsTypeModelList = new ArrayList<>();
        lotteryConfig = new LotteryConfig();
    }

    public List<AdvertModel> getAdvertModelList() {
        return advertModelList;
    }

    public void setAdvertModelList(List<AdvertModel> advertModelList) {
        this.advertModelList = advertModelList;
    }

    public List<NewBanner> getNewBannerList() {
        return newBannerList;
    }

    public void setNewBannerList(List<NewBanner> newBannerList) {
        this.newBannerList = newBannerList;
    }

    public LotteryConfig getLotteryConfig() {
        return lotteryConfig;
    }

    public void setLotteryConfig(LotteryConfig lotteryConfig) {
        this.lotteryConfig = lotteryConfig;
    }

    public List<GoodsTypeModel> getGoodsTypeModelList() {
        return goodsTypeModelList;
    }

    public void setGoodsTypeModelList(List<GoodsTypeModel> goodsTypeModelList) {
        this.goodsTypeModelList = goodsTypeModelList;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }
}
