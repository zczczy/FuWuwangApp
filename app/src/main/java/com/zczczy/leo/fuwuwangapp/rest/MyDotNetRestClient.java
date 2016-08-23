package com.zczczy.leo.fuwuwangapp.rest;

import com.zczczy.leo.fuwuwangapp.model.Activity;
import com.zczczy.leo.fuwuwangapp.model.AdvertModel;
import com.zczczy.leo.fuwuwangapp.model.AllCity;
import com.zczczy.leo.fuwuwangapp.model.Announcement;
import com.zczczy.leo.fuwuwangapp.model.Bank;
import com.zczczy.leo.fuwuwangapp.model.Banner;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.CartInfo;
import com.zczczy.leo.fuwuwangapp.model.CityModel;
import com.zczczy.leo.fuwuwangapp.model.ConfirmOrderModel;
import com.zczczy.leo.fuwuwangapp.model.CooperationMerchant;
import com.zczczy.leo.fuwuwangapp.model.ExchangeLongBiModel;
import com.zczczy.leo.fuwuwangapp.model.Experience;
import com.zczczy.leo.fuwuwangapp.model.FwwUser;
import com.zczczy.leo.fuwuwangapp.model.Goods;
import com.zczczy.leo.fuwuwangapp.model.GoodsCommentsModel;
import com.zczczy.leo.fuwuwangapp.model.GoodsTypeModel;
import com.zczczy.leo.fuwuwangapp.model.Information;
import com.zczczy.leo.fuwuwangapp.model.LoginInfo;
import com.zczczy.leo.fuwuwangapp.model.LogisticsInfo;
import com.zczczy.leo.fuwuwangapp.model.Lottery;
import com.zczczy.leo.fuwuwangapp.model.LotteryConfig;
import com.zczczy.leo.fuwuwangapp.model.LotteryInfo;
import com.zczczy.leo.fuwuwangapp.model.MReceiptAddressModel;
import com.zczczy.leo.fuwuwangapp.model.MemberInfo;
import com.zczczy.leo.fuwuwangapp.model.NewArea;
import com.zczczy.leo.fuwuwangapp.model.NewBanner;
import com.zczczy.leo.fuwuwangapp.model.NewCity;
import com.zczczy.leo.fuwuwangapp.model.NewProvince;
import com.zczczy.leo.fuwuwangapp.model.Notice;
import com.zczczy.leo.fuwuwangapp.model.OpenAccount;
import com.zczczy.leo.fuwuwangapp.model.OrderCountModel;
import com.zczczy.leo.fuwuwangapp.model.OrderDetailModel;
import com.zczczy.leo.fuwuwangapp.model.PagerResult;
import com.zczczy.leo.fuwuwangapp.model.ProvinceModel;
import com.zczczy.leo.fuwuwangapp.model.Purse;
import com.zczczy.leo.fuwuwangapp.model.QueueCompanyDetail;
import com.zczczy.leo.fuwuwangapp.model.QueueCount;
import com.zczczy.leo.fuwuwangapp.model.QueueMDetailModel;
import com.zczczy.leo.fuwuwangapp.model.ShopOrder;
import com.zczczy.leo.fuwuwangapp.model.StoreDetailModel;
import com.zczczy.leo.fuwuwangapp.model.UpdateApp;
import com.zczczy.leo.fuwuwangapp.model.UserBaseInfo;
import com.zczczy.leo.fuwuwangapp.model.UserFinanceInfo;
import com.zczczy.leo.fuwuwangapp.model.Volume;
import com.zczczy.leo.fuwuwangapp.model.Wealth;
import com.zczczy.leo.fuwuwangapp.model.YpdRecord;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Header;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.RequiresHeader;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientErrorHandling;
import org.androidannotations.rest.spring.api.RestClientHeaders;
import org.androidannotations.rest.spring.api.RestClientRootUrl;
import org.androidannotations.rest.spring.api.RestClientSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

/**
 * Created by Leo on 2016/3/2.
 * http://218.61.203.50:8002/
 * http://appapia.86fuwuwang.com/
 */
@Rest(rootUrl = "http://appapia.86fuwuwang.com/", requestFactory = MyOkHttpClientHttpRequestFactory.class, interceptors = {MyInterceptor.class},
        converters = {StringHttpMessageConverter.class, GsonHttpMessageConverter.class, FormHttpMessageConverter.class, ByteArrayHttpMessageConverter.class},
        responseErrorHandler = MyResponseErrorHandlerBean.class)
