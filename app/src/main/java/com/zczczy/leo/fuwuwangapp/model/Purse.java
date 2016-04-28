package com.zczczy.leo.fuwuwangapp.model;

/**
 * 电子钱包交易明细
 * Created by darkwh on 2015/7/7.
 */
public class Purse {
    public String TradeName;

    public String Money;

    public String TradeTime;

    public String Explain;



    // 用户名
    public  String m_Uid;
    // 真实姓名
    public  String m_realrname;
    // 会员状态
    public  String gradename;
    // 麻豆
    public  String point;
    // 总麻豆
    public  String allPoint;
    // 会员统计
    public  String childCount;
    //注册时间
    public  String m_SignDate;

    //会员id
    public int UserId;





    public String getTradeName() {
        return TradeName;
    }

    public void setTradeName(String tradeName) {
        TradeName = tradeName;
    }

    public String getMoney() {
        return Money;
    }

    public void setMoney(String money) {
        Money = money;
    }

    public String getTradeTime() {
        return TradeTime;
    }

    public void setTradeTime(String tradeTime) {
        TradeTime = tradeTime;
    }

    public String getExplain() {
        return Explain;
    }

    public void setExplain(String explain) {
        Explain = explain;
    }

    public String getM_Uid() {return m_Uid;}

    public void setM_Uid(String m_Uid) {this.m_Uid = m_Uid;}

    public String getM_realrname() {return m_realrname;}

    public void setM_realrname(String m_realrname) {this.m_realrname = m_realrname;}

    public String getGradename() {return gradename;}

    public void setGradename(String gradename) {this.gradename = gradename;}

    public String getPoint() {return point;}

    public void setPoint(String point) {this.point = point;}

    public String getAllPoint() {return allPoint;}

    public void setAllPoint(String allPoint) {this.allPoint = allPoint;}

    public String getChildCount() {return childCount;}

    public void setChildCount(String childCount) {this.childCount = childCount;}

    public String getM_SignDate() {return m_SignDate;}

    public void setM_SignDate(String m_SignDate) {this.m_SignDate = m_SignDate;}

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        this.UserId = userId;
    }
}
