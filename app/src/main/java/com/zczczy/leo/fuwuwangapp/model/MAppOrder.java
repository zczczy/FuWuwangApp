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

    public String MOrderId; //订单id
    public String UserInfoId; //用户id
    public String MOrderNo;  //订单号
    public int MorderStatus; //订单状态(0:待支付，1：已支付，2:已取消,3：已发货4:确认收货,5:交易完成)
    public String ShrName; //收货人
    public String Lxdh;  //联系电话
    public String DetailAddress; // 收货地址
    public String StoreInfoId; //店铺id
    public String StoreName; // 店铺名称
    public double Postage; //运费
    public double MOrderMoney; //订单金额
    public int MOrderLbCount; //龙币金额
    public double MOrderDzb; //电子币金额
    public String chrCode; //特征码
    public String transId; //银联编号
    public String merSign;//银联签名
    public int GoodsAllCount;//商品数量
    public List<OrderDetailModel> MOrderDetailList;

    public CouponModel FanQuan;
}
