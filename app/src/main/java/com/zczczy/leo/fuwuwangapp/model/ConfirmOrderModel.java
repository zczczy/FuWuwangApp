package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Leo on 2016/5/4.
 */
public class ConfirmOrderModel implements Serializable {

    public String StoreInfoId;
    public String StoreName;
    public double Postage; //邮费
    public CouponModel FanQuan; //返券数量
    public double MOrderMoney; //总金额
    public int MOrderLbCount; //订单龙币数量
    public double MOrderDzb; //电子币
    public int GoodsAllCount; //商品总数
    public double MaxDzb; //用户能使用的电子币数
    public int SellerType; //1:服务网商家，2：普通商家，3：商城会员申请商家)
    public UnionPay unionPay; //银联


    public List<BuyCartInfoList> BuyCartInfoList;

    public MReceiptAddressModel MReceiptAddress;
}
