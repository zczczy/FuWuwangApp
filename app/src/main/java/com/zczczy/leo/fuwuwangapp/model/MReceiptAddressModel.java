package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;

/**
 * Created by Leo on 2016/5/4.
 */
public class MReceiptAddressModel implements Serializable {


    /**
     * MReceiptAddressId : 1
     * AreaId : 2
     * UserInfoId : sample string 3
     * ReceiptName : sample string 4
     * DetailAddress : sample string 5
     * IsPrimary : sample string 6
     * Mobile : sample string 7
     * CityId : 8
     * ProvinceId : 9
     * ProvinceName : sample string 10
     * CityName : sample string 11
     * AreaName : sample string 12
     */

    public int MReceiptAddressId;
    public int AreaId;
    public String UserInfoId;
    public String ReceiptName;
    public String DetailAddress;
    public String IsPrimary;
    public String Mobile;
    public int CityId;
    public int ProvinceId;
    public String ProvinceName;
    public String CityName;
    public String AreaName;
}
