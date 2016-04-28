package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;

/**
 * Created by Leo on 2015/12/26.
 */
public class SecondCategory implements Serializable {


    /**
     * store_type2_name : 川菜
     * store_type2_img :
     * store_type2_desc : null
     * store_type1_id : 1
     * store_type2_id : 1
     */

    private String store_type2_name;
    private String store_type2_img;
    private String store_type2_desc;
    private int store_type1_id;
    private int store_type2_id;

    public void setStore_type2_name(String store_type2_name) {
        this.store_type2_name = store_type2_name;
    }

    public void setStore_type2_img(String store_type2_img) {
        this.store_type2_img = store_type2_img;
    }

    public void setStore_type2_desc(String store_type2_desc) {
        this.store_type2_desc = store_type2_desc;
    }

    public void setStore_type1_id(int store_type1_id) {
        this.store_type1_id = store_type1_id;
    }

    public void setStore_type2_id(int store_type2_id) {
        this.store_type2_id = store_type2_id;
    }

    public String getStore_type2_name() {
        return store_type2_name;
    }

    public String getStore_type2_img() {
        return store_type2_img;
    }

    public String getStore_type2_desc() {
        return store_type2_desc;
    }

    public int getStore_type1_id() {
        return store_type1_id;
    }

    public int getStore_type2_id() {
        return store_type2_id;
    }
}
