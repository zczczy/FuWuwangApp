package com.zczczy.leo.fuwuwangapp.activities;

import android.widget.EditText;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.MemberInfo;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.HashMap;

/**
 * Created by zczczy on 2016/5/5.
 */
@EActivity(R.layout.activity_member)
public class MemberInfoActivity extends BaseActivity {

    @ViewById
    TextView txt_change;

    @ViewById
    EditText txt_name, edt_blog, edt_qq, edt_email;

    @RestService
    MyDotNetRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        AndroidTool.showLoadDialog(this);
        getMemberInfo();
    }

    @Background
    void getMemberInfo() {
        myRestClient.setHeader("Token", pre.token().get());
        myRestClient.setHeader("ShopToken", pre.shopToken().get());
        myRestClient.setHeader("Kbn", MyApplication.ANDROID);
        afterGetMemberInfo(myRestClient.getMemberInfo());
    }

    @UiThread
    void afterGetMemberInfo(BaseModelJson<MemberInfo> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (!bmj.Successful) {
            AndroidTool.showToast(this, bmj.Error);
        } else {
            txt_name.setText(bmj.Data.MemberRealName);
            edt_email.setText(bmj.Data.MemberEmail);
            edt_qq.setText(bmj.Data.MemberQQ);
            edt_blog.setText(bmj.Data.MemberBlog);
        }
    }


    //保存
    @Click
    void btn_save() {
//        if (AndroidTool.checkIsNull(edt_blog)) {
//            AndroidTool.showToast(this, "微博不能为空");
//        } else if (AndroidTool.checkIsNull(edt_qq)) {
//            AndroidTool.showToast(this, "qq不能为空");
//        } else if (AndroidTool.checkIsNull(edt_email)) {
//            AndroidTool.showToast(this, "邮箱不能为空");
//        } else if (AndroidTool.checkEmail(edt_email)) {
//            AndroidTool.showToast(this, "邮箱格式不正确");
//        } else {
//        }
        changeInfo(edt_email.getText().toString().trim(), edt_qq.getText().toString().trim(), edt_blog.getText().toString().trim());
    }

    @Click
    void ll_shipping() {
        ShippingAddressActivity_.intent(this).start();
    }

    @Background
    void changeInfo(String MemberEmail, String MemberQQ, String MemberBlog) {
        myRestClient.setHeader("Token", pre.token().get());
        myRestClient.setHeader("ShopToken", pre.shopToken().get());
        myRestClient.setHeader("kbn", MyApplication.ANDROID);
        HashMap<String, String> map = new HashMap<>();
        map.put("MemberBlog", MemberBlog);
        map.put("MemberQQ", MemberQQ);
        map.put("MemberEmail", MemberEmail);
        map.put("MemberRealName", txt_name.getText().toString().trim());
        BaseModel bmj = myRestClient.updateMemberInfo(map);
        afterChange(bmj);
    }

    @UiThread
    void afterChange(BaseModel bmj) {
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (bmj.Successful) {
            AndroidTool.showToast(this, "保存成功");
            finish();
        } else {
            AndroidTool.showToast(this, bmj.Error);
        }
    }
}
