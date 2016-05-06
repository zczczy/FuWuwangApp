package com.zczczy.leo.fuwuwangapp.rest;

import com.zczczy.leo.fuwuwangapp.model.AdvertModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.CartInfo;
import com.zczczy.leo.fuwuwangapp.model.ConfirmOrderModel;
import com.zczczy.leo.fuwuwangapp.model.Goods;
import com.zczczy.leo.fuwuwangapp.model.GoodsCommentsModel;
import com.zczczy.leo.fuwuwangapp.model.GoodsDetailModel;
import com.zczczy.leo.fuwuwangapp.model.GoodsTypeModel;
import com.zczczy.leo.fuwuwangapp.model.LoginInfo;
import com.zczczy.leo.fuwuwangapp.model.Lottery;
import com.zczczy.leo.fuwuwangapp.model.LotteryConfig;
import com.zczczy.leo.fuwuwangapp.model.LotteryInfo;
import com.zczczy.leo.fuwuwangapp.model.MAppOrder;
import com.zczczy.leo.fuwuwangapp.model.MReceiptAddressModel;
import com.zczczy.leo.fuwuwangapp.model.MemberInfo;
import com.zczczy.leo.fuwuwangapp.model.NewArea;
import com.zczczy.leo.fuwuwangapp.model.NewBanner;
import com.zczczy.leo.fuwuwangapp.model.NewCity;
import com.zczczy.leo.fuwuwangapp.model.NewProvince;
import com.zczczy.leo.fuwuwangapp.model.PagerResult;
import com.zczczy.leo.fuwuwangapp.model.RebuiltRecommendedGoods;
import com.zczczy.leo.fuwuwangapp.model.StoreDetailModel;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.RequiresHeader;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientErrorHandling;
import org.androidannotations.rest.spring.api.RestClientHeaders;
import org.androidannotations.rest.spring.api.RestClientRootUrl;
import org.androidannotations.rest.spring.api.RestClientSupport;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;
import java.util.Map;

/**
 * Created by Leo on 2016/3/2.
 * http://124.254.56.58:8007/
 * http://192.168.0.198:8002/
 * http://appapia.86fuwuwang.com/
 */
@Rest(rootUrl = "http://124.254.56.58:8007/", requestFactory = MyOkHttpClientHttpRequestFactory.class, interceptors = {MyInterceptor.class},
        converters = {StringHttpMessageConverter.class, MappingJackson2HttpMessageConverter.class, FormHttpMessageConverter.class, ByteArrayHttpMessageConverter.class})
public interface MyDotNetRestClient extends RestClientRootUrl, RestClientSupport, RestClientHeaders, RestClientErrorHandling {


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
     * 获取龙币
     * Header  Token
     *
     * @return Double
     */
    @Get("api/Member/GetMemberElectronicMoney")
    @RequiresHeader(value = "Token")
    BaseModelJson<Double> GetMemberElectronicMoney();


    /**
     * 获取电子币
     *
     * @return Integer
     */
    @Get("api/Member/GetMemberLongBi")
    @RequiresHeader(value = "Token")
    BaseModelJson<Integer> GetMemberLongBi();


    /**
     * 查询App首页Banner
     */
    @Get("api/ShopContent/GetHomeBanner")
    BaseModelJson<List<NewBanner>> getHomeBanner();

    /**
     * 根据广告区分查询广告信息（1：首页广告，2：服务类页面广告）
     *
     * @param kbn
     * @return
     */
    @Get("api/ShopContent/GetAdvertByKbn?kbn={kbn}")
    BaseModelJson<List<AdvertModel>> getAdvertByKbn(@Path String kbn);

    /**
     * 查询APP首页分类信息
     *
     * @return
     */
    @Get("api/ShopContent/GetHomeGoodsTypeList")
    BaseModelJson<List<GoodsTypeModel>> getHomeGoodsTypeList();


    /**
     * 查询推荐商品
     *
     * @param PageIndex
     * @param PageSize
     * @return
     */
    @Get("api/ShopContent/GetRecommendedGoods?PageIndex={PageIndex}&PageSize={PageSize}")
    BaseModelJson<PagerResult<RebuiltRecommendedGoods>> getRecommendedGoods(@Path int PageIndex, @Path int PageSize);


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
    BaseModelJson<GoodsDetailModel> getGoodsDetailById(@Path String GoodsInfoId);

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
     * 查询分类
     *
     * @param GoodsTypePid (1邮寄类,2服务类) 如果是一级的分类id，则查询相应二级分类
     * @return
     */
    @Get("api/ShopContent/GetGoodsTypeByPid?GoodsTypePid={GoodsTypePid}")
    BaseModelJson<List<GoodsTypeModel>> getGoodsTypeByPid(@Path String GoodsTypePid);


    /**
     * 根据店铺ID查询店铺详细
     *
     * @param StoreInfoId
     * @return
     */
    @Get("api/ShopContent/GetStoreDetailById?StoreInfoId={StoreInfoId}")
    BaseModelJson<StoreDetailModel> getStoreDetailById(@Path String StoreInfoId);


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
     * @param GoodsTypeId 商品分类
     * @param GoodsType   是否是服务类商品(1:服务类，2：邮寄类)
     * @param GodosName   商品名称
     * @param sort        排序（0 默认（推荐降序加时间升序/降序）,1 价格,2 销量）
     * @param desc        asc升序 desc降序
     * @param PageIndex   当前页
     * @param PageSize    条数
     * @return
     */
    @Get("api/ShopContent/GetGoodsByGoodsTypeId?GoodsTypeId={GoodsTypeId}&GoodsType={GoodsType}&GodosName={GodosName}&sort={sort}&desc={desc}&PageIndex={PageIndex}&PageSize={PageSize}")
    BaseModelJson<PagerResult<Goods>> getGoodsByGoodsTypeId(@Path int GoodsTypeId, @Path String GoodsType, @Path String GodosName, @Path int sort, @Path String desc, @Path int PageIndex, @Path int PageSize);


    /**
     * 单商品生成临时订单信息
     *
     * @param GoodsInfoId
     * @param number
     * @return
     */
    @Get("api/Shop/CreateTempGoodsOrderInfo?GoodsInfoId={GoodsInfoId}&number={number}")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModelJson<ConfirmOrderModel> createTempGoodsOrderInfo(@Path String GoodsInfoId, @Path int number);

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
     * @param map GoodsInfoId
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
    BaseModelJson<PagerResult<MAppOrder>> getAllOrderInfoList(@Path int PageIndex, @Path int PageSize, @Path int MorderStatus);

    /**
     * @param map GoodsInfoId 商品id
     *            number 数量
     *            DZB 电子币
     *            TwoPass 支付密码
     * @return
     */
    @Post("api/Shop/CreateGoodsOrderInfo")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModelJson<ConfirmOrderModel> createGoodsOrderInfo(@Body Map map);

    /**
     * 根据订单ID查询订单信息
     * @param MOrderId 订单ID
     * @return
     */
    @Get("api/Shop/GetOrderDetailById?MOrderId={MOrderId}")
    @RequiresHeader(value = {"Token", "ShopToken", "Kbn"})
    BaseModelJson<MAppOrder> getOrderDetailById(@Path String MOrderId);
}
