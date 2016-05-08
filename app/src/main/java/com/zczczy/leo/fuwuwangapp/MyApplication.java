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

    public static final String PAY_URL = "http://116.228.21.162:9127/umsFrontWebQmjf/umspay";
    //正式环境
//    public static final String PAY_URL = "https://mpos.quanminfu.com:8018/umsFrontWebQmjf/umspay";


    public static final String LOTTERYDIST = "LotteryDist";

    public static final String DETAILPAGE = "DetailPage/";

    public static final Integer PAGE_COUNT = 10;

    public static final String ANDROID = "1";

    public static final String NORMAL = "1";

    public static final String VIP = "2";

    public static final String ASC = "asc"; //asc升序
    public static final String DESC = "desc"; //desc降序
    public static final int DEFUALT_SORT = 0; //排序（0 默认（推荐降序加时间升序/降序）,1 价格,2 销量）
    public static final int PRICE_SORT = 0; //排序（0 默认（推荐降序加时间升序/降序）,1 价格,2 销量）
    public static final int COUNT_SORT = 0; //排序（0 默认（推荐降序加时间升序/降序）,1 价格,2 销量）

    public static final int STORE_GOODS = 0; // 店铺入口
    public static final int SEARCH_GOODS = 1; // 搜索入口


    public static final int DUEPAYMENT = 0; //0:待支付
    public static final int PAID = 1;   //1：已支付
    public static final int CANCEL = 2; //2:已取消,
    public static final int SEND = 3; //3：已发货
    public static final int CONFIRM = 4; //4:确认收货
    public static final int FINISH = 5; //5:交易完成
    public static final int ALL_ORDER = 9; //5:交易完成

    public static final int UMSPAY = 1; //1:网银
    public static final int DZB = 2;   //2电子币
    public static final int LONG_BI = 3; //3龙币
    public static final int DZB_LONGBI = 4; //4电子币+龙币
    public static final int DZB_UMSPAY = 5; //5电子币+网银
    public static final int LONGBI_UMSPAY = 6; //6龙币+网银
    public static final int LONGBI_UMSPAY_DZB = 7; //7电子币+龙币+网银


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
