package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;

/**
 * Created by leo on 2015/12/24.
 */
public class Order implements Serializable {


    /**
     * goods_img : .jpg
     * goods_desc : 好吃好吃可口好吃可口好吃可口好吃可口好吃可口好吃可口好吃可口好吃可口好吃可口好吃可口好吃可口
     * goods_name : 吉祥馄饨
     * userLogin : ricky
     * goods_id : aeef2021d8ec434a823e0d05ed684fb6
     * store_name : 冰海大酒店园
     * store_phone : 12655698978
     * store_address : 6A203
     * order_id : fde39193ca2c42caa637b49f784f2d39
     * electronics_money : 0
     * pay_type : 0
     * gooods_number : 1
     * order_state : 2
     * electronics_evidence : 0
     * order_time : 1450954772000
     * pay_time : null
     * deal_time : null
     * dianzibi_pay_state : 0
     * yinlian_pay_state : 0
     * longbi_pay_state : 0
     * transId :
     * chrCode :
     * content : null
     * lb_money : 0
     * unionpay_money : 1
     */
    private String goods_img;
    private String goods_desc;
    private String goods_name;
    private String userLogin;
    private String goods_id;
    private String store_name;
    private String store_phone;
    private String store_address;
    private String order_id;
    private int electronics_money;
    private int pay_type;
    private int gooods_number;
    private String order_state;
    private long order_time;
    private String pay_time;
    private String deal_time;
    private int dianzibi_pay_state;
    private int yinlian_pay_state;
    private int longbi_pay_state;


    private int lb_money;
    private int unionpay_money;


    //银联订单返回结果
    private String transId; // 银商订单号
    private String electronics_evidence; // 消费码
    private String chrCode;// 订单特征码
    private String merSign; // 签名  作为商户 app 调用全民付收银台客户端的参数，由商户后台传给商户客户端
    private String content;

    private String user_id; // 下单人的ID
    private Integer goods_price; // 价格

    private Integer return_number; // 返券数量
    private String return_mz;//返券面值
    private Integer return_number_state; // 该订单 是否返券；

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public void setGoods_desc(String goods_desc) {
        this.goods_desc = goods_desc;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public void setStore_phone(String store_phone) {
        this.store_phone = store_phone;
    }

    public void setStore_address(String store_address) {
        this.store_address = store_address;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setElectronics_money(int electronics_money) {
        this.electronics_money = electronics_money;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public void setGooods_number(int gooods_number) {
        this.gooods_number = gooods_number;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public void setElectronics_evidence(String electronics_evidence) {
        this.electronics_evidence = electronics_evidence;
    }

    public void setOrder_time(long order_time) {
        this.order_time = order_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public void setDeal_time(String deal_time) {
        this.deal_time = deal_time;
    }

    public void setDianzibi_pay_state(int dianzibi_pay_state) {
        this.dianzibi_pay_state = dianzibi_pay_state;
    }

    public void setYinlian_pay_state(int yinlian_pay_state) {
        this.yinlian_pay_state = yinlian_pay_state;
    }

    public void setLongbi_pay_state(int longbi_pay_state) {
        this.longbi_pay_state = longbi_pay_state;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public void setChrCode(String chrCode) {
        this.chrCode = chrCode;
    }

    public void setLb_money(int lb_money) {
        this.lb_money = lb_money;
    }

    public void setUnionpay_money(int unionpay_money) {
        this.unionpay_money = unionpay_money;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public String getGoods_desc() {
        return goods_desc;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public String getStore_phone() {
        return store_phone;
    }

    public String getStore_address() {
        return store_address;
    }

    public String getOrder_id() {
        return order_id;
    }

    public int getElectronics_money() {
        return electronics_money;
    }

    public int getPay_type() {
        return pay_type;
    }

    public int getGooods_number() {
        return gooods_number;
    }

    public String getOrder_state() {
        return order_state;
    }

    public String getElectronics_evidence() {
        return electronics_evidence;
    }

    public long getOrder_time() {
        return order_time;
    }

    public String getPay_time() {
        return pay_time;
    }

    public String getDeal_time() {
        return deal_time;
    }

    public int getDianzibi_pay_state() {
        return dianzibi_pay_state;
    }

    public int getYinlian_pay_state() {
        return yinlian_pay_state;
    }

    public int getLongbi_pay_state() {
        return longbi_pay_state;
    }

    public String getTransId() {
        return transId;
    }

    public String getChrCode() {
        return chrCode;
    }

    public int getLb_money() {
        return lb_money;
    }

    public int getUnionpay_money() {
        return unionpay_money;
    }

    public String getMerSign() {
        return merSign;
    }

    public void setMerSign(String merSign) {
        this.merSign = merSign;
    }

    public Integer getReturn_number() {
        return return_number;
    }

    public void setReturn_number(Integer return_number) {
        this.return_number = return_number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Integer getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(Integer goods_price) {
        this.goods_price = goods_price;
    }

    public Integer getReturn_number_state() {
        return return_number_state;
    }

    public void setReturn_number_state(Integer return_number_state) {
        this.return_number_state = return_number_state;
    }

    public String getReturn_mz() {
        return return_mz;
    }

    public void setReturn_mz(String return_mz) {
        this.return_mz = return_mz;
    }
}
