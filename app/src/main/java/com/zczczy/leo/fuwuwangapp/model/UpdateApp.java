package com.zczczy.leo.fuwuwangapp.model;

/**
 * 银行
 * Created by darkwh on 2015/6/30.
 */
public class UpdateApp {

    public int id;

    public int versioncode;

    public String versionurl;

    public String remark;

    public String createtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(int versioncode) {
        this.versioncode = versioncode;
    }

    public String getVersionurl() {
        return versionurl;
    }

    public void setVersionurl(String versionurl) {
        this.versionurl = versionurl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
