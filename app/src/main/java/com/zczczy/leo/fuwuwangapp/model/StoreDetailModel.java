package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Leo on 2016/5/5.
 */
public class StoreDetailModel implements Serializable {


    /**
     * StoreInfoId : sample string 1
     * StreetInfoId : 2
     * SellerInfoId : 3
     * StoreName : sample string 4
     * StoreDesc : sample string 5
     * StoreImgSl : sample string 6
     * StoreStatus : 7
     * CreateTime : sample string 8
     * LinkTel : sample string 9
     * StoreAddress : sample string 10
     * SearchKeyWordf : sample string 11
     * StorePX : 12
     */

    public String StoreInfoId;
    public int StreetInfoId;
    public int SellerInfoId;
    public String StoreName;
    public String StoreDesc;
    public String StoreImgSl;
    public int StoreStatus;
    public String CreateTime;
    public String LinkTel;
    public String StoreAddress;
    public String SearchKeyWordf;
    public int StorePX;
    public List<StoreImg> StoreImgList;
    public List<Goods> GoodsList;

    public String provinceName;
    public String CityName;
    public String AreaName;
    public String StreetName;
    public String CheckUser;
}