public interface MyDotNetRestClient extends RestClientRootUrl, RestClientSupport, RestClientHeaders, RestClientErrorHandling {

    /**
     * 版本升级
     *
     * @param kbn
     * @return
     */
    @Get("api/Content/AppUpdCheck?kbn={kbn}")
    BaseModelJson<UpdateApp> AppUpdCheck(@Path int kbn);

    /**
     * 获取通知
     *
     * @param id
     * @return
     */
    @Get("api/Content/GetAppConfig/{id}")
    BaseModelJson<Announcement> GetAppConfig(@Path int id);

    /**
     * 非会员模块
     */
    //公告列表
    @Get("api/Content/GetNoticeList?PageIndex={PageIndex}&PageSize={PageSize}")
    BaseModelJson<PagerResult<Notice>> GetNoticeList(@Path int PageIndex, @Path int PageSize);

    //资讯列表
    @Get("api/Content/GetNewsList?PageIndex={PageIndex}&PageSize={PageSize}")
    BaseModelJson<PagerResult<Information>> GetInformationList(@Path int PageIndex, @Path int PageSize);

    //麻团基金列表
    @Get("api/Content/GetMtFuncList?PageIndex={PageIndex}&PageSize={PageSize}")
    BaseModelJson<PagerResult<Notice>> GetMtFuncList(@Path int PageIndex, @Path int PageSize);

    //联盟商家列表
    @Get("api/Content/GetCompany?CompanyName={CompanyName}&CityCode={CityCode}&PageIndex={PageIndex}&PageSize={PageSize}")
    BaseModelJson<PagerResult<CooperationMerchant>> GetCompany(@Path String CompanyName, @Path String CityCode, @Path int PageIndex, @Path int PageSize);

    //体验中心列表
    @Get("api/Content/GetBusinessList?BusinessName={BusinessName}&CityCode={CityCode}&PageIndex={PageIndex}&PageSize={PageSize}")
    BaseModelJson<PagerResult<Experience>> GetBusinessList(@Path String BusinessName, @Path String CityCode, @Path int PageIndex, @Path int PageSize);

    //活动列表
    @Get("api/Content/GetActivity?PageIndex={PageIndex}&PageSize={PageSize}")
    BaseModelJson<PagerResult<Activity>> GetActivity(@Path int PageIndex, @Path int PageSize);

    //新会员注册
    @Post("api/Content/RegisterNew")
    BaseModelJson<String> RegisterNew(@Body FwwUser ff);

    //省下拉数据
    @Get("api/Content/GetProvinceList")
    BaseModelJson<List<ProvinceModel>> GetProvinceList();

    //市下拉数据(根据省Code)
    @Get("api/Content/GetCityList?Pno={Pno}")
    BaseModelJson<List<CityModel>> GetCityList(@Path String Pno);

    //查询开户行名称下拉数据
    @Get("api/Content/GetBankNumBerList?CityCode={CityCode}&BankKey={BankKey}")
    BaseModelJson<List<OpenAccount>> GetBankNumBerList(@Path String CityCode, @Path String BankKey);

    //查询银行下拉数据
    @Get("api/Content/GetBankItemsList")
    BaseModelJson<List<Bank>> GetBankItemsList();

    //查询APP上方的banner
    @Get("api/Content/GetTopBanner")
    BaseModelJson<List<Banner>> GetTopBanner();

    //根据会员账号查询商家名称
    @Get("api/Content/GetCompanyNameByUlogin?ulogin={ulogin}")
    BaseModelJson<String> GetCompanyNameByUlogin(@Path String ulogin);

    //根据会员账号查询会员真实姓名
    @Get("api/Content/GetUserNameByUlogin?ulogin={ulogin}")
    BaseModelJson<String> GetUserNameByUlogin(@Path String ulogin);

    /**
     * 会员模块
     */
    //获取财富相关信息
    @Get("api/Member/GetWealth")
    @RequiresHeader("Token")
    BaseModelJson<Wealth> GetWealth();

    //获取会员基本信息
    @Get("api/Member/GetZcUserById")
    @RequiresHeader("Token")
    BaseModelJson<UserBaseInfo> GetZcUserById();

    //获取会员财务信息
    @Get("api/Member/GetFinancialById")
    @RequiresHeader("Token")
    BaseModelJson<UserFinanceInfo> GetFinancialById();

