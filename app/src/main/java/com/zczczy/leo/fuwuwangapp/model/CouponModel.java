package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;

/**
 * Created by Leo on 2016/5/5.
 */
public class CouponModel implements Serializable {

    /// <summary>
    /// 商品返券面值(0:不返券，7：100面值，8：200面值，9：500面值，10：400面值)
    /// </summary>
    public int RetMianZhi7 ;
    public int RetMianZhi8 ;
    public int RetMianZhi9 ;
    public int RetMianZhi10 ;

    public String RetMianZhi7Str ;
    public String RetMianZhi8Str ;
    public String RetMianZhi9Str ;
    public String RetMianZhi10Str ;

}
