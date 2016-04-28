package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;

/**
 * Created by Leo on 2015/12/21.
 */
public class Goods implements Serializable{


    /**
     * goods_id : aeef2021d8ec434a823e0d05ed684fb6
     * goods_name : 吉祥馄饨
     * goods_desc : 好吃好吃可口好吃可口好吃可口好吃可口好吃可口好吃可口好吃可口好吃可口好吃可口好吃可口好吃可口
     * goods_img : .jpg
     * goods_price : 1
     * goods_price_LB : 0
     * store_id : 2
     * goods_pay_type : 0
     * goods_return_ticket : 11111
     * goods_return_type : 0
     * goods_return_standard : 100
     * goods_create_time : 2015-11-01
     * goods_update_time : 2015-11-13
     * goods_type2_id : 1
     * goods_delete_state : 0
     * goods_check_state : 1
     * goods_check_time : 2015-11-13
     * goods_check_user : 907805637a024be094940e75b89bc4d8
     * merchant_type : 2
     * goods_purchase_notes : 有效期:2015年10月17日至2016年06月30日可用时间:周末法定节假日通用16:30 - 21:00，周一至周五16:30-21:00，周六至周日11:00-21:00预约提示无需预约，直接消费（高峰期间消费需排号等位）
     * store_name : null
     * street_name : 同庆路
     */

    private String goods_id;
    private String goods_name;
    private String goods_desc;
    private String goods_img;
    private int goods_price;
    private int goods_price_LB;
    private String store_id;
    private int goods_pay_type;
    private int goods_return_ticket;
    private int goods_return_type;
    private int goods_return_standard;
    private String goods_create_time;
    private String goods_update_time;
    private int goods_type2_id;
    private int goods_delete_state;
    private int goods_check_state;
    private String goods_check_time;
    private String goods_check_user;
    private int merchant_type;
    private String goods_purchase_notes;
    private String store_name;
    private String street_name;
    private Integer goods_putaway_state;
    private String goods_return_mz;

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public void setGoods_desc(String goods_desc) {
        this.goods_desc = goods_desc;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public void setGoods_price(int goods_price) {
        this.goods_price = goods_price;
    }

    public void setGoods_price_LB(int goods_price_LB) {
        this.goods_price_LB = goods_price_LB;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public void setGoods_pay_type(int goods_pay_type) {
        this.goods_pay_type = goods_pay_type;
    }

    public void setGoods_return_ticket(int goods_return_ticket) {
        this.goods_return_ticket = goods_return_ticket;
    }

    public void setGoods_return_type(int goods_return_type) {
        this.goods_return_type = goods_return_type;
    }

    public void setGoods_return_standard(int goods_return_standard) {
        this.goods_return_standard = goods_return_standard;
    }

    public void setGoods_create_time(String goods_create_time) {
        this.goods_create_time = goods_create_time;
    }

    public void setGoods_update_time(String goods_update_time) {
        this.goods_update_time = goods_update_time;
    }

    public void setGoods_type2_id(int goods_type2_id) {
        this.goods_type2_id = goods_type2_id;
    }

    public void setGoods_delete_state(int goods_delete_state) {
        this.goods_delete_state = goods_delete_state;
    }

    public void setGoods_check_state(int goods_check_state) {
        this.goods_check_state = goods_check_state;
    }

    public void setGoods_check_time(String goods_check_time) {
        this.goods_check_time = goods_check_time;
    }

    public void setGoods_check_user(String goods_check_user) {
        this.goods_check_user = goods_check_user;
    }

    public void setMerchant_type(int merchant_type) {
        this.merchant_type = merchant_type;
    }

    public void setGoods_purchase_notes(String goods_purchase_notes) {
        this.goods_purchase_notes = goods_purchase_notes;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public String getGoods_desc() {
        return goods_desc;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public int getGoods_price() {
        return goods_price;
    }

    public int getGoods_price_LB() {
        return goods_price_LB;
    }

    public String getStore_id() {
        return store_id;
    }

    public int getGoods_pay_type() {
        return goods_pay_type;
    }

    public int getGoods_return_ticket() {
        return goods_return_ticket;
    }

    public int getGoods_return_type() {
        return goods_return_type;
    }

    public int getGoods_return_standard() {
        return goods_return_standard;
    }

    public String getGoods_create_time() {
        return goods_create_time;
    }

    public String getGoods_update_time() {
        return goods_update_time;
    }

    public int getGoods_type2_id() {
        return goods_type2_id;
    }

    public int getGoods_delete_state() {
        return goods_delete_state;
    }

    public int getGoods_check_state() {
        return goods_check_state;
    }

    public String getGoods_check_time() {
        return goods_check_time;
    }

    public String getGoods_check_user() {
        return goods_check_user;
    }

    public int getMerchant_type() {
        return merchant_type;
    }

    public String getGoods_purchase_notes() {
        return goods_purchase_notes;
    }

    public String getStore_name() {
        return store_name;
    }

    public String getStreet_name() {
        return street_name;
    }

    public Integer getGoods_putaway_state() {
        return goods_putaway_state;
    }

    public void setGoods_putaway_state(Integer goods_putaway_state) {
        this.goods_putaway_state = goods_putaway_state;
    }

    public String getGoods_return_mz() {
        return goods_return_mz;
    }

    public void setGoods_return_mz(String goods_return_mz) {
        this.goods_return_mz = goods_return_mz;
    }
}
