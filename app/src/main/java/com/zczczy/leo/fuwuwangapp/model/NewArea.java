package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Leo on 2016/5/4.
 */
public class NewArea implements Serializable {

    public  String AreaId;

    public String CityId;

    public String AreaName;

    public List<StreetInfo> listStreet;
}
