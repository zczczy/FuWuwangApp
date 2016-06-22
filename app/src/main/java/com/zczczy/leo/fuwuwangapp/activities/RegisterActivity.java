package com.zczczy.leo.fuwuwangapp.activities;

import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.tools.Constants;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zczczy on 2016/5/5.
 */
@EActivity(R.layout.activity_register_layout)
public class RegisterActivity extends BaseActivity {

    @ViewById
    EditText edt_suggest_username, edt_suggest_pass, edt_confirm_pass, service_man, text_email;

    @ViewById
    RadioGroup gar_id_group;

    @ViewById
    RadioButton gar_id_normal, gar_id_vip;

    @ViewById
    LinearLayout ll_email, gar_service_man_layout, ll_confirm;

    @RestService
    MyDotNetRestClient myDotNetRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    Map<String, String> map;

    @AfterInject
    void afterInject() {
        myDotNetRestClient.setRestErrorHandler(myErrorHandler);
        map = new HashMap<>();
    }

    @Click
    void btn_register() {
        if (isNetworkAvailable(this)) {
            if (AndroidTool.checkIsNull(edt_suggest_username)) {
                AndroidTool.showToast(this, "用户名不能为空");
            } else if (AndroidTool.checkIsNull(edt_suggest_pass)) {
                AndroidTool.showToast(this, "密码不能为空");
            } else if (ll_confirm.isShown() && AndroidTool.checkIsNull(edt_confirm_pass)) {
                AndroidTool.showToast(this, "确认密码不能为空");
            } else if (ll_confirm.isShown() && !edt_suggest_pass.getText().toString().equals(edt_confirm_pass.getText().toString())) {
                AndroidTool.showToast(this, "两次密码输入不一致");
            } else if (gar_id_normal.isChecked() && AndroidTool.checkIsNull(text_email)) {
                AndroidTool.showToast(this, "邮箱不能为空");
            } else if (gar_id_normal.isChecked() && AndroidTool.checkEmail(text_email)) {
                AndroidTool.showToast(this, "邮箱格式不正确");
            } else if (gar_id_vip.isChecked() && AndroidTool.checkIsNull(service_man)) {
                AndroidTool.showToast(this, "服务专员不能为空");
            } else {
                map.put("userLogin", edt_suggest_username.getText().toString().trim());
                map.put("passWord", edt_suggest_pass.getText().toString().trim());
                map.put("passWordConfirm", ll_confirm.isShown() ? edt_confirm_pass.getText().toString().trim() : edt_suggest_pass.getText().toString().trim());
                map.put("zy", service_man.getText().toString().trim());
                map.put("MemberEmail", text_email.getText().toString().trim());
                map.put("MemberRealName", null);
                map.put("UserType", gar_id_normal.isChecked() ? Constants.NORMAL : Constants.VIP);
                AndroidTool.showLoadDialog(this);
                register();
            }
        }
    }

    @Background
    void register() {
        afterRegister(myDotNetRestClient.register(map));
    }

    @UiThread
    void afterRegister(BaseModel bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (bmj.Successful) {
            AndroidTool.showToast(this, "注册成功");
            finish();
        } else {
            AndroidTool.showToast(this, bmj.Error);
        }
    }

    @AfterViews
    void afterView() {
        gar_id_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.gar_id_normal) {
                    ll_email.setVisibility(View.VISIBLE);
                    gar_service_man_layout.setVisibility(View.GONE);
                } else {
                    ll_email.setVisibility(View.GONE);
                    gar_service_man_layout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //点击显示密码
    @CheckedChange
    void cb_isShow(boolean isChecked) {

        if (isChecked) {
            ll_confirm.setVisibility(View.GONE);
            edt_suggest_pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            edt_confirm_pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            ll_confirm.setVisibility(View.VISIBLE);
            edt_suggest_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            edt_confirm_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }


}
