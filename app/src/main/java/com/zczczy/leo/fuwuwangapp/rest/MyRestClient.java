package com.zczczy.leo.fuwuwangapp.rest;


import com.zczczy.leo.fuwuwangapp.model.Activity;
import com.zczczy.leo.fuwuwangapp.model.AllCity;
import com.zczczy.leo.fuwuwangapp.model.Announcement;
import com.zczczy.leo.fuwuwangapp.model.Bank;
import com.zczczy.leo.fuwuwangapp.model.Banner;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.CardInfo;
import com.zczczy.leo.fuwuwangapp.model.CityModel;
import com.zczczy.leo.fuwuwangapp.model.CooperationMerchant;
import com.zczczy.leo.fuwuwangapp.model.Experience;
import com.zczczy.leo.fuwuwangapp.model.FwwUser;
import com.zczczy.leo.fuwuwangapp.model.Information;
import com.zczczy.leo.fuwuwangapp.model.Notice;
import com.zczczy.leo.fuwuwangapp.model.OpenAccount;
import com.zczczy.leo.fuwuwangapp.model.PagerResult;
import com.zczczy.leo.fuwuwangapp.model.PaperFace;
import com.zczczy.leo.fuwuwangapp.model.ProvinceModel;
import com.zczczy.leo.fuwuwangapp.model.Purse;
import com.zczczy.leo.fuwuwangapp.model.QueueCompanyDetail;
import com.zczczy.leo.fuwuwangapp.model.QueueCount;
import com.zczczy.leo.fuwuwangapp.model.QueueMDetailModel;
import com.zczczy.leo.fuwuwangapp.model.UpdateApp;
import com.zczczy.leo.fuwuwangapp.model.UserBaseInfo;
import com.zczczy.leo.fuwuwangapp.model.UserFinanceInfo;
import com.zczczy.leo.fuwuwangapp.model.Volume;
import com.zczczy.leo.fuwuwangapp.model.Wealth;
import com.zczczy.leo.fuwuwangapp.model.YpdRecord;


import org.androidannotations.rest.spring.annotations.Accept;
import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Delete;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Put;
import org.androidannotations.rest.spring.annotations.RequiresAuthentication;
import org.androidannotations.rest.spring.annotations.RequiresHeader;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientErrorHandling;
import org.androidannotations.rest.spring.api.RestClientHeaders;
import org.androidannotations.rest.spring.api.RestClientRootUrl;
import org.androidannotations.rest.spring.api.RestClientSupport;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Leo on 2015/3/9.
 */
//http://124.254.56.58:8007/
//http://appapia.86fuwuwang.com/

@Rest(rootUrl = "http://192.168.0.198:8002/", requestFactory = MyRequestFactory.class, interceptors = { MyInterceptor.class },
        converters = {StringHttpMessageConverter.class,	MappingJackson2HttpMessageConverter.class,FormHttpMessageConverter.class, ByteArrayHttpMessageConverter.class })
public interface MyRestClient extends RestClientRootUrl, RestClientSupport, RestClientHeaders, RestClientErrorHandling {

    @Get("api/Content/AppUpdCheck?kbn={kbn}")
    BaseModelJson<UpdateApp> AppUpdCheck(@Path int kbn);

    @Get("api/Content/GetAppConfig/{id}")
    BaseModelJson<Announcement>GetAppConfig(@Path int id);

