package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;

/**
 * Created by Leo on 2016/4/27.
 */
public class BuyCartInfoList implements Serializable {

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

    public int BuyCartInfoId;
    public String GoodsInfoId;
    public String UserInfoId;
    public String CreateTime;
    public int ProductCount;
    public String GoodsImgSl;
    public double GoodsPrice;
    public int GoodsLBPrice;
    public String GodosName;
    public String XfNo;
    public String GoodsType; //1:服务类，2：邮寄类
    public String XfStatusDisp; //消费状态描述
}
