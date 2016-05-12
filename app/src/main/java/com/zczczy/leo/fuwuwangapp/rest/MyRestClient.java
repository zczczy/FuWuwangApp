package com.zczczy.leo.fuwuwangapp.rest;


import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.CardInfo;
import com.zczczy.leo.fuwuwangapp.model.Notice;
import com.zczczy.leo.fuwuwangapp.model.PaperFace;

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

/**
 * Created by Leo on 2015/3/9.
 */
//http://124.254.56.58:8007/
//http://192.168.0.198:8002/
//http://appapia.86fuwuwang.com/

@Rest(rootUrl = "http://218.61.203.50:8002/", requestFactory = MyRequestFactory.class, interceptors = {MyInterceptor.class},
        converters = {StringHttpMessageConverter.class, MappingJackson2HttpMessageConverter.class, FormHttpMessageConverter.class, ByteArrayHttpMessageConverter.class})
public interface MyRestClient extends RestClientRootUrl, RestClientSupport, RestClientHeaders, RestClientErrorHandling {


    @Post("/Post/{id}")
    @RequiresHeader(value = {"Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE, "Content-Disposition", "filename", "charset"})
    String uploadAvatar(@Path int id, @Body MultiValueMap<String, Object> formData);

    //保存个人信息
    @Post("api/Member/SaveBaseInfo?RealName={RealName}&Mobile={Mobile}&ZipCode={ZipCode}&Address={Address}&EjPass={EjPass}&IdCard={IdCard}&Email={Email}")
    @RequiresHeader("Token")
    BaseModelJson<String> SaveBaseInfo(@Path String RealName, @Path String Mobile, @Path String ZipCode, @Path String Address, @Path String EjPass, @Path String IdCard, @Path String Email);

    //意见反馈
    @Post("api/Member/FeedBack?Content={Content}")
    @RequiresHeader("Token")
    BaseModelJson<String> FeedbackInfo(@Path String Content);


    //保存会员财务信息
    @Post("api/Member/SaveFinancial?PayeeName={PayeeName}&BankCode={BankCode}&BankName={BankName}&Province={Province}&City={City}&BankNumber={BankNumber}&EjPass={EjPass}&IdCard={IdCard}")
    @RequiresHeader("Token")
    BaseModelJson<String> SaveFinancial(@Path String PayeeName, @Path String BankCode, @Path String BankName, @Path String Province, @Path String City, @Path String BankNumber, @Path String EjPass, @Path String IdCard);

    //电子钱包提现申请
    @Post("api/Member/WithdrawalsApply?money={money}&description={description}&pw={pw}")
    @RequiresHeader("Token")
    BaseModelJson<String> WithdrawalsApply(@Path BigDecimal money, @Path String description, @Path String pw);

    //转账给朋友
    @Post("api/Member/TransferAccounts?money={money}&recipientname={recipientname}&pw={pw}")
    @RequiresHeader("Token")
    BaseModelJson<String> TransferAccounts(@Path BigDecimal money, @Path String recipientname, @Path String pw);

    //电子券批量排队处理
    @Post("api/Member/PlPd?mianzhi={mianzhi}&guize={guize}&duilie={duilie}&companyLogin={companyLogin}&paiduinum={paiduinum}")
    @RequiresHeader("Token")
    BaseModelJson<String> PlPd(@Path String mianzhi, @Path String guize, @Path String duilie, @Path String companyLogin, @Path String paiduinum);

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
    BaseModelJson<String> SinglePd(@Path String qi_id, @Path String queuesId, @Path String queuesInRule, @Path String guize, @Path String duilie, @Path String companyLogin);

//    //推荐注册
//    @Post("api/Member/Register?userLogin={userLogin}&passWord={passWord}&passWordConfirm={passWordConfirm}&realName={realName}&idCard={idCard}&dq={dq}")
//    @RequiresHeader("Token")
//    BaseModelJson<String> SuRegister(String userLogin, String passWord, String passWordConfirm);

    //批量转让兑现券接口
    @Post("api/Member/PlZrRecord?mz={mz}&num={num}&ulogin={ulogin}&paypwd={paypwd}")
    @RequiresHeader("Token")
    BaseModelJson<String> PlZrRecord(@Path String mz, @Path String num, @Path String ulogin, @Path String paypwd);


    //活动报名
    @Post("api/Member/Apply?Aid={Aid}")
    @RequiresHeader("Token")
    BaseModelJson<String> Apply(@Path int Aid);


    //查询商家坐标信息
    @Get("api/Member/GetCompanyZb")
    @RequiresHeader("Token")
    BaseModelJson<String> GetCompanyZb();

    //保存商家经纬度
    @Post("api/Member/SaveCompanyZb?latitude={latitude}&longitude={longitude}")
    @RequiresHeader("Token")
    BaseModelJson<String> SaveCompanyZb(@Path String latitude, @Path String longitude);


    //身份证识别接口
    @Post("DetailPage/GetIdCardInfo")
    @Accept(MediaType.MULTIPART_FORM_DATA_VALUE)
    @RequiresHeader(value = {"Content-Type", "Content-Disposition", "charset", "connection"})
    BaseModelJson<CardInfo> GetIdCardInfo(@Body MultiValueMap<String, Object> form);

    //会员快速注册接口
    @Post("api/Member/FastRegister?IdCard={IdCard}&RealName={RealName}")
    @RequiresHeader("Token")
    BaseModel FastRegister(@Path String IdCard, @Path String RealName);

    //用户登录验证
    @Post("api/Content/SignIn?userLogin={userLogin}&userPass={userPass}")
    BaseModelJson<String> SignIn(@Path String userLogin, @Path String userPass);
}
