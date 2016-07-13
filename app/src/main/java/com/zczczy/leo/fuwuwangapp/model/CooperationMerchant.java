package com.zczczy.leo.fuwuwangapp.model;


import java.math.BigDecimal;
import java.util.List;

/**
 * 联盟商家列表
 * Created by leo on 2015/6/20.
 */
public class CooperationMerchant {

    public Integer cp_id;

    public String cp_name_zh;

    public String cp_name_en;

    public String cp_pic;

    public String cp_type;

    public String cp_class;

    public String cp_province;

    public String cp_city;

    public String cp_district;

    public String cp_info;

    public String cp_minfo;

    public String cp_address;

    public String cp_industry;

    public String cp_weburl;

    public String cp_phone;

    public String cp_linkman;

    public String cp_isopen;

    public String cp_isshow;

    public String cp_iscommend;

    public String cp_adddate;

    public String cp_lx;

    public String cp_zt;

    public String zk;

    public String gsxlx;

    public String zyts;

    public String skzc;

    public String yysjks;

    public String yysjjs;

    public String jtyd;

    public String wmzc;

    public String djl;

    public String xy;

    public String yhsm;

    public String kdsq;

    public String zhituiren;

    public String logoimg;

    public String erweimaimg;

    public String zuobiao;

    public Integer isjty;

    public String FwwUser;

    public int SellerInfoId;

    public String CompanyKbn;

    public List<StoreDetailModel> StoreList;

    public List<CooperationMerchantProduct> gscpList;

    public List<RebuiltRecommendedGoods> GoodsList;


    /**
     * 联盟商家产品
     * wh
     * 2015-6-30
     */
    private class CooperationMerchantProduct {
        public Integer id;

        public String cpname;

        public String cpjs;

        public BigDecimal cpjg;

        public String cptplj;

        public String zk;

        public String rmsq;

        public String bzjz;

        public String rmbq;

        public Boolean zt;

        public Integer djl;

        public Integer gsid;

        public Integer isjty;
    }
}
