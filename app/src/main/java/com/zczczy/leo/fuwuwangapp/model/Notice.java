package com.zczczy.leo.fuwuwangapp.model;

/**
 * 新闻表列表
 * Created by leo on 2015/6/18.
 */
public class Notice {
    public String NoticeId;

    public String Title;

    public String Date;


    public String Appcontent;

    public String NimgUrl;

    public String Ncontent;

    public String Nsource;

    public String Creator;

    public String Nauthor;

    public String getNoticeId() {
        return NoticeId;
    }

    public void setNoticeId(String noticeId) {
        NoticeId = noticeId;
    }

    public String getAppcontent() {
        return Appcontent;
    }

    public void setAppcontent(String appcontent) {
        Appcontent = appcontent;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getNimgUrl() {
        return NimgUrl;
    }

    public void setNimgUrl(String nimgUrl) {
        NimgUrl = nimgUrl;
    }

    public String getNcontent() {
        return Ncontent;
    }

    public void setNcontent(String ncontent) {
        Ncontent = ncontent;
    }

    public String getNsource() {
        return Nsource;
    }

    public void setNsource(String nsource) {
        Nsource = nsource;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public String getNauthor() {
        return Nauthor;
    }

    public void setNauthor(String nauthor) {
        Nauthor = nauthor;
    }
}
