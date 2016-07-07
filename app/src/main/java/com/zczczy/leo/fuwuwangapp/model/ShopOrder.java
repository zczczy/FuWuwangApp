package com.zczczy.leo.fuwuwangapp.model;

import com.tencent.mm.sdk.modelpay.PayReq;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leo on 2016/5/6.
 */
public class ShopOrder implements Serializable {


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

    public String BuyCardIds;
    public String StoreInfoId; //店铺id
    public String StoreName; // 店铺名称
    public int MPaymentType;
    public int MorderStatus; //订单状态(0:待支付，1：已支付，2:已取消,3：已发货4:确认收货,5:交易完成)
    public int MReceiptAddressId;
    public int AreaId;//
    public String AreaName;//
    public String CityName;//
    public String ProvinceName;//
    public String ShrName; //收货人
    public String Lxdh;  //联系电话
    public String DetailAddress; // 收货地址
    public String Postage; //运费
    public int GoodsAllCount;//商品数量
    public double MOrderMoney; //订单金额
    public int GoodsAllLbCount; //龙币金额
    public double MOrderDzb; //电子币金额
    public double MaxDzb; //电子币金额

    public CouponModel FanQuan;
    public UnionPay unionPay; //银联
    public String AlipayInfo;//支付宝


    public String MOrderId; //订单id
    public String UserInfoId; //用户id
    public String MOrderNo;  //订单号

    public PayReq WxPayData;
    public List<OrderDetailModel> OrderDetailList;

    public MReceiptAddressModel MReceiptAddress;

    public String GoodsType; //1.服务类  2. 邮寄类

    public String DeverKbn;

    public int SellerType;


}
