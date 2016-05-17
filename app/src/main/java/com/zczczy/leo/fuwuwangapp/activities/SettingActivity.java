package com.zczczy.leo.fuwuwangapp.activities;

import android.widget.RelativeLayout;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyTitleBar;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.springframework.util.StringUtils;

/**
 * Created by zczczy on 2016/5/6.
 * 设置
 */
@EActivity(R.layout.activity_setting)
public class SettingActivity extends BaseActivity {


    @ViewById
    RelativeLayout rl_change_pass;

    @Click
    void rl_change_pass() {
        if (checkUserIsLogin()) {
            ChangePasswordActivity_.intent(this).startForResult(1000);
        } else {
            AndroidTool.showToast(this, "请先登录");
        }
    }

//    @Click
//    void rl_change_email() {
//        if (StringUtils.isEmpty(pre.token().get())) {
//            AndroidTool.showToast(this, "请先登录");
//        } else {
//            NewChangeEmailActivity_.intent(this).start();
//        }
//    }
//
//    @Click
//    void rl_forget_pass() {
//
//        NewResetPasswordActivity_.intent(this).start();
//
//    }

    @OnActivityResult(value = 1000)
    void onResult(int resultCode) {
        if (resultCode == 1001) {
            setResult(1001);
            finish();
        }
    }
}