    //电子钱包交易明细
    @Get("api/Member/WalletTransaction?PageIndex={PageIndex}&PageSize={PageSize}")
    @RequiresHeader("Token")
    BaseModelJson<PagerResult<Purse>> WalletTransaction(@Path int PageIndex, @Path int PageSize);

    //我的联盟会员
    @Get("api/Member/GetUnionMember?PageIndex={PageIndex}&PageSize={PageSize}&PId={PId}")
    @RequiresHeader("Token")
    BaseModelJson<PagerResult<Purse>> GetUnionMember(@Path int PageIndex, @Path int PageSize, @Path int PId);

    //查询兑现券数量
    @Get("api/Member/GetQueueCount")
    @RequiresHeader("Token")
    BaseModelJson<Volume> GetQueueCount();

    //兑现券管理
    @Get("api/Member/QueueM")
    @RequiresHeader("Token")
    BaseModelJson<QueueCount> QueueM();

    //查询50进1兑现券详细
    @Get("api/Member/QueueMDetail?QueuesInRule={QueuesInRule}")
    @RequiresHeader("Token")
    BaseModelJson<QueueMDetailModel> QueueMDetail(@Path String QueuesInRule);

    //查询所有城市信息
    @Get("api/Content/GetAllCity")
    BaseModelJson<List<AllCity>> GetAllCity();

    //根据城市名称模糊查询当前城市code
    @Get("api/Content/GetCityCodeByName?Cname={Cname}")
    BaseModelJson<String> GetCityCodeByName(@Path String Cname);

    //查询会员商家队列明细
    @Get("api/Member/GetCompanyQueueDetail?queueRuleId={queueRuleId}")
    @RequiresHeader("Token")
    BaseModelJson<List<QueueCompanyDetail>> GetCompanyQueueDetail(@Path String queueRuleId);

    //查询会员当日已排队信息
    @Get("api/Member/GetCurrYpdInfo?date={date}&flag={flag}")
    @RequiresHeader("Token")
    BaseModelJson<List<YpdRecord>> GetCurrYpdInfo(@Path String date, @Path String flag);

    //查询商家坐标信息
    @Get("api/Member/GetCompanyZb")
    @RequiresHeader("Token")
    BaseModelJson<String> GetCompanyZb();

    /**
     * 查询会员卡号信息
     *
     * @return
     */
    @Get("api/Member/GetMyCardNo")
    @RequiresHeader("Token")
    BaseModelJson<String> GetMyCardNo();

    /**
     * 绑定会员卡
     *
     * @param CardNo
     * @return
     */
    @Post("api/Member/BindCardNo?CardNo={CardNo}")
    @RequiresHeader("Token")
    @Header(name = HttpHeaders.CONTENT_TYPE, value = MediaType.APPLICATION_JSON_VALUE)
    BaseModel BindCardNo(@Path String CardNo);

    /**
     * 兑换龙币处理
     *
     * @param qi_loginKey
     * @return
     */
    @Post("api/Member/Dhlb?qi_loginKey={qi_loginKey}")
    @RequiresHeader("Token")
    @Header(name = HttpHeaders.CONTENT_TYPE, value = MediaType.APPLICATION_JSON_VALUE)
    BaseModel Dhlb(@Path String qi_loginKey);

    /**
     * 查询排队中的兑现券信息
     *
     * @param PageIndex    页码
     * @param PageSize     每页条数
     * @param QueuesInRule 队列ID
     * @param CpId         公司ID
     * @return
     */
    @Get("api/Member/GetMyQueueInfoList?PageIndex={PageIndex}&PageSize={PageSize}&QueuesInRule={QueuesInRule}&CpId={CpId}")
    @RequiresHeader("Token")
    BaseModelJson<PagerResult<ExchangeLongBiModel>> GetMyQueueInfoList(@Path int PageIndex, @Path int PageSize, @Path String QueuesInRule, @Path String CpId);


    /**
     * 功能：订阅安全信使
     * <p/>
     *
     * @param map SendCode  验证码
     *            UserName  用户名
     *            mobile 电话
     *            TwoPW	支付密码
     * @return
     */
    @Post("api/SMS/SubscriptionService")
    BaseModelJson<String> SubscriptionService(@Body Map map);

    /**
     * 功能：取消订阅安全信使
     * <p/>
     *
     * @param map SendCode  验证码
     *            UserName  用户名
     *            mobile 电话
     * @return
     */
    @Post("api/SMS/CancelSubscription")
    BaseModelJson<String> CancelSubscription(@Body Map map);

