package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;

/**
 * Created by Leo on 2016/4/27.
 */
public class AdvertModel implements Serializable {

    /**
     * AdvertId : sample string 1
     * AdsenseTypeId : 2
     * AdvertImg : sample string 3
     * InfoId : sample string 4
     * AdvertStatus : 5
     * CreateTime : sample string 6
     * JumpType: 1 店铺 2商品
     *
     */

    public String AdvertId;
    public int AdsenseTypeId;
    public String AdvertImg;
    public String InfoId;
    public int AdvertStatus;
    public String CreateTime;
    public String AdsenseTypeName;
    public int JumpType; //1 店铺 2商品
}
