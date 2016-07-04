package com.zczczy.leo.fuwuwangapp.model;

/**
 * Created by zczczy on 2015/11/9.
 */
public class FwwUser  {

    public String userLogin;
    public String passWord;
    public String passWordConfirm;
    public String zy;
    public String kbn;
    public String cardNo;
    public String Mobile;
    public String RealName;
    public String IdCard;

    public FwwUser(){

    }
    public FwwUser(String userLogin, String passWord, String passWordConfirm, String zy, String
            kbn, String Mobile, String RealName, String IdCard, String cardNo) {
        this.userLogin = userLogin;
        this.passWord = passWord;
        this.passWordConfirm = passWordConfirm;
        this.zy = zy;
        this.kbn = kbn;
        this.Mobile = Mobile;
        this.RealName = RealName;
        this.IdCard = IdCard;
        this.cardNo = cardNo;
    }
}
