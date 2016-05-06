package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;

/**
 * Created by Leo on 2016/5/1.
 */
public class GoodsCommentsModel implements Serializable {


    /**
     * GoodsCommentsId : 1
     * GoodsInfoId : sample string 2
     * MOrderDetailId : sample string 3
     * UserInfoId : sample string 4
     * UserLogin : sample string 5
     * GoodsCommentsDj : 6  评论等级
     * GoodsCommentsNr : sample string 7  评论内容
     * XNum : 8 星的数量
     * PlTime : sample string 9 评论时间
     * GodosName : sample string 10
     * GoodsImgSl : sample string 11
     * MOrderNo : sample string 12
     */

    public int GoodsCommentsId;
    public String GoodsInfoId;
    public String MOrderDetailId;
    public String UserInfoId;
    public String UserLogin;
    public int GoodsCommentsDj;  //评论等级(1:好评，2：中评，3：差评)
    public String GoodsCommentsNr;
    public int XNum;
    public String PlTime;
    public String GodosName;
    public String GoodsImgSl;
    public String MOrderNo;
}