    /**
     * 功能：获取手机验证码
     * <p/>
     *
     * @param map SendType （0：提现，1：变更资料，2：订阅服务，3：取消订阅,4.转账）
     *            UserName  用户名
     *            mobile 电话
     * @return String
     */
    @Post("api/SMS/SendVerificationCode")
    BaseModelJson<String> SendVerificationCode(@Body Map map);

    /**
     * 功能：验证验证码
     * <p/>
     *
     * @param username 用户名
     * @param code     验证码
     * @param SendType 类型 （0：提现，1：变更资料，2：订阅服务，3：取消订阅,4.转账）
     * @return String
     */
    @Get("api/SMS/VerifyExite?username={username}&code={code}&SendType={SendType}")
    BaseModelJson<String> VerifyExite(@Path String username, @Path String code, @Path String SendType);

    /**
     * SubscriptionExist
     * 功能：根据用户名验证查询是否订阅
     * <p/>
     *
     * @param UserName 用户名
     * @return String
     */
    @Get("api/SMS/SubscriptionExist?UserName={UserName}")
    BaseModelJson<String> SubscriptionExist(@Path String UserName);

    /**
     * SubscriptionExist
     * 功能：获取电话
     *
     * @param UserName 用户名
     * @return 获取电话
     */
    @Get("api/SMS/GetMobile?UserName={UserName}")
    BaseModelJson<String> GetMobile(@Path String UserName);

    /**
     * 查询：是否取消订阅
     *
     * @param UserName 登录帐号
     * @return BaseModel
     */
    @Get("api/SMS/checkIsExistSubByUserName?UserName={UserName}")
    BaseModel checkIsExistSubByUserName(@Path String UserName);

    /**
     * 查询抽奖配置信息
     *
     * @return LotteryConfig
     * @see LotteryConfig
     */
    @Get("api/Lottery/GetLotteryConfigInfo")
    BaseModelJson<LotteryConfig> getLotteryConfigInfo();

    /**
     * 查询所有中奖信息
     *
     * @param PageIndex 页号
     * @param PageSize  每页显示多少
     * @return PagerResult<LotteryInfo>
     * @see PagerResult
     * @see LotteryInfo
     */
    @Get("api/Lottery/GetAllLotteryInfo?PageIndex={PageIndex}&PageSize={PageSize}")
    BaseModelJson<PagerResult<LotteryInfo>> getAllLotteryInfo(@Path int PageIndex, @Path int PageSize);

    /**
     * 排队抽奖处理
     *
     * @param UserName 登录帐号
     * @return Lottery
     * @see Lottery
     */
    @Get("api/Lottery/Lottery?UserName={UserName}")
    BaseModelJson<Lottery> lottery(@Path String UserName);

    /**
     * 查询会员自己的中奖信息
     *
     * @param UserName  登录帐号
     * @param PageIndex 页号
     * @param PageSize  每页显示多少
     * @return PagerResult<LotteryInfo>
     * @see PagerResult
     * @see LotteryInfo
     */
    @Get("api/Lottery/GetMyLotteryInfo?UserName={UserName}&PageIndex={PageIndex}&PageSize={PageSize}")
    BaseModelJson<PagerResult<LotteryInfo>> getMyLotteryInfo(@Path String UserName, @Path int PageIndex, @Path int PageSize);

    /**
     * 查询App首页Banner
     */
    @Get("api/ShopContent/GetHomeBanner")
    BaseModelJson<List<NewBanner>> getHomeBanner();

    /**
     * 根据广告区分查询广告信息（1：首页广告，2：服务类页面广告，21：首页弹出广告）
     *
     * @param kbn
     * @return
     */
    @Get("api/ShopContent/GetAdvertByKbn?kbn={kbn}")
    BaseModelJson<List<AdvertModel>> getAdvertByKbn(@Path String kbn);

    /**
     * 查询推荐商品
     *
     * @param PageIndex
     * @param PageSize
     * @return
     */
    @Get("api/ShopContent/GetRecommendedGoods?PageIndex={PageIndex}&PageSize={PageSize}")
    BaseModelJson<PagerResult<Goods>> getRecommendedGoods(@Path int PageIndex, @Path int PageSize);

    /**
     * 查询用户购物车信息
     *
     * @return
     */
    @Get("api/Shop/GetBuyCartInfo")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModelJson<List<CartInfo>> getBuyCartInfo();

    /**
     * 根据商品ID查询商品明细
     *
     * @param GoodsInfoId
     * @return
     */
    @Get("api/ShopContent/GetGoodsDetailById?GoodsInfoId={GoodsInfoId}")
    BaseModelJson<Goods> getGoodsDetailById(@Path String GoodsInfoId);

