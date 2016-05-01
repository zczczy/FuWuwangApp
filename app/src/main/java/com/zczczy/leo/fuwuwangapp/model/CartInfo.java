package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Leo on 2016/4/27.
 */
public class CartInfo implements Serializable{


    /**
     * StoreInfoId : sample string 1
     * StoreName : sample string 2
     * BuyCartInfoList : [{"BuyCartInfoId":1,"GoodsInfoId":"sample string 2","UserInfoId":"sample string 3","CreateTime":"sample string 4","ProductCount":5,"GoodsImgSl":"sample string 6","GoodsPrice":7,"GoodsLBPrice":8,"GodosName":"sample string 9"},{"BuyCartInfoId":1,"GoodsInfoId":"sample string 2","UserInfoId":"sample string 3","CreateTime":"sample string 4","ProductCount":5,"GoodsImgSl":"sample string 6","GoodsPrice":7,"GoodsLBPrice":8,"GodosName":"sample string 9"}]
     */

    public String StoreInfoId;
    public String StoreName;
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

    public List<BuyCartInfoList> BuyCartInfoList;

    public String getStoreInfoId() {
        return StoreInfoId;
    }

    public void setStoreInfoId(String StoreInfoId) {
        this.StoreInfoId = StoreInfoId;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String StoreName) {
        this.StoreName = StoreName;
    }

    public List<BuyCartInfoList> getBuyCartInfoList() {
        return BuyCartInfoList;
    }

    public void setBuyCartInfoList(List<BuyCartInfoList> BuyCartInfoList) {
        this.BuyCartInfoList = BuyCartInfoList;
    }


}
