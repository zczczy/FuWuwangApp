package com.zczczy.leo.fuwuwangapp.model;

/**
 * Created by zczczy on 2015/9/16.
 */
public class Announcement {

    public int AppConfigId;
    public String AppConfigTitle;
    public String  AppConfigContent;
    public String  IsCloseBtn;
    public String IsShow;




    public int getAppConfigId() {
        return AppConfigId;
    }

    public void setAppConfigId(int AppConfigId) {
        this.AppConfigId = AppConfigId;
    }


    public String getAppConfigTitle() {
        return AppConfigTitle;
    }

    public void setAppConfigTitle(String AppConfigTitle) {this.AppConfigTitle = AppConfigTitle;}


    public String getAppConfigContent() {
        return AppConfigContent;
    }

    public void setAppConfigContent(String AppConfigContent) {this.AppConfigContent = AppConfigContent;}


    public String getIsCloseBtn() {
        return IsCloseBtn;
    }

    public void setIsCloseBtn(String IsCloseBtn) {this.IsCloseBtn = IsCloseBtn;}


    public String getIsShow() {
        return IsShow;
    }

    public void setIsShow(String IsShow) {this.IsShow = IsShow;}



}