    /**
     * 根据商品ID查询评论信息
     *
     * @param PageIndex
     * @param PageSize
     * @param GoodsInfoId
     * @return
     */
    @Get("api/ShopContent/GetGoodsCommentsByGoodsInfoId?PageIndex={PageIndex}&PageSize={PageSize}&GoodsInfoId={GoodsInfoId}")
    BaseModelJson<PagerResult<GoodsCommentsModel>> getGoodsCommentsByGoodsInfoId(@Path String GoodsInfoId, @Path int PageIndex, @Path int PageSize);

    /**
     * @param UserName  登录账号
     * @param UserPw    登录密码
     * @param LoginType 登录类型（1：普通会员，2：VIP用户）
     * @param Kbn       设备类型（1：Android,2:Ios）
     * @return
     */
    @Get("api/ShopContent/Login?UserName={UserName}&UserPw={UserPw}&LoginType={LoginType}&Kbn={Kbn}")
    BaseModelJson<LoginInfo> login(@Path String UserName, @Path String UserPw, @Path String LoginType, @Path String Kbn);

    /**
     * @param map userLogin
     *            passWord
     *            passWordConfirm
     *            zy
     *            MemberEmail
     *            MemberRealName
     *            UserType
     * @return
     */
    @Post("api/ShopContent/Register")
    BaseModel register(@Body Map map);

    /**
     * 为服务类准备
     * 查询一级分类带二级分类的列表
     *
     * @param GoodsTypePid (1邮寄类,2服务类)
     * @return
     */
    @Get("api/ShopContent/GetGoodsType?GoodsTypePid={GoodsTypePid}")
    BaseModelJson<List<GoodsTypeModel>> getGoodsType(@Path String GoodsTypePid);

    /**
     * 根据店铺ID查询店铺详细
     *
     * @param StoreInfoId
     * @return
     */
    @Get("api/ShopContent/GetStoreDetailById?StoreInfoId={StoreInfoId}")
    BaseModelJson<StoreDetailModel> getStoreDetailById(@Path String StoreInfoId);


    /**
     * 查询服务类店铺
     *
     * @param StoreName 名称
     * @param PageIndex 当前页数
     * @param PageSize  页面大小
     * @return
     */
    @Get("api/ShopContent/GetStoreInfoByGoodsType?StoreName={StoreName}&PageIndex={PageIndex}&PageSize={PageSize}")
    BaseModelJson<PagerResult<StoreDetailModel>> getStoreInfoByGoodsType(@Path String StoreName, @Path int PageIndex, @Path int PageSize);


    /**
     * 查询省下拉数据
     *
     * @return
     */
    @Get("api/ShopContent/GetAllProvinceList")
    BaseModelJson<List<NewProvince>> getAllProvinceList();

    /**
     * 查询市下拉数据
     *
     * @return
     */
    @Get("api/ShopContent/GetCityListByProvinceId?ProvinceId={ProvinceId}")
    BaseModelJson<List<NewCity>> getCityListByProvinceId(@Path String ProvinceId);

    /**
     * 查询区下拉数据
     *
     * @return
     */
    @Get("api/ShopContent/GetAreaListByCityId?CityId={CityId}")
    BaseModelJson<List<NewArea>> getAreaListByCityId(@Path String CityId);

    /**
     * 查询区域（包括商圈）根据城市id
     *
     * @param CityId 城市id
     * @return
     */
    @Get("api/ShopContent/GetAreaByCity?CityId={CityId}")
    BaseModelJson<List<NewArea>> getAreaByCity(@Path String CityId);

    /**
     * 根据 商业圈,商品类别 查询商品
     *
     * @param StreetInfoId 商圈id
     * @param GoodsTypeId  分类id
     * @param PageIndex    当前页
     * @param PageSize     条数
     * @return
     */
    @Get("api/ShopContent/GetGoodsInfo?StreetInfoId={StreetInfoId}&GoodsTypeId={GoodsTypeId}&PageIndex={PageIndex}&PageSize={PageSize}")
    BaseModelJson<PagerResult<Goods>> getGoodsInfo(@Path int StreetInfoId, @Path int GoodsTypeId, @Path int PageIndex, @Path int PageSize);


