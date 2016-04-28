package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Leo on 2015/12/26.
 */
public class NewRegion implements Serializable {


    /**
     * region_id : 1
     * region_name : 白城区
     * city_id : 1
     */

    private int region_id;
    private String region_name;
    private int city_id;
    private List<NewStreet> streets;

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getRegion_id() {
        return region_id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public int getCity_id() {
        return city_id;
    }

    public List<NewStreet> getStreets() {
        return streets;
    }

    public void setStreets(List<NewStreet> streets) {
        this.streets = streets;
    }
}
