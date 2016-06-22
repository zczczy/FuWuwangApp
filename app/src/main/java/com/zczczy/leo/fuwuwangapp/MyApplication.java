package com.zczczy.leo.fuwuwangapp;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.baidu.mapapi.SDKInitializer;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zczczy.leo.fuwuwangapp.model.AdvertModel;
import com.zczczy.leo.fuwuwangapp.model.GoodsTypeModel;
import com.zczczy.leo.fuwuwangapp.model.LotteryConfig;
import com.zczczy.leo.fuwuwangapp.model.NewArea;
import com.zczczy.leo.fuwuwangapp.model.NewBanner;
import com.zczczy.leo.fuwuwangapp.model.StreetInfo;
import com.zczczy.leo.fuwuwangapp.service.LocationService;
import com.zczczy.leo.fuwuwangapp.tools.Constants;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 2016/4/27.
 */
@EApplication
public class MyApplication extends Application {


    //服务商品类型
    private List<AdvertModel> serviceAdvertModelList;


    //服务页商品类型 包括二级分类
    private List<GoodsTypeModel> serviceGoodsTypeModelList;

//    //服务商品类型
//    private List<GoodsTypeModel> firstCategoryList;

    //一级分类
    private GoodsTypeModel firstCategory;
    //二级分类
    private GoodsTypeModel secondCategory;


    private List<NewArea> regionList;
    private NewArea newRegion;
    private StreetInfo newStreet;


    //首页广告
    private List<AdvertModel> advertModelList;

    //首页banner
    private List<NewBanner> newBannerList;

    //首页中奖配置
    private LotteryConfig lotteryConfig;

    //首页商品类型 包括二级菜单
    private List<GoodsTypeModel> goodsTypeModelList;

    private boolean isFirst;

    //百度地图
    public LocationService locationService;
    public Vibrator mVibrator;

    public IWXAPI iWXApi;

    @AfterInject
    void afterInject() {
        advertModelList = new ArrayList<>(9);
        newBannerList = new ArrayList<>();
        goodsTypeModelList = new ArrayList<>(9);
        lotteryConfig = new LotteryConfig();
        serviceAdvertModelList = new ArrayList<>(9);
        serviceGoodsTypeModelList = new ArrayList<>(6);
//        百度地图
        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());

        iWXApi = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
        iWXApi.registerApp(Constants.APP_ID);
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

    public GoodsTypeModel getFirstCategory() {
        return firstCategory;
    }

    public void setFirstCategory(GoodsTypeModel firstCategory) {
        this.firstCategory = firstCategory;
    }

    public GoodsTypeModel getSecondCategory() {
        return secondCategory;
    }

    public void setSecondCategory(GoodsTypeModel secondCategory) {
        this.secondCategory = secondCategory;
    }

    public List<NewArea> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<NewArea> regionList) {
        this.regionList = regionList;
    }

    public NewArea getNewRegion() {
        return newRegion;
    }

    public void setNewRegion(NewArea newRegion) {
        this.newRegion = newRegion;
    }

    public StreetInfo getNewStreet() {
        return newStreet;
    }

    public void setNewStreet(StreetInfo newStreet) {
        this.newStreet = newStreet;
    }

    public List<GoodsTypeModel> getServiceGoodsTypeModelList() {
        return serviceGoodsTypeModelList;
    }

    public void setServiceGoodsTypeModelList(List<GoodsTypeModel> serviceGoodsTypeModelList) {
        this.serviceGoodsTypeModelList = serviceGoodsTypeModelList;
    }
}
