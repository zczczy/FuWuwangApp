package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;

/**
 * Created by leo on 2015/12/27.
 */
public class Member implements Serializable {


    /**
     * user_id : 13b30f3dfaa7450ba5010d08442da4a9
     * user_type : 4
     * user_state : 2
     * userLogin : ricky
     * passWord : e10adc3949ba59abbe56e057f20f883e
     * user_img : null
     * realName : null
     * idCard : null
     * dq : null
     * zy : null
     * user_email : null
     * merchant_add_user : null
     * merchant_desc : null
     * merchant_phone : null
     * service_man : null
     * merchant_account : null
     * merchant_type : null
     * session_id : null
     * results : 登录成功
     * token : FqVvD+ObQZWlNfFE8yP+ycXgWIub9qVo+75sXExiAY8wCnRfpBT348ekSUpVD0JVN1njdJoon1W1zyKcg2jgRGx+N/bcOx4o+7kbjpNibkTxVmbndbCRnouN1nZ+YyXQshCKD1iQj49a1iqJgVk+BLVmauuvcbL18H1W8Hynwl8=
     * longbi : 50
     * dianzibi : 394007.1
     * login_state : 0
     * passWordConfirm：null
     */

    private String user_id;
    private String user_type;
    private String user_state;
    private String userLogin;
    private String passWord;
    private String user_img;
    private String realName;
    private String idCard;
    private String dq;
    private String zy;
    private String user_email;
    private String merchant_add_user;
    private String merchant_desc;
    private String merchant_phone;
    private String service_man;
    private String merchant_account;
    private String merchant_type;
    private String session_id;
    private String results;
    private String token;
    private int longbi;
    private double dianzibi;
    private int login_state;
    private String passWordConfirm;
    private String user_create_time;

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public void setUser_state(String user_state) {
        this.user_state = user_state;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }


    public void setResults(String results) {
        this.results = results;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setLongbi(int longbi) {
        this.longbi = longbi;
    }

    public void setDianzibi(double dianzibi) {
        this.dianzibi = dianzibi;
    }

    public void setLogin_state(int login_state) {
        this.login_state = login_state;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public String getUser_state() {
        return user_state;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getUser_img() {
        return user_img;
    }

    public String getRealName() {
        return realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public String getDq() {
        return dq;
    }

    public String getZy() {
        return zy;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getMerchant_add_user() {
        return merchant_add_user;
    }

    public String getMerchant_desc() {
        return merchant_desc;
    }

    public String getMerchant_phone() {
        return merchant_phone;
    }

    public String getService_man() {
        return service_man;
    }

    public String getMerchant_account() {
        return merchant_account;
    }

    public String getMerchant_type() {
        return merchant_type;
    }

    public String getSession_id() {
        return session_id;
    }

    public String getResults() {
        return results;
    }

    public String getToken() {
        return token;
    }

    public int getLongbi() {
        return longbi;
    }

    public double getDianzibi() {
        return dianzibi;
    }

    public int getLogin_state() {
        return login_state;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public void setDq(String dq) {
        this.dq = dq;
    }

    public void setZy(String zy) {
        this.zy = zy;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public void setMerchant_add_user(String merchant_add_user) {
        this.merchant_add_user = merchant_add_user;
    }

    public void setMerchant_desc(String merchant_desc) {
        this.merchant_desc = merchant_desc;
    }

    public void setMerchant_phone(String merchant_phone) {
        this.merchant_phone = merchant_phone;
    }

    public void setService_man(String service_man) {
        this.service_man = service_man;
    }

    public void setMerchant_account(String merchant_account) {
        this.merchant_account = merchant_account;
    }

    public void setMerchant_type(String merchant_type) {
        this.merchant_type = merchant_type;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }


    public String getPassWordConfirm() {
        return passWordConfirm;
    }

    public void setPassWordConfirm(String passWordConfirm) {
        this.passWordConfirm = passWordConfirm;
    }

    public String getUser_create_time() {
        return user_create_time;
    }

    public void setUser_create_time(String user_create_time) {
        this.user_create_time = user_create_time;
    }
}
