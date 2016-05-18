package com.zczczy.leo.fuwuwangapp.activities;

import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.rest.MyRestClient;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyAlertDialog;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyEidtViewDialog;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Leo on 2016/4/28.
 */
@EActivity(R.layout.activity_withdraw)
public class WithDrawActivity extends BaseActivity {

    @ViewById
    EditText edt_withdraw_money, edt_common, edit_code, edit_mobile;

    @ViewById
    TextView text_send;

    @ViewById
    LinearLayout ll_mobile, ll_code;

    @ViewById
    Button btn_withdraw;

    @StringRes
    String txt_withdraw_title;

    MyAlertDialog dialog;

    @Bean
    MyErrorHandler myErrorHandler;

    @RestService
    MyRestClient myRestClient;

    @RestService
    MyDotNetRestClient newMyRestClient;

    @StringRes
    String timer, txt_ejpass, send_message;

    CountDownTimer countDownTimer;

    boolean isCode = false;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
        newMyRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        edit_code.setEnabled(false);
        GetSafeMessage();
        getCountDownTimer();
        if (AndroidTool.getCodeTime(pre.timerWithDraw().get()) < 120000L) {
            countDownTimer.start();
        }
    }

    void getCountDownTimer() {
        countDownTimer = new CountDownTimer(AndroidTool.getCodeTime(pre.timerWithDraw().get()), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                pre.timerWithDraw().put(System.currentTimeMillis() + millisUntilFinished);
                text_send.setPressed(true);
                text_send.setText(String.format(timer, millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                pre.timerWithDraw().put(0L);
                text_send.setPressed(false);
                text_send.setText(send_message);
            }
        };
    }

    //验证订阅
    @Background
    void GetSafeMessage() {
        BaseModelJson<String> bmj = newMyRestClient.SubscriptionExist(pre.username().get());
        AfterGetSafe(bmj);

    }

    @UiThread
    void AfterGetSafe(BaseModelJson<String> bmj) {
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (bmj.Successful) {
            getBind();
            isCode = true;
        } else {
            //   AndroidTool.showToast(this, bmj.Error);
            ll_code.setVisibility(View.GONE);
            ll_mobile.setVisibility(View.GONE);
            isCode = false;
        }

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
                edit_mobile.setText(bmj.Data);
                edit_mobile.setEnabled(false);
            }
        }
    }

    //获取验证码
    @Background
    void sendCode() {
        Map<String, String> map = new HashMap<>();
        map.put("UserName", pre.username().get());
        map.put("SendType", "0");
        map.put("mobile", edit_mobile.getText().toString());
        afterSendCode(newMyRestClient.SendVerificationCode(map));
    }


    @UiThread
    void afterSendCode(BaseModelJson<String> bmj) {
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (bmj.Successful) {
            //  AndroidTool.showToast(this, bmj.Data);
        } else {
            AndroidTool.showToast(this, bmj.Error);
        }
    }

    //验证码按钮
    @Click
    void text_send() {
        edit_code.setEnabled(true);
        if (AndroidTool.checkIsNull(edt_withdraw_money)) {
            AndroidTool.showToast(this, "提现金额不能为空");
            return;
        }
        if (AndroidTool.checkIsNull(edt_common)) {
            AndroidTool.showToast(this, "请求说明不能为空");
            return;
        }
        if (pre.timerWithDraw().get() == 0L || AndroidTool.getCodeTime(pre.timerWithDraw().get()) >= 120000L) {
            getCountDownTimer();
            countDownTimer.start();
            edit_code.setEnabled(true);
            sendCode();
        }
    }

    //验证验证码
    @Background
    void GetResult() {
        BaseModelJson<String> bmj = newMyRestClient.VerifyExite(pre.username().get(), edit_code.getText().toString(), "0");
        AfterGetResult(bmj);

    }

    @UiThread
    void AfterGetResult(BaseModelJson<String> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (bmj.Successful) {

            dialogxia = new MyEidtViewDialog(WithDrawActivity.this, txt_ejpass, listener);
            dialogxia.show();
            dialogxia.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            dialogxia.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        } else {
            AndroidTool.showToast(this, bmj.Error);
        }

    }


    //提现按钮点击事件
    @Click
    void btn_withdraw() {
        if (isNetworkAvailable(this)) {
            String money = edt_withdraw_money.getText().toString();
            String str = edt_common.getText().toString();
            String code = edit_code.getText().toString();
            if (StringUtils.isEmpty(money)) {
                MyAlertDialog dialog = new MyAlertDialog(this, "提现金额不能为空！", null);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                return;
            }
            if (Float.parseFloat(money) < 100) {
                MyAlertDialog dialog = new MyAlertDialog(this, "提现金额不能小于100元！", null);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                return;
            }
            if (StringUtils.isEmpty(str)) {
                MyAlertDialog dialog = new MyAlertDialog(this, "提现说明不能为空！", null);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                return;
            }
            if (!isCode) {
                dialogxia = new MyEidtViewDialog(WithDrawActivity.this, "支付密码:", listener);
                dialogxia.show();
                dialogxia.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                dialogxia.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            } else {
                if (StringUtils.isEmpty(code)) {
                    MyAlertDialog dialog = new MyAlertDialog(this, "验证码不能为空！", null);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    return;
                }
                GetResult();
            }

        } else {
            Toast.makeText(this, no_net, Toast.LENGTH_SHORT).show();
        }
    }


    MyEidtViewDialog dialogxia;
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String str = dialogxia.getEditTextValue();
            if (str != null && str != "" && !str.isEmpty()) {
                AndroidTool.showCancelabledialog(WithDrawActivity.this);
                dialogxia.dismiss();
                savetxsq();
            } else {
                MyAlertDialog dialog = new MyAlertDialog(WithDrawActivity.this, "请输入支付密码！", null);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
            }
        }
    };

    @Background
    void savetxsq() {
        String money = edt_withdraw_money.getText().toString();
        BigDecimal bd = new BigDecimal(money);
        //设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        String token = pre.token().get();
        myRestClient.setHeader("Token", token);
        BaseModelJson<String> bmj = myRestClient.WithdrawalsApply(bd, edt_common.getText().toString(), dialogxia.getEditTextValue());
        getmesagess(bmj);
    }

    @UiThread
    void getmesagess(BaseModelJson<String> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj != null) {
            if (bmj.Successful) {
                dialog = new MyAlertDialog(this, "提现申请成功！", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.close();
                        setResult(RESULT_OK);
                        finish();
                    }
                });
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
            } else {
                MyAlertDialog dialog = new MyAlertDialog(this, bmj.Error, null);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
            }
        }
    }

    public void setCode(String code) {
        edit_code.setText(code);

    }

}
