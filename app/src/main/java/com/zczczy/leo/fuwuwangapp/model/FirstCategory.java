package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Leo on 2015/12/26.
 */
public class FirstCategory implements Serializable {


    /**
     * store_type1_id : 1
     * store_type1_name : 美食
     * store_type1_img :
     * store_type1_desc :
     */

    private int store_type1_id;
    private String store_type1_name;
    private String store_type1_img;
    private String store_type1_desc;
    private List<SecondCategory> store_type2;

    public void setStore_type1_id(int store_type1_id) {
        this.store_type1_id = store_type1_id;
    }

    public void setStore_type1_name(String store_type1_name) {
        this.store_type1_name = store_type1_name;
    }

    public void setStore_type1_img(String store_type1_img) {
        this.store_type1_img = store_type1_img;
    }

    public void setStore_type1_desc(String store_type1_desc) {
        this.store_type1_desc = store_type1_desc;
    }

    public int getStore_type1_id() {
        return store_type1_id;
    }

    public String getStore_type1_name() {
        return store_type1_name;
    }

    public String getStore_type1_img() {
        return store_type1_img;
    }

    public String getStore_type1_desc() {
        return store_type1_desc;
    }

    public List<SecondCategory> getStore_type2() {
        return store_type2;
    }

    public void setStore_type2(List<SecondCategory> store_type2) {
        this.store_type2 = store_type2;
    }
}