    /**
     * 查询店铺中的商品
     *
     * @param StoreInfoId 店铺id
     * @param PageIndex   当前页
     * @param PageSize    条数
     * @return
     */
    @Get("api/ShopContent/GetStoreAllGoodsList?StoreInfoId={StoreInfoId}&PageIndex={PageIndex}&PageSize={PageSize}")
    BaseModelJson<PagerResult<Goods>> getStoreAllGoodsList(@Path String StoreInfoId, @Path int PageIndex, @Path int PageSize);


    /**
     * 查询店铺中推荐的商品
     *
     * @param StoreInfoId 店铺id
     * @return
     */
    @Get("api/ShopContent/GetStoreIsCommendGoodsList?StoreInfoId={StoreInfoId}")
    BaseModelJson<List<Goods>> getStoreIsCommendGoodsList(@Path String StoreInfoId);

    /**
     * 查询店铺中上新的商品
     *
     * @param StoreInfoId 店铺id
     * @return
     */
    @Get("api/ShopContent/GetStoreNewGoodsList?StoreInfoId={StoreInfoId}")
    BaseModelJson<List<Goods>> getStoreNewGoodsList(@Path String StoreInfoId);


    /**
     * 根据 商业圈,商品类别 查询店铺
     *
     * @param StreetInfoId   商商圈id 全部 0
     * @param GoodsTypeId    一级分类id 全部0 （二级级分类为0 一级分类不能为0）
     * @param TwoGoodsTypeId 二级级分类id 全部0
     * @param CityId         城市id 全部为空 区域id和商圈id为空或0的时候值不能为空
     * @param AreaId         区域id 全部为空
     * @param PageIndex      当前页
     * @param PageSize       条数
     * @return
     */
    @Get("api/ShopContent/GetStoreInfo?StreetInfoId={StreetInfoId}&GoodsTypeId={GoodsTypeId}&TwoGoodsTypeId={TwoGoodsTypeId}&CityId={CityId}&AreaId={AreaId}&PageIndex={PageIndex}&PageSize={PageSize}")
    BaseModelJson<PagerResult<StoreDetailModel>> getStoreInfo(@Path int StreetInfoId, @Path int GoodsTypeId, @Path int TwoGoodsTypeId, @Path String CityId, @Path String AreaId, @Path int PageIndex, @Path int PageSize);

    /**
     * 查询服务类推荐商品
     *
     * @param CityId    城市id 全国传空
     * @param PageIndex 当前页号
     * @param PageSize  条数
     * @return
     * @see Goods
     */
    @Get("api/ShopContent/GetGoodsInfoByCity?CityId={CityId}&PageIndex={PageIndex}&PageSize={PageSize}")
    BaseModelJson<PagerResult<Goods>> getGoodsInfoByCity(@Path String CityId, @Path int PageIndex, @Path int PageSize);

    /**
     * 根据城市名称查询城市ID
     *
     * @param CityName 城市名称
     * @return
     */
    @Get("api/ShopContent/GetCityId?CityName={CityName}")
    BaseModelJson<CityModel> getCityId(@Path String CityName);

    /**
     * @param GoodsTypeId 商品分类id
     * @param GoodsType   是否是服务类商品(1:服务类，2：邮寄类)
     * @param GodosName   商品名称
     * @param OB          1:综合排序，2：销量降序，3：价格降序，4：价格升序
     * @param MinPrice    最小钱
     * @param MaxPrice    最大钱
     * @param PageIndex   当前页
     * @param PageSize    条数
     * @return
     */
    @Get("api/ShopContent/GetGoodsByGoodsTypeId?GoodsTypeId={GoodsTypeId}&GoodsType={GoodsType}&GodosName={GodosName}&OB={OB}&MinPrice={MinPrice}&MaxPrice={MaxPrice}&PageIndex={PageIndex}&PageSize={PageSize}&StoreInfoId={StoreInfoId}")
    BaseModelJson<PagerResult<Goods>> getGoodsByGoodsTypeId(@Path String GoodsTypeId, @Path String GoodsType, @Path String GodosName, @Path String OB, @Path String MinPrice, @Path String MaxPrice, @Path int PageIndex, @Path int PageSize, @Path String StoreInfoId);

    /**
     * 单商品生成临时订单信息
     *
     * @param GoodsInfoId
     * @param number
     * @return
     */
    @Get("api/Shop/CreateTempGoodsOrderInfo?GoodsInfoId={GoodsInfoId}&number={number}&StoreInfoId={StoreInfoId}&GoodsAttributeId={GoodsAttributeId}")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModelJson<ShopOrder> createTempGoodsOrderInfo(@Path String GoodsInfoId, @Path int number, @Path String StoreInfoId, @Path int GoodsAttributeId);

