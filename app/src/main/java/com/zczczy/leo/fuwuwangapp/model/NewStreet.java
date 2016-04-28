package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;

/**
 * Created by Leo on 2015/12/26.
 */
public class NewStreet implements Serializable {


    /**
     * street_id : 2
     * street_name : 西安路
     * region_id : 1
     */

    private int street_id;
    private String street_name;
    private int region_id;

    public void setStreet_id(int street_id) {
        this.street_id = street_id;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public int getStreet_id() {
        return street_id;
    }

    public String getStreet_name() {
        return street_name;
    }

    public int getRegion_id() {
        return region_id;
    }
}
