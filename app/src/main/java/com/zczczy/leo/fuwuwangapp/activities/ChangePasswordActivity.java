package com.zczczy.leo.fuwuwangapp.activities;

import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zczczy on 2016/5/5.
 */
@EActivity(R.layout.activity_change_password)
public class ChangePasswordActivity extends BaseActivity {

    @ViewById
    EditText gar_old, gar_password, gar_password_confirm;

    @RestService
    MyDotNetRestClient myDotNetRestClient;

    @Pref
    MyPrefs_ pre;

    @Bean
    MyErrorHandler myErrorHandler;

    @AfterViews
    void afterView() {
        myDotNetRestClient.setRestErrorHandler(myErrorHandler);
    }

    @Click
    void btn_change() {
        if (AndroidTool.checkIsNull(gar_old)) {
            AndroidTool.showToast(this, "原始密码不能为空");
        } else if (AndroidTool.checkIsNull(gar_password)) {
            AndroidTool.showToast(this, "新始密码不能为空");
        } else if (AndroidTool.checkIsNull(gar_password_confirm)) {
            AndroidTool.showToast(this, "确认密码不能为空");
        } else if (!gar_password.getText().toString().equals(gar_password_confirm.getText().toString())) {
            AndroidTool.showToast(this, "两次密码输入不一致");
        } else {
            AndroidTool.showLoadDialog(this);
            changePassword();
        }
    }

    @Background
    void changePassword() {
        Map<String, String> map = new HashMap<>();
        myDotNetRestClient.setHeader("Token", pre.token().get());
        myDotNetRestClient.setHeader("ShopToken", pre.shopToken().get());
        myDotNetRestClient.setHeader("Kbn", MyApplication.ANDROID);
        map.put("UserPw", gar_old.getText().toString());
        map.put("NewUserPw", gar_password.getText().toString());
        map.put("QNewUserPw", gar_password_confirm.getText().toString());
        afterChangePassword(myDotNetRestClient.changePassword(map));
    }

    @UiThread
    void afterChangePassword(BaseModel bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (bmj.Successful) {
            AndroidTool.showToast(this, "修改成功");
            finish();
        } else {
            AndroidTool.showToast(this, bmj.Error);
        }
    }

    @EditorAction
    void gar_password_confirm(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_GO) {
            btn_change();
        }
    }
}
