package com.zczczy.leo.fuwuwangapp.tools;

/**
 * @author Created by LuLeo on 2016/6/22.
 *         you can contact me at :361769045@qq.com
 * @since 2016/6/22.
 */
public class Constants {

    //    测试环境
//    public static final String URL = "http://218.61.203.50:8002/";
//    public static final String PAY_URL = "http://116.228.21.162:9127/umsFrontWebQmjf/umspay";

    //正式环境
    public static final String PAY_URL = "https://mpos.quanminfu.com:8018/umsFrontWebQmjf/umspay";
    public static final String URL = "http://appapib.86fuwuwang.com/";


    public static final String DETAIL_PAGE_ACTION = "DetailPage/";
    public static final String LOTTERY_DIST = "LotteryDist";
    public static final String NOTICE_DETAIL_METHOD = "NoticeDetail/";
    public static final String NEWS_DETAIL_METHOD = "NewsDetail/";
    public static final String FUNCTION_DETAIL_METHOD = "MtFuncDetail/";
    public static final String AGENT_DETAIL_METHOD = "AgentBusinessDetail?userid=";
    public static final String COMPANY_DETAIL_METHOD = "CompanyDetail/";
    public static final String GUIDE_WE_CHAT_METHOD = "GuideWeChat/";
    public static final String GAME_DISP_METHOD = "GameDisp?kbn=2";


    public static final Integer PAGE_COUNT = 10;

    public static final String ANDROID = "1";//请求类型  android

    public static final String NORMAL = "1"; //会员类型  1.普通会员

    public static final String VIP = "2"; //会员类型 2.vip（服务网会员）

    public static final String ASC = "asc"; //asc升序
    public static final String DESC = "desc"; //desc降序
    public static final int DEFAULT_SORT = 0; //排序（0 默认（推荐降序加时间升序/降序）,1 价格,2 销量）
    public static final int PRICE_SORT = 1; //排序（0 默认（推荐降序加时间升序/降序）,1 价格,2 销量）
    public static final int COUNT_SORT = 2; //排序（0 默认（推荐降序加时间升序/降序）,1 价格,2 销量）

    public static final int STORE_GOODS = 0; // 店铺入口
    public static final int SEARCH_GOODS = 1; // 搜索入口

    public static final String NORMAL_CATEGORY = "1"; //(1邮寄类,2服务类)
    public static final String SERVICE_CATEGORY = "2"; //(1邮寄类,2服务类)


    public static final int DUEPAYMENT = 0; //0:待支付
    public static final int PAID = 1;   //1：已支付
    public static final int CANCEL = 2; //2:已取消,
    public static final int SEND = 3; //3：已发货
    public static final int CONFIRM = 4; //4:确认收货
    public static final int FINISH = 5; //5:交易完成
    public static final int ALL_ORDER = 9; //5:交易完成

    public static final int UMSPAY = 1; //1:网银
    public static final int DZB = 2;   //2电子币
    public static final int LONG_BI = 3; //3龙币
    public static final int DZB_LONGBI = 4; //4电子币+龙币
    public static final int DZB_UMSPAY = 5; //5电子币+网银
    public static final int LONGBI_UMSPAY = 6; //6龙币+网银
    public static final int LONGBI_UMSPAY_DZB = 7; //7电子币+龙币+网银
    public static final int ALI_PAY = 8; //支付宝
    public static final int ALI_DZB = 9; //支付宝+电子
    public static final int ALI_LONGBI = 10; //支付宝+龙币
    public static final int ALI_DZB_LONGBI = 11; //支付宝+电子币+龙币
    public static final int WX_PAY = 12; //ALI_LONGBI
    public static final int WX_DZB = 13; //微信+电子币
    public static final int WX_LONGBI = 14; //微信+龙币
    public static final int WX_DZB_LONGBI = 15; //微信+电子币+龙币

    public static final String STORE_STATE_NORMAL = "1"; //1.待审核 2.锁定 3.审核成功，活跃，解锁状态
    public static final String STORE_STATE_LOCK = "2"; //1.待审核 2.锁定 3.审核成功，活跃，解锁状态
    public static final String STORE_STATE_ACTIVITY = "3"; //1.待审核 2.锁定 3.审核成功，活跃，解锁状态

    public static final String GOODS_STATE_UP = "0"; //0.上架
    public static final String GOODS_STATE_DOWN = "-1"; //-1.下架
    public static final String GOODS_STATE_PASS = "1"; //1.审核通过


    // APP_ID 替换为你的应用从官方网站申请到的合法appId
//    public static final String APP_ID = "wxb4ba3c02aa476ea1";
    public static final String APP_ID = "wx6205a3c918095253";

    // 腾讯bugly
    public static final String BUGLY_APP_ID = "900019033";

}
