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
 * Created by zczczy on 2016/2/29.
 * 取消订阅
 */
@EActivity(R.layout.activity_subscribe_cancel)
public class SubscribeCancelActivity extends  BaseActivity {

    @ViewById
    TextView text_send;

    @ViewById
    EditText edt_mobile,edit_code,edt_username;

    @ViewById
    MyTitleBar myTitleBar;

    @ViewById
    Button btn_sure;


    @RestService
    MyDotNetRestClient newMyRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    @StringRes
    String timer,send_message;

    CountDownTimer countDownTimer;

    @AfterViews
    void afterView() {
        edit_code.setEnabled(false);
        newMyRestClient.setRestErrorHandler(myErrorHandler);
        getBind();
        edt_username.setEnabled(false);
        edt_mobile.setEnabled(false);
        getCountDownTimer();
        if(AndroidTool.getCodeTime(pre.timerCancel().get())<120000L){
            countDownTimer.start();
        }
    }

    void getCountDownTimer(){
        countDownTimer = new CountDownTimer(AndroidTool.getCodeTime(pre.timerCancel().get()), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                pre.timerCancel().put(System.currentTimeMillis() + millisUntilFinished);
                text_send.setPressed(true);
                text_send.setText(String.format(timer, millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                pre.timerCancel().put(0L);
                text_send.setPressed(false);
                text_send.setText(send_message);
            }
        };
    }


    @Background
    void getBind(){

        BaseModelJson<String> bmj= newMyRestClient.GetMobile(pre.username().get());
        setBind(bmj);
    }
    @UiThread
    void setBind(BaseModelJson<String> bmj){
        AndroidTool.dismissLoadDialog();
        if (bmj!=null) {
            if (bmj.Successful) {
                //获取手机号码
                edt_mobile.setText(bmj.Data);
                edt_username.setText(pre.username().get());

            }

        }
    }

    @Background
    void sendCode() {
        Map<String, String> map = new HashMap<>();
        map.put("UserName",edt_username.getText().toString());
        map.put("SendType", "3");
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
        edit_code.setEnabled(true);
        if (AndroidTool.checkIsNull(edt_username)) {
            AndroidTool.showToast(this, "用户名不能为空");
            return;
        }

        if (AndroidTool.checkIsNull(edt_mobile)) {
            AndroidTool.showToast(this, "手机号码不能为空");
            return;
        }

        if (pre.timerCancel().get() == 0L || AndroidTool.getCodeTime(pre.timerCancel().get()) >= 120000L) {
            getCountDownTimer();
            countDownTimer.start();
            edit_code.setEnabled(true);
            sendCode();
        }
    }
    @Click
    void btn_sure(){
        if (AndroidTool.checkIsNull(edt_username)) {
            AndroidTool.showToast(this, "用户名不能为空");
        } else if (AndroidTool.checkIsNull(edt_mobile)) {
            AndroidTool.showToast(this, "手机号码不能为空");
        }  else if (AndroidTool.checkIsNull(edit_code)) {
            AndroidTool.showToast(this, "验证码不能为空");
        }
        else {
            AndroidTool.showLoadDialog(this);
            cancel();

        }

    }

    @Override
    public void finish(){
        setResult(1001);
        super.finish();
    }


    //取消订阅
    @Background
    void cancel() {
        Map<String, String> map = new HashMap<>();
        map.put("UserName", edt_username.getText().toString());
        map.put("mobile", edt_mobile.getText().toString());
        map.put("SendCode",edit_code.getText().toString());
        AfterCancel(newMyRestClient.CancelSubscription(map));
    }

    @UiThread
    void AfterCancel(BaseModelJson<String> bmj) {
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
    public void setCode(String code){
        edit_code.setText(code);

    }
}
