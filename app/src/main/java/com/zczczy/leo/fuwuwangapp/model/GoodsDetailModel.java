package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Leo on 2016/4/29.
 */
public class GoodsDetailModel implements Serializable {
    /**
     * GoodsInfoId : sample string 1
     * StoreInfoId : sample string 2
     * GoodsTypeId : sample string 3
     * GodosName : sample string 4
     * GoodsDesc : sample string 5
     * GoodsPrice : sample string 6
     * GoodsLBPrice : sample string 7
     * GoodsPurchaseNotes : sample string 8
     * GoodsReturnTicket : sample string 9
     * GoodsReturnTtype : sample string 10
     * GoodsRreturnRtandard : sample string 11
     * RetMianZhi : sample string 12
     * GoodsCreateTime : sample string 13
     * GoodsUpdTime : sample string 14
     * IsLbProduct : sample string 15
     * GoodsCheckStatus : sample string 16
     * GoodsDelStatus : sample string 17
     * GoodsCheckTime : sample string 18
     * GoodsCheckUser : sample string 19
     * GoodsType : sample string 20
     * GoodsStock : sample string 21
     * GoodsImgSl : sample string 22
     * GoodsIsBy : sample string 23
     * TempDisp : sample string 24
     * GoodsImgList : [{"GoodsImgId":1,"GoodsInfoId":"sample string 2","GoodsImgUrl":"sample string 3","GoodsImgStatus":4,"GodosName":"sample string 5"},{"GoodsImgId":1,"GoodsInfoId":"sample string 2","GoodsImgUrl":"sample string 3","GoodsImgStatus":4,"GodosName":"sample string 5"}]
     */

    public String GoodsInfoId;
    public String StoreInfoId;
    public String GoodsTypeId;
    public String GodosName;
    public String GoodsDesc;
    public String GoodsPrice;
    public String GoodsLBPrice;
    public String GoodsPurchaseNotes;
    public String GoodsReturnTicket;
    public String GoodsReturnTtype;
    public String GoodsRreturnRtandard;
    public String RetMianZhi;
    public String GoodsCreateTime;
    public String GoodsUpdTime;
    public String IsLbProduct;
    public String GoodsCheckStatus;
    public String GoodsDelStatus;
    public String GoodsCheckTime;
    public String GoodsCheckUser;
    public String GoodsType;
    public String GoodsStock;
    public String GoodsImgSl;
    public String GoodsIsBy;
    public String TempDisp;
    /**
     * GoodsImgId : 1
     * GoodsInfoId : sample string 2
     * GoodsImgUrl : sample string 3
     * GoodsImgStatus : 4
     * GodosName : sample string 5
     */

    public List<GoodsImgListModel> GoodsImgList;


}
