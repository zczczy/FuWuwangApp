package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;

/**
 * Created by Leo on 2016/5/6.
 */
public class OrderDetailModel implements Serializable {

    public String MOrderDetailId; //订单详细id
    public String MOrderId;  //订单id
    public String StoreInfoId; //店铺id
    public int LogisticsInfoId; //公司id
    public String MOrderDetailPrice; // 金额
    public String MOrderDetailLbCount; //龙币数量
    public String MOrderDetailDzbPrice; //电子币
    public String MOrderDetailStatus; //订单详细状态
    public String ProductName; //产品名称
    public String ProductPrice; // 商品单格
    public String Postage; //运费
    public String ProductNum; //产品数量
    public String GoodsInfoId; //产品id
    public String GoodsImgSl; //图片
    public String XfNo; //消费码
    public String GoodsType; // 产品类别
    public String TrackingNo; //运单号
    public String RetQueueStatus; //返券状态
    public String CreateTime; //创建时间
    public int RetMianZhi; //返券面值
    public int GoodsReturnTicket; //返券数量
    public String MOrderNo; //订单号
    public String GoodsDesc;//商品描述

}
