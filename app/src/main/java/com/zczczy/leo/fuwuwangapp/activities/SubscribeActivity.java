package com.zczczy.leo.fuwuwangapp.activities;

import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.rest.MyRestClient;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zczczy on 2016/2/25.
 * 订阅、
 */
@EActivity(R.layout.activity_subscribe)
public class SubscribeActivity extends BaseActivity {

    @ViewById
    TextView text_send;

    @ViewById
    EditText edt_mobile, edit_code, edt_pwd, edt_username, edit_mobile;

    @ViewById
    Button btn_sure;

    @ViewById
    MyTitleBar myTitleBar;

    @RestService
    MyRestClient myRestClient;

    @RestService
    MyDotNetRestClient newMyRestClient;

    @Pref
    MyPrefs_ pre;

    @Bean
    MyErrorHandler myErrorHandler;

    @StringRes
    String timer,send_message;

    CountDownTimer countDownTimer;

    @AfterViews
    void afterView() {
        getCountDownTimer();
        if (AndroidTool.getCodeTime(pre.timerCode().get()) < 120000L) {
            countDownTimer.start();
            edit_code.setEnabled(true);
        }else{
            edit_code.setEnabled(false);
        }

        myRestClient.setRestErrorHandler(myErrorHandler);
        newMyRestClient.setRestErrorHandler(myErrorHandler);
        getBind();
        edt_username.setEnabled(false);
        edt_mobile.setEnabled(false);
    }

    void getCountDownTimer(){
        countDownTimer = new CountDownTimer(AndroidTool.getCodeTime(pre.timerCode().get()), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                pre.timerCode().put(System.currentTimeMillis() + millisUntilFinished);
                text_send.setPressed(true);
                text_send.setText(String.format(timer, millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                pre.timerCode().put(0L);
                text_send.setPressed(false);
                text_send.setText(send_message);
            }
        };
    }

    //获取手机号码
    @Background
    void getBind() {
        BaseModelJson<String> bmj = newMyRestClient.GetMobile(pre.username().get());
        setBind(bmj);
    }

    @UiThread
    void setBind(BaseModelJson<String> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj != null) {
            if (bmj.Successful) {
                //获取手机号码
                edt_mobile.setText(bmj.Data);
                edt_username.setText(pre.username().get());
            }
        }
    }

    //获取验证码
    @Background
    void sendCode() {
        Map<String, String> map = new HashMap<>();
        map.put("UserName", edt_username.getText().toString());
        map.put("SendType", "2");
        map.put("mobile", edt_mobile.getText().toString());
        afterSendCode(newMyRestClient.SendVerificationCode(map));
    }

    @UiThread
    void afterSendCode(BaseModelJson<String> bmj) {
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (bmj.Successful) {
            AndroidTool.showToast(this, bmj.Error);
        } else {
            AndroidTool.showToast(this, bmj.Error);
        }
    }

    @Click
    void text_send() {

        if (AndroidTool.checkIsNull(edt_username)) {
            AndroidTool.showToast(this, "用户名不能为空");
            return;
        }

        if (AndroidTool.checkIsNull(edt_mobile)) {
            AndroidTool.showToast(this, "手机号码不能为空");
            return;
        }
        if (pre.timerCode().get() == 0L || AndroidTool.getCodeTime(pre.timerCode().get()) >= 120000L) {
            getCountDownTimer();
            countDownTimer.start();
            edit_code.setEnabled(true);
            sendCode();
        }
    }



    @Click
    void btn_sure() {
        if (AndroidTool.checkIsNull(edt_username)) {
            AndroidTool.showToast(this, "用户名不能为空");
        } else if (AndroidTool.checkIsNull(edt_mobile)) {
            AndroidTool.showToast(this, "手机号码不能为空");
        } else if (AndroidTool.checkIsNull(edt_pwd)) {
            AndroidTool.showToast(this, "支付密码不能为空");
        } else if (AndroidTool.checkIsNull(edit_code)) {
            AndroidTool.showToast(this, "验证码不能为空");
        } else {
            AndroidTool.showLoadDialog(this);
            subscribe();
        }

    }

    //订阅
    @Background
    void subscribe() {
        Map<String, String> map = new HashMap<>();
        map.put("UserName", edt_username.getText().toString());
        map.put("TwoPW", edt_pwd.getText().toString());
        map.put("mobile", edt_mobile.getText().toString());
        map.put("SendCode", edit_code.getText().toString());
        Aftersubscribe(newMyRestClient.SubscriptionService(map));
    }

    @UiThread
    void Aftersubscribe(BaseModelJson<String> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (bmj.Successful) {
            AndroidTool.showToast(this, bmj.Error);
            finish();
        } else {
            AndroidTool.showToast(this, bmj.Error);
        }
    }

    @Override
    public void finish() {
        setResult(1001);
        super.finish();
    }

    public void setCode(String code){
        edit_code.setText(code);

    }

}
