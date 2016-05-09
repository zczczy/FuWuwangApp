package com.zczczy.leo.fuwuwangapp;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.baidu.mapapi.SDKInitializer;
import com.zczczy.leo.fuwuwangapp.model.AdvertModel;
import com.zczczy.leo.fuwuwangapp.model.GoodsTypeModel;
import com.zczczy.leo.fuwuwangapp.model.LotteryConfig;
import com.zczczy.leo.fuwuwangapp.model.NewBanner;
import com.zczczy.leo.fuwuwangapp.service.LocationService;

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


    //服务商品类型
    private List<AdvertModel> serviceAdvertModelList;

    //服务商品类型
    private List<GoodsTypeModel> serviceGoodsTypeModelList;


    //首页广告
    private List<AdvertModel> advertModelList;

    //首页banner
    private List<NewBanner> newBannerList;

    //首页中奖配置
    private LotteryConfig lotteryConfig;

    //首页商品类型
    private List<GoodsTypeModel> goodsTypeModelList;

    private boolean isFirst;

    //百度地图
    public LocationService locationService;
    public Vibrator mVibrator;

    @AfterInject
    void afterInject() {
//        CrashReport.initCrashReport(getApplicationContext(), "900019033", false);
        advertModelList = new ArrayList<>(9);
        newBannerList = new ArrayList<>();
        goodsTypeModelList = new ArrayList<>(9);
        lotteryConfig = new LotteryConfig();
        serviceAdvertModelList = new ArrayList<>(9);
        //百度地图
        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());
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

    public List<AdvertModel> getServiceAdvertModelList() {
        return serviceAdvertModelList;
    }

    public void setServiceAdvertModelList(List<AdvertModel> serviceAdvertModelList) {
        this.serviceAdvertModelList = serviceAdvertModelList;
    }

    public List<GoodsTypeModel> getServiceGoodsTypeModelList() {
        return serviceGoodsTypeModelList;
    }

    public void setServiceGoodsTypeModelList(List<GoodsTypeModel> serviceGoodsTypeModelList) {
        this.serviceGoodsTypeModelList = serviceGoodsTypeModelList;
    }
}
