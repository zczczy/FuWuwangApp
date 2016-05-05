package com.zczczy.leo.fuwuwangapp.dao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Leo on 2016/1/6.
 */
@DatabaseTable(tableName="tab_search_history")
public class SearchHistory implements Serializable {

    @DatabaseField(generatedId = true,useGetSet=true)
    private int id;

    @DatabaseField(useGetSet=true,canBeNull=false)
    private String searchContent;

    @DatabaseField(useGetSet=true,canBeNull=true)
    private String userName;

    @DatabaseField(useGetSet=true,canBeNull=true)
    private String searchTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(String searchTime) {
        this.searchTime = searchTime;
    }
}
