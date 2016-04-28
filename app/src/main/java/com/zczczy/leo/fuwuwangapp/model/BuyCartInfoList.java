package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;

/**
 * Created by Leo on 2016/4/27.
 */
public class BuyCartInfoList implements Serializable{

    /**
     * BuyCartInfoId : 1
     * GoodsInfoId : sample string 2
     * UserInfoId : sample string 3
     * CreateTime : sample string 4
     * ProductCount : 5
     * GoodsImgSl : sample string 6
     * GoodsPrice : 7.0
     * GoodsLBPrice : 8
     * GodosName : sample string 9
     */

    private int BuyCartInfoId;
    private String GoodsInfoId;
    private String UserInfoId;
    private String CreateTime;
    private int ProductCount;
    private String GoodsImgSl;
    private double GoodsPrice;
    private int GoodsLBPrice;
    private String GodosName;

    public int getBuyCartInfoId() {
        return BuyCartInfoId;
    }

    public void setBuyCartInfoId(int BuyCartInfoId) {
        this.BuyCartInfoId = BuyCartInfoId;
    }

    public String getGoodsInfoId() {
        return GoodsInfoId;
    }

    public void setGoodsInfoId(String GoodsInfoId) {
        this.GoodsInfoId = GoodsInfoId;
    }

    public String getUserInfoId() {
        return UserInfoId;
    }

    public void setUserInfoId(String UserInfoId) {
        this.UserInfoId = UserInfoId;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public int getProductCount() {
        return ProductCount;
    }

    public void setProductCount(int ProductCount) {
        this.ProductCount = ProductCount;
    }

    public String getGoodsImgSl() {
        return GoodsImgSl;
    }

    public void setGoodsImgSl(String GoodsImgSl) {
        this.GoodsImgSl = GoodsImgSl;
    }

    public double getGoodsPrice() {
        return GoodsPrice;
    }

    public void setGoodsPrice(double GoodsPrice) {
        this.GoodsPrice = GoodsPrice;
    }

    public int getGoodsLBPrice() {
        return GoodsLBPrice;
    }

    public void setGoodsLBPrice(int GoodsLBPrice) {
        this.GoodsLBPrice = GoodsLBPrice;
    }

    public String getGodosName() {
        return GodosName;
    }

    public void setGodosName(String GodosName) {
        this.GodosName = GodosName;
    }
}
