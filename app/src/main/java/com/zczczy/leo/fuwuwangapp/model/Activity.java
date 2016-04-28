package com.zczczy.leo.fuwuwangapp.model;

/**
 * 活动列表
 * Created by darkwh on 2015/7/8.
 */
public class Activity {
    public int aid;

    public String atitle;

    public String astarttime;

    public String aendtime;

    public String createtime;

    public String aaddress;

    public String promoterid;

    public String promoterusername;

    public String promotername;

    public String aimgurl;

    public String isexamine;

    public String status;

    public String atid;

    public String acontent;

    public String getAcontent() {
        return acontent;
    }

    public void setAcontent(String acontent) {
        this.acontent = acontent;
    }

    public String getAtid() {
        return atid;
    }

    public void setAtid(String atid) {
        this.atid = atid;
    }

    public String atname;

    public String getAtname() {
        return atname;
    }

    public void setAtname(String atname) {
        this.atname = atname;
    }

    public ActivityType atype;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getAtitle() {
        return atitle;
    }

    public void setAtitle(String atitle) {
        this.atitle = atitle;
    }

    public String getAstarttime() {
        return astarttime;
    }

    public void setAstarttime(String astarttime) {
        this.astarttime = astarttime;
    }

    public String getAendtime() {
        return aendtime;
    }

    public void setAendtime(String aendtime) {
        this.aendtime = aendtime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getAaddress() {
        return aaddress;
    }

    public void setAaddress(String aaddress) {
        this.aaddress = aaddress;
    }

    public String getPromoterid() {
        return promoterid;
    }

    public void setPromoterid(String promoterid) {
        this.promoterid = promoterid;
    }

    public String getPromoterusername() {
        return promoterusername;
    }

    public void setPromoterusername(String promoterusername) {
        this.promoterusername = promoterusername;
    }

    public String getPromotername() {
        return promotername;
    }

    public void setPromotername(String promotername) {
        this.promotername = promotername;
    }

    public String getAimgurl() {
        return aimgurl;
    }

    public void setAimgurl(String aimgurl) {
        this.aimgurl = aimgurl;
    }

    public ActivityType getAtype() {
        return atype;
    }

    public void setAtype(ActivityType atype) {
        this.atype = atype;
    }

    public String getIsexamine() {
        return isexamine;
    }

    public void setIsexamine(String isexamine) {
        this.isexamine = isexamine;
    }

    public class ActivityType {
        public String atid;

        public String atname;

        public String getAtid() {
            return atid;
        }

        public void setAtid(String atid) {
            this.atid = atid;
        }

        public String getAtname() {
            return atname;
        }

        public void setAtname(String atname) {
            this.atname = atname;
        }
    }
}
