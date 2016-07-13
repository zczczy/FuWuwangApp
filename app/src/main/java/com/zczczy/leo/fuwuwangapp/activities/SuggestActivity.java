package com.zczczy.leo.fuwuwangapp.activities;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.FwwUser;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyAlertDialog;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.util.StringUtils;

/**
 * Created by Leo on 2016/4/29.
 */
@EActivity(R.layout.activity_suggest)
public class SuggestActivity extends BaseActivity {

    @ViewById
    EditText edt_suggest_username, edt_suggest_pass, edt_confirm_pass, edt_id_card, edt_realname, edt_mobile_phone;

    MyAlertDialog dialog;

    @Bean
    MyErrorHandler myErrorHandler;

    @ViewById
    LinearLayout ll_confirm;

    @RestService
    MyDotNetRestClient myRestClient;

    String username, pass, confirm_pass;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    //注册按钮点击事件
    @Click
    void btn_register() {
         /*隐藏软键盘*/
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(SuggestActivity.this.getCurrentFocus().getWindowToken(), 0);
        }
        if (isNetworkAvailable(this)) {
            this.username = edt_suggest_username.getText().toString();
            this.pass = edt_suggest_pass.getText().toString();
            this.confirm_pass = edt_confirm_pass.getText().toString();
            if (AndroidTool.checkIsNull(edt_suggest_username)) {
                MyAlertDialog dialog = new MyAlertDialog(this, "用户名不能为空", null);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
            } else if (AndroidTool.checkIsNull(edt_suggest_pass)) {
                MyAlertDialog dialog = new MyAlertDialog(this, "密码不能为空", null);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
            } else if (ll_confirm.isShown() && StringUtils.isEmpty(confirm_pass)) {
                MyAlertDialog dialog = new MyAlertDialog(this, "确认密码不能为空", null);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
            } else if (ll_confirm.isShown() && !pass.equals(confirm_pass)) {
                MyAlertDialog dialog = new MyAlertDialog(this, "两次密码输入不一致", null);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
            } else if (AndroidTool.checkIsNull(edt_realname)) {
                MyAlertDialog dialog = new MyAlertDialog(this, "真实姓名不能为空", null);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
            } else if (AndroidTool.checkIsNull(edt_id_card)) {
                MyAlertDialog dialog = new MyAlertDialog(this, "身份证不能为空", null);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
            } else if (AndroidTool.checkIsNull(edt_mobile_phone)) {
                MyAlertDialog dialog = new MyAlertDialog(this, "手机号不能为空", null);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
            } else {
                AndroidTool.showLoadDialog(this);
                getHttp();
            }
        } else {
            Toast.makeText(this, no_net, Toast.LENGTH_SHORT).show();
        }
    }

    @Background
    void getHttp() {
        FwwUser fwwUser = new FwwUser(username, pass,
                ll_confirm.isShown() ? confirm_pass : pass,
                pre.token().get(),
                "1",
                edt_mobile_phone.getText().toString().trim(),
                edt_realname.getText().toString().trim(),
                edt_id_card.getText().toString().trim(),
                null);
        BaseModelJson<String> bmj = myRestClient.RegisterNew(fwwUser);
        showsuccess(bmj);
    }

    @UiThread
    void showsuccess(BaseModelJson<String> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (bmj.Successful) {
            dialog = new MyAlertDialog(this, "注册成功", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.close();
                    finish();
                }
            });
            dialog.setCancelable(false);
            dialog.show();
        } else {
            MyAlertDialog dialog = new MyAlertDialog(this, bmj.Error, null);
            dialog.show();
        }
    }

    //点击显示密码
    @CheckedChange
    void cb_isShow(boolean isChecked) {

        if (isChecked) {
            edt_suggest_pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            edt_confirm_pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            ll_confirm.setVisibility(View.GONE);
        } else {
            ll_confirm.setVisibility(View.VISIBLE);
            edt_suggest_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            edt_confirm_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

}
