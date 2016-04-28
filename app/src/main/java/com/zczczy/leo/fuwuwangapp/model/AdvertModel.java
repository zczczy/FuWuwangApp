package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;

/**
 * Created by Leo on 2016/4/27.
 */
public class AdvertModel implements Serializable{

    /**
     * AdvertId : sample string 1
     * AdsenseTypeId : 2
     * AdvertImg : sample string 3
     * InfoId : sample string 4
     * AdvertStatus : 5
     * CreateTime : sample string 6
     */

    private String AdvertId;
    private int AdsenseTypeId;
    private String AdvertImg;
    private String InfoId;
    private int AdvertStatus;
    private String CreateTime;

    public String getAdvertId() {
        return AdvertId;
    }

    public void setAdvertId(String AdvertId) {
        this.AdvertId = AdvertId;
    }

    public int getAdsenseTypeId() {
        return AdsenseTypeId;
    }

    public void setAdsenseTypeId(int AdsenseTypeId) {
        this.AdsenseTypeId = AdsenseTypeId;
    }

    public String getAdvertImg() {
        return AdvertImg;
    }

    public void setAdvertImg(String AdvertImg) {
        this.AdvertImg = AdvertImg;
    }

    public String getInfoId() {
        return InfoId;
    }

    public void setInfoId(String InfoId) {
        this.InfoId = InfoId;
    }

    public int getAdvertStatus() {
        return AdvertStatus;
    }

    public void setAdvertStatus(int AdvertStatus) {
        this.AdvertStatus = AdvertStatus;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }
}
