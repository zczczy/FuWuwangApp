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
    public String RetTicketNum; //返券数量
    public double MOrderMoney; //
    public int MOrderLbCount; //订单龙币数量
    public double MOrderDzb; //电子币
    public int GoodsAllCount; //商品总数
    public double MaxDzb; //电子币数

    public List<BuyCartInfoList> BuyCartInfoList;

    public MReceiptAddressModel MReceiptAddress;
}