    /**
     * 购物车生成临时订单信息
     *
     * @param BuyCartInfoIds 购物车 商品id 以,分开 example：  adfa,asdfasf,adfa
     * @param StoreInfoId    店铺Id
     * @return
     */
    @Get("api/Shop/CreateTempOrderInfo?BuyCartInfoIds={BuyCartInfoIds}&StoreInfoId={StoreInfoId}")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModelJson<ShopOrder> createTempOrderInfo(@Path String BuyCartInfoIds, @Path String StoreInfoId);

    /**
     * 根据订单ID取消订单
     *
     * @param id 根据订单ID
     * @return
     */
    @Post("api/Shop/CancelOrderById/{id}")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModel cancelOrderById(@Path String id);


    /**
     * 根据订单ID查询订单支付信息
     *
     * @param OrderId 根据订单ID
     * @return
     */
    @Get("api/Shop/GetPaymentOrder?OrderId={OrderId}")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModelJson<ShopOrder> getPaymentOrder(@Path String OrderId);

    /**
     * 查询收货地址
     *
     * @return
     */
    @Get("api/Shop/GetMReceiptAddressListByUserInfoId")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModelJson<List<MReceiptAddressModel>> getMReceiptAddressListByUserInfoId();

    /**
     * 新增收货地址
     *
     * @param model
     * @return
     */
    @Post("api/Shop/AddMReceiptAddress")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModel addMReceiptAddress(@Body MReceiptAddressModel model);

    /**
     * 修改收货地址
     *
     * @param model
     * @return
     */
    @Post("api/Shop/UpdMReceiptAddress")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModel updMReceiptAddress(@Body MReceiptAddressModel model);

    /**
     * 设置默认收货地址
     *
     * @param map MReceiptAddressId
     * @return
     */
    @Post("api/Shop/UpdDefaultReceiptAddress")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModel updDefaultReceiptAddress(@Body Map map);

    /**
     * 删除收货地址
     *
     * @param map MReceiptAddressId
     * @return
     */
    @Post("api/Shop/DelReceiptAddress")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModel delReceiptAddress(@Body Map map);

    /**
     * 查询收货地址
     *
     * @return
     */
    @Get("api/Shop/GetMReceiptAddressById?MReceiptAddressId={MReceiptAddressId}")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModelJson<MReceiptAddressModel> getMReceiptAddressById(@Path int MReceiptAddressId);

    /**
     * 商品加入购物车
     * 购物车加1
     *
     * @param map GoodsInfoId 商品ID
     *            GoodsAttributeId 属性规格ID
     * @return
     */
    @Post("api/Shop/AddShoppingCart")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModel addShoppingCart(@Body Map map);

    /**
     * 购物车减1
     *
     * @param map GoodsInfoId
     * @return
     */
    @Post("api/Shop/SubShoppingCart")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModel subShoppingCart(@Body Map map);

    /**
     * 修改会员信息
     *
     * @param map MemberEmail
     *            MemberQQ
     *            MemberBlog
     *            HeadImg
     * @return
     */
    @Post("api/Shop/UpdateMemberInfo")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModel updateMemberInfo(@Body Map map);

    /***
     * 查询用户信息
     *
     * @return
     */
    @Get("api/Shop/GetMemberInfo")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModelJson<MemberInfo> getMemberInfo();

    /**
     * 查询会员自己所有的订单信息
     *
     * @param PageIndex    当前页
     * @param PageSize     每页显示多少
     * @param MorderStatus 订单状态(0:待支付，1：已支付，2:已取消,3：已发货4:确认收货,5:交易完成,9)
     * @return
     */
    @Get("api/Shop/GetAllOrderInfoList?PageIndex={PageIndex}&PageSize={PageSize}&MorderStatus={MorderStatus}")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModelJson<PagerResult<ShopOrder>> getAllOrderInfoList(@Path int PageIndex, @Path int PageSize, @Path int MorderStatus);

    /**
     * 单个商品下单支付
     *
     * @param map GoodsInfoId 商品id
     *            number 数量
     *            DZB 电子币
     *            TwoPass 支付密码
     *            PayType 支付方式  1 支付宝 2 微信 3 银联
     * @return
     */
    @Post("api/Shop/CreateGoodsOrderInfo")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModelJson<ConfirmOrderModel> createGoodsOrderInfo(@Body Map map);

