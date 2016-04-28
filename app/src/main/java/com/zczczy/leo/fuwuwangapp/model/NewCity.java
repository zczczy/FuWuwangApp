package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;

/**
 * Created by leo on 2016/1/1.
 */
public class NewCity implements Serializable {

    /**
     * city_id : 1
     * city_name : 大连市
     */

    private int city_id;
    private String city_name;

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public int getCity_id() {
        return city_id;
    }

    public String getCity_name() {
        return city_name;
    }
}
