package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;

/**
 * Created by Leo on 2015/12/23.
 */
public class Store implements Serializable {


    /**
     * store_id : 1322a8df724544308ff7758f6beb6348
     * store_name : 绿园酒店
     * store_desc : 嘎嘎的
     * store_img : .jpg
     * user_id : a050f581367c4bb7b5646c043bac552d
     * store_state : 3
     * store_create_time : 1446535734000
     * store_check_time : 1446019300000
     * check_user_id : 4
     * street_id : 7
     * store_phone : 15689412203
     * store_type2_id : 3
     * store_address : 6A206
     * city_name : 营口市
     * region_name : 甘井子区
     * street_name : 红菱路
     */

    private String store_id;
    private String store_name;
    private String store_desc;
    private String store_img;
    private String user_id;
    private int store_state;
    private long store_create_time;
    private long store_check_time;
    private String check_user_id;
    private int street_id;
    private String store_phone;
    private int store_type2_id;
    private String store_address;
    private String city_name;
    private String region_name;
    private String street_name;

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public void setStore_desc(String store_desc) {
        this.store_desc = store_desc;
    }

    public void setStore_img(String store_img) {
        this.store_img = store_img;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setStore_state(int store_state) {
        this.store_state = store_state;
    }

    public void setStore_create_time(long store_create_time) {
        this.store_create_time = store_create_time;
    }

    public void setStore_check_time(long store_check_time) {
        this.store_check_time = store_check_time;
    }

    public void setCheck_user_id(String check_user_id) {
        this.check_user_id = check_user_id;
    }

    public void setStreet_id(int street_id) {
        this.street_id = street_id;
    }

    public void setStore_phone(String store_phone) {
        this.store_phone = store_phone;
    }

    public void setStore_type2_id(int store_type2_id) {
        this.store_type2_id = store_type2_id;
    }

    public void setStore_address(String store_address) {
        this.store_address = store_address;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public String getStore_id() {
        return store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public String getStore_desc() {
        return store_desc;
    }

    public String getStore_img() {
        return store_img;
    }

    public String getUser_id() {
        return user_id;
    }

    public int getStore_state() {
        return store_state;
    }

    public long getStore_create_time() {
        return store_create_time;
    }

    public long getStore_check_time() {
        return store_check_time;
    }

    public String getCheck_user_id() {
        return check_user_id;
    }

    public int getStreet_id() {
        return street_id;
    }

    public String getStore_phone() {
        return store_phone;
    }

    public int getStore_type2_id() {
        return store_type2_id;
    }

    public String getStore_address() {
        return store_address;
    }

    public String getCity_name() {
        return city_name;
    }

    public String getRegion_name() {
        return region_name;
    }

    public String getStreet_name() {
        return street_name;
    }
}
