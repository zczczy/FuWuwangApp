package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;

/**
 * Created by Leo on 2016/5/5.
 */
public class UnionPay implements Serializable {


    /**
     * MerSign : sample string 1
     * ChrCode : sample string 2
     * TransId : sample string 3
     * MerchantId : sample string 4
     */
    public String MerSign; //银联签名
    public String ChrCode; //特征码
    public String TransId; //银联编号
    public String MerchantId;

}