    /**
     * 购物车下单支付
     *
     * @param model BuyCartInfoIds 商品id
     *              StoreInfoId 数量
     *              DZB 电子币
     *              TwoPass 支付密码
     *              PayType 支付方式  1 支付宝 2 微信 3 银联
     * @return
     * @see ShopOrder
     */
    @Post("api/Shop/CreateOrderInfo")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModelJson<ShopOrder> createOrderInfo(@Body ShopOrder model);

    /**
     * 根据订单ID查询订单信息
     *
     * @param MOrderId 订单ID
     * @return
     */
    @Get("api/Shop/GetOrderDetailById?MOrderId={MOrderId}")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModelJson<ShopOrder> getOrderDetailById(@Path String MOrderId);

    /**
     * @param MOrderId 订单id
     * @return
     */
    @Get("api/Shop/GetLogistics?MOrderId={MOrderId}")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModelJson<List<LogisticsInfo>> getLogistics(@Path String MOrderId);

    /**
     * 确认收货
     * 两个id传一个就行
     *
     * @param map MOrderDetailId
     *            MOrderId
     * @return
     */
    @Post("api/Shop/ConfirmReceipt")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModel confirmReceipt(@Body Map map);

    /**
     * 查询待评价订单
     *
     * @param PageIndex 当前页数
     * @param PageSize  每页显示多少
     * @return
     */
    @Get("api/Shop/GetOrderMorderStatus?PageIndex={PageIndex}&PageSize={PageSize}")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModelJson<PagerResult<OrderDetailModel>> getOrderMorderStatus(@Path int PageIndex, @Path int PageSize);

    /**
     * 添加商品评论
     *
     * @param map XNum 星星
     *            GoodsInfoId 商品id
     *            MOrderDetailId 订单详细id
     *            GoodsCommentsDj 评论等级(1:好评，2：中评，3：差评)
     *            GoodsCommentsNr 评论内容
     * @return
     */
    @Post("api/Shop/InsertGoodsComments")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModel publishReview(@Body Map map);

    /**
     * 修改用户密码信息
     *
     * @param map OldPw
     *            NewPw
     *            ConfirmPw
     *            UserType
     * @return
     */
    @Post("api/Shop/UpdPwd")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModel changePassword(@Body Map map);

    /**
     * 删除购物车商品
     *
     * @param map BuyCartInfoIds  以逗号隔开
     * @return
     */
    @Post("api/Shop/DelShoppingCartCountByIds")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModel deleteShoppingCartById(@Body Map map);

    /**
     * 更新头像
     * HttpHeaders.CONTENT_TYPE
     *
     * @param data
     * @return
     */
    @Post("http://updimage.86fuwuwang.com/FileHandler.ashx?type=&folder=Shop")
    @RequiresHeader(value = {HttpHeaders.CONTENT_TYPE})
    String uploadAvatar(@Body MultiValueMap<String, Object> data);

    /**
     * 更新头像
     *
     * @param map HeadImg
     * @return
     */
    @Post("api/Shop/UpdateMemberInfoImg")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModelJson<String> updateMemberInfoImg(@Body Map map);

    /**
     * 获取默认收货地址
     *
     * @return
     * @see MReceiptAddressModel
     */
    @Get("api/Shop/GetUserDefaultAddress")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModelJson<MReceiptAddressModel> getUserDefaultAddress();

    /**
     * @param CompanyName
     * @param CityCode
     * @param PageIndex
     * @param PageSize
     * @return
     */
    //联盟商家列表
    @Get("api/ShopContent/GetShopCompany?CompanyName={CompanyName}&CityCode={CityCode}&PageIndex={PageIndex}&PageSize={PageSize}")
    BaseModelJson<PagerResult<CooperationMerchant>> getShopCompany(@Path String CompanyName, @Path String CityCode, @Path int PageIndex, @Path int PageSize);

    /**
     * 根据商家ID查询商家明细信息
     *
     * @param CompanyId
     * @return
     */
    @Get("api/ShopContent/GetCompanyDetailById?CompanyId={CompanyId}")
    BaseModelJson<CooperationMerchant> getCompanyDetailById(@Path String CompanyId);

    /**
     * 查询会员OpenId
     *
     * @return
     */
    @Get("api/Member/PreShare")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModelJson<String> preShare();


    /**
     * 查询会员OpenId
     *
     * @return
     */
    @Get("api/Shop/getUserOrderCount")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModelJson<OrderCountModel> getUserOrderCount();

}
