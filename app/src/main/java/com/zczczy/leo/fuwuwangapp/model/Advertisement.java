package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;

/**
 * Created by leo on 2015/12/21.
 */
public class Advertisement implements Serializable {

    /**
     * ad_id : 2fc23576e2e24619ba971a639cee8031
     * ad_img : .jpg
     * ad_position : 1
     * ad_weight : 1
     * ad_state : 1
     * ad_type : 1
     * ad_pid : 149d7ce134fa4f56907823518d085511
     * ad_create_time : 1446887239000
     * number : null
     */

    private String ad_id;
    private String ad_img;
    private int ad_position;
    private int ad_weight;
    private int ad_state;
    private int ad_type;
    private String ad_pid;
    private long ad_create_time;
    private int ad_pd;
    private Object number;
    private Store stores;
    private Integer city_id;
    private Goods goods;

    public void setAd_id(String ad_id) {
        this.ad_id = ad_id;
    }

    public void setAd_img(String ad_img) {
        this.ad_img = ad_img;
    }

    public void setAd_position(int ad_position) {
        this.ad_position = ad_position;
    }

    public void setAd_weight(int ad_weight) {
        this.ad_weight = ad_weight;
    }

    public void setAd_state(int ad_state) {
        this.ad_state = ad_state;
    }

    public void setAd_type(int ad_type) {
        this.ad_type = ad_type;
    }

    public void setAd_pid(String ad_pid) {
        this.ad_pid = ad_pid;
    }

    public void setAd_create_time(long ad_create_time) {
        this.ad_create_time = ad_create_time;
    }

    public void setNumber(Object number) {
        this.number = number;
    }

    public String getAd_id() {
        return ad_id;
    }

    public String getAd_img() {
        return ad_img;
    }

    public int getAd_position() {
        return ad_position;
    }

    public int getAd_weight() {
        return ad_weight;
    }

    public int getAd_state() {
        return ad_state;
    }

    public int getAd_type() {
        return ad_type;
    }

    public String getAd_pid() {
        return ad_pid;
    }

    public long getAd_create_time() {
        return ad_create_time;
    }

    public Object getNumber() {
        return number;
    }

    public int getAd_pd() {
        return ad_pd;
    }

    public void setAd_pd(int ad_pd) {
        this.ad_pd = ad_pd;
    }

    public Store getStores() {
        return stores;
    }

    public void setStores(Store stores) {
        this.stores = stores;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }
}