    @Post("/Post/{id}")
    @RequiresHeader(value = {"Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE, "Content-Disposition", "filename", "charset"})
    String uploadAvatar(@Path int id, @Body MultiValueMap<String, Object> formData);

    @Post("/testPostEntity/{id}")
    BaseModelJson<Notice> testPostEntity(@Path int id,@Body BaseModel bm);


    @Delete("/deleteTest/{id}")
    @RequiresAuthentication
    BaseModel deleteTest(@Path int id);


    @Get("/Login/{id}")
    BaseModel login(@Path int id);

    /**
     * 必须传入一个JSESSIONID
     * 也就是说，必须在登录的情况下才可以
     *
     * @param page
     * @param rows
     * @return
     */
    @Post("/getVideoInfoList/{page}/{rows}")
    String getVideoInfoList(@Path int page,@Path int rows);

    @Put("/Put")
    String putTest(@Body  BaseModel bm);

    /**
     * 非会员模块
     */
    //公告列表
    @Get("api/Content/GetNoticeList?PageIndex={PageIndex}&PageSize={PageSize}")
    BaseModelJson<PagerResult<Notice>> GetNoticeList(@Path int PageIndex, @Path int PageSize);

    //资讯列表
    @Get("api/Content/GetNewsList?PageIndex={PageIndex}&PageSize={PageSize}")
    BaseModelJson<PagerResult<Information>> GetInformationList(@Path int PageIndex,@Path int PageSize);

    //麻团基金列表
    @Get("api/Content/GetMtFuncList?PageIndex={PageIndex}&PageSize={PageSize}")
    BaseModelJson<PagerResult<Notice>> GetMtFuncList(@Path int PageIndex, @Path int PageSize);

    //联盟商家列表
    @Get("api/Content/GetCompany?CompanyName={CompanyName}&CityCode={CityCode}&PageIndex={PageIndex}&PageSize={PageSize}")
    BaseModelJson<PagerResult<CooperationMerchant>> GetCompany(@Path String CompanyName, @Path String CityCode,@Path  int PageIndex, @Path int PageSize);

    //体验中心列表
    @Get("api/Content/GetBusinessList?BusinessName={BusinessName}&CityCode={CityCode}&PageIndex={PageIndex}&PageSize={PageSize}")
    BaseModelJson<PagerResult<Experience>> GetBusinessList(@Path String BusinessName,@Path  String CityCode,@Path  int PageIndex, @Path int PageSize);

    //活动列表
    @Get("api/Content/GetActivity?PageIndex={PageIndex}&PageSize={PageSize}")
    BaseModelJson<PagerResult<Activity>> GetActivity(@Path int PageIndex,@Path  int PageSize);

    //用户登录验证
    @Post("api/Content/SignIn?userLogin={userLogin}&userPass={userPass}")
    BaseModelJson<String> SignIn(@Path String userLogin,@Path  String userPass);

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

    //保存个人信息
    @Post("api/Member/SaveBaseInfo?RealName={RealName}&Mobile={Mobile}&ZipCode={ZipCode}&Address={Address}&EjPass={EjPass}&IdCard={IdCard}&Email={Email}")
    @RequiresHeader("Token")
    BaseModelJson<String> SaveBaseInfo(@Path String RealName,@Path  String Mobile,@Path  String ZipCode, @Path String Address, @Path String EjPass,@Path  String IdCard,@Path  String Email);

    //意见反馈
    @Post("api/Member/FeedBack?Content={Content}")
    @RequiresHeader("Token")
    BaseModelJson<String> FeedbackInfo(@Path String Content);

    //获取会员财务信息
    @Get("api/Member/GetFinancialById")
    @RequiresHeader("Token")
    BaseModelJson<UserFinanceInfo> GetFinancialById();

    //保存会员财务信息
    @Post("api/Member/SaveFinancial?PayeeName={PayeeName}&BankCode={BankCode}&BankName={BankName}&Province={Province}&City={City}&BankNumber={BankNumber}&EjPass={EjPass}&IdCard={IdCard}")
    @RequiresHeader("Token")
    BaseModelJson<String> SaveFinancial(@Path String PayeeName, @Path String BankCode, @Path String BankName, @Path String Province, @Path String City, @Path String BankNumber,@Path  String EjPass,@Path  String IdCard);

    //电子钱包提现申请
    @Post("api/Member/WithdrawalsApply?money={money}&description={description}&pw={pw}")
    @RequiresHeader("Token")
    BaseModelJson<String> WithdrawalsApply(@Path BigDecimal money, @Path String description,@Path  String pw);

    //转账给朋友
    @Post("api/Member/TransferAccounts?money={money}&recipientname={recipientname}&pw={pw}")
    @RequiresHeader("Token")
    BaseModelJson<String> TransferAccounts(@Path BigDecimal money, @Path String recipientname,@Path  String pw);

    //电子钱包交易明细
    @Get("api/Member/WalletTransaction?PageIndex={PageIndex}&PageSize={PageSize}")
    @RequiresHeader("Token")
    BaseModelJson<PagerResult<Purse>> WalletTransaction(@Path int PageIndex, @Path int PageSize);
    //我的联盟会员
    @Get("api/Member/GetUnionMember?PageIndex={PageIndex}&PageSize={PageSize}&PId={PId}")
    @RequiresHeader("Token")
    BaseModelJson<PagerResult<Purse>> GetUnionMember(@Path int PageIndex,@Path  int PageSize,@Path  int PId);
    //查询兑现券数量
    @Get("api/Member/GetQueueCount")
    @RequiresHeader("Token")
    BaseModelJson<Volume> GetQueueCount();

    //电子券批量排队处理
    @Post("api/Member/PlPd?mianzhi={mianzhi}&guize={guize}&duilie={duilie}&companyLogin={companyLogin}&paiduinum={paiduinum}")
    @RequiresHeader("Token")
    BaseModelJson<String> PlPd(@Path String mianzhi,@Path  String guize, @Path String duilie,@Path  String companyLogin,@Path  String paiduinum);

    //纸卷登录验证
    @Post("api/Member/LoginQueue?queueNo={queueNo}&queuePw={queuePw}")
    @RequiresHeader("Token")
    BaseModelJson<PaperFace> LoginQueue(@Path String queueNo, @Path String queuePw);

    //扫描兑现券二维码后验证
    @Post("api/Member/ScanLogin?code={code}")
    @RequiresHeader("Token")
    BaseModelJson<PaperFace> ScanLogin(@Path String code);

    //纸卷排队
    @Post("api/Member/SinglePd?qi_id={qi_id}&queuesId={queuesId}&queuesInRule={queuesInRule}&guize={guize}&duilie={duilie}&companyLogin={companyLogin}")
    @RequiresHeader("Token")
    BaseModelJson<String> SinglePd(@Path String qi_id, @Path String queuesId,@Path  String queuesInRule, @Path String guize,@Path  String duilie, @Path String companyLogin);

//    //推荐注册
//    @Post("api/Member/Register?userLogin={userLogin}&passWord={passWord}&passWordConfirm={passWordConfirm}&realName={realName}&idCard={idCard}&dq={dq}")
//    @RequiresHeader("Token")
//    BaseModelJson<String> SuRegister(String userLogin, String passWord, String passWordConfirm);

    //批量转让兑现券接口
    @Post("api/Member/PlZrRecord?mz={mz}&num={num}&ulogin={ulogin}&paypwd={paypwd}")
    @RequiresHeader("Token")
    BaseModelJson<String> PlZrRecord(@Path String mz, @Path String num,@Path  String ulogin, @Path String paypwd);

    //兑现券管理
    @Get("api/Member/QueueM")
    @RequiresHeader("Token")
    BaseModelJson<QueueCount> QueueM();

    //查询50进1兑现券详细
    @Get("api/Member/QueueMDetail?QueuesInRule={QueuesInRule}")
    @RequiresHeader("Token")
    BaseModelJson<QueueMDetailModel> QueueMDetail(@Path String QueuesInRule);

    //活动报名
    @Post("api/Member/Apply?Aid={Aid}")
    @RequiresHeader("Token")
    BaseModelJson<String> Apply(@Path int Aid);

    //查询所有城市信息
    @Get("api/Content/GetAllCity")
    BaseModelJson<List<AllCity>> GetAllCity();

    //根据城市名称模糊查询当前城市code
    @Get("api/Content/GetCityCodeByName?Cname={Cname}")
    BaseModelJson<String> GetCityCodeByName(@Path String Cname);

    //查询商家坐标信息
    @Get("api/Member/GetCompanyZb")
    @RequiresHeader("Token")
    BaseModelJson<String> GetCompanyZb();

    //保存商家经纬度
    @Post("api/Member/SaveCompanyZb?latitude={latitude}&longitude={longitude}")
    @RequiresHeader("Token")
    BaseModelJson<String> SaveCompanyZb(@Path String latitude,@Path  String longitude);

    //查询会员商家队列明细
    @Get("api/Member/GetCompanyQueueDetail?queueRuleId={queueRuleId}")
    @RequiresHeader("Token")
    BaseModelJson<List<QueueCompanyDetail>> GetCompanyQueueDetail(@Path String queueRuleId);

    //查询会员当日已排队信息
    @Get("api/Member/GetCurrYpdInfo?date={date}&flag={flag}")
    @RequiresHeader("Token")
    BaseModelJson<List<YpdRecord>> GetCurrYpdInfo(@Path String date, @Path String flag);

    //身份证识别接口
    @Post("DetailPage/GetIdCardInfo")
    @Accept(MediaType.MULTIPART_FORM_DATA_VALUE)
    @RequiresHeader(value = { "Content-Type", "Content-Disposition","charset", "connection" })
    BaseModelJson<CardInfo> GetIdCardInfo(@Body MultiValueMap<String, Object> form);

    //会员快速注册接口
    @Post("api/Member/FastRegister?IdCard={IdCard}&RealName={RealName}")
    @RequiresHeader("Token")
    BaseModel FastRegister(@Path String IdCard,@Path  String RealName);
}
