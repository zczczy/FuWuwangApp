package com.zczczy.leo.fuwuwangapp.activities;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioButton;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.LoginInfo;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.util.StringUtils;

/**
 * Created by Leo on 2016/5/1.
 */
@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    @ViewById
    EditText username, psd;

    @Bean
    MyErrorHandler myErrorHandler;

    @ViewById
    RadioButton gal_id_normal;

    @RestService
    MyDotNetRestClient myRestClient;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }


    @Click
    void login_btn() {
        if (AndroidTool.checkIsNull(username)) {
            AndroidTool.showToast(this, "帐号不能为空");
        } else if (AndroidTool.checkIsNull(psd)) {
            AndroidTool.showToast(this, "密码不能为空");
        } else {
            AndroidTool.showLoadDialog(this);
            login();
        }
    }

    @EditorAction
    void psd(int actionId) {
        if (actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_DONE) {
            login_btn();
        }
    }


    @Click
    void sign_up_account() {
        RegisterActivity_.intent(this).start();
    }


    @Click
    void forget() {
//        NewResetPasswordActivity_.intent(this).start();
    }


    @Background
    void login() {
        BaseModelJson<LoginInfo> bmj = myRestClient.login(username.getText().toString().trim(),
                psd.getText().toString().trim(), gal_id_normal.isChecked() ? MyApplication.NORMAL : MyApplication.VIP, MyApplication.ANDROID);
        afterLogin(bmj);
    }

    @UiThread
    void afterLogin(BaseModelJson<LoginInfo> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (bmj.Successful) {
            pre.token().put(bmj.Data.Token);
            pre.shopToken().put(bmj.Data.ShopToken);
            pre.userType().put(bmj.Data.LoginType);
            pre.username().put(username.getText().toString());
            finish();
        } else {
            AndroidTool.showToast(this, bmj.Error);
        }
    }

    public void finish() {
        if (!(StringUtils.isEmpty(pre.token().get()) || StringUtils.isEmpty(pre.shopToken().get()))) {
            setResult(RESULT_OK);
        }
        super.finish();
    }
}
