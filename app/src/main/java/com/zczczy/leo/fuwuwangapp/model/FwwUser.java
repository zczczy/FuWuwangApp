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

    public FwwUser(){

    }
    public FwwUser(String userLogin, String passWord, String passWordConfirm, String zy, String
            kbn, String cardNo) {
        this.userLogin = userLogin;
        this.passWord = passWord;
        this.passWordConfirm = passWordConfirm;
        this.zy = zy;
        this.kbn = kbn;
        this.cardNo = cardNo;
    }
}
