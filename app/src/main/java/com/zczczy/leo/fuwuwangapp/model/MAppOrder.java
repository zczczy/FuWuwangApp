package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leo on 2016/5/6.
 */
public class MAppOrder implements Serializable {


    /**
     * MOrderId : sample string 1
     * UserInfoId : sample string 2
     * MOrderNo : sample string 3
     * MorderStatus : 4
     * ShrName : sample string 5
     * Lxdh : sample string 6
     * DetailAddress : sample string 7
     * StoreInfoId : sample string 8
     * StoreName : sample string 9
     * Postage : 10.0
     * MOrderMoney : 11.0
     * MOrderLbCount : 12
     * MOrderDzb : 13.0
     */

    public String MOrderId;
    public String UserInfoId;
    public String MOrderNo;
    public int MorderStatus;
    public String ShrName;
    public String Lxdh;
    public String DetailAddress;
    public String StoreInfoId;
    public String StoreName;
    public double Postage;
    public double MOrderMoney;
    public int MOrderLbCount;
    public double MOrderDzb;
    public List<BuyCartInfoList> BuyCartInfoList;
}
