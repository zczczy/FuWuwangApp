package com.zczczy.leo.fuwuwangapp.activities;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.rest.MyRestClient;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyAlertDialog;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyConfirmAlertDialog;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Leo on 2016/4/28.
 */
@EActivity(R.layout.activity_coupon_transfer)
public class CouponTransferActivity extends BaseActivity {

    @ViewById
    EditText edt_money, edt_receiver, edt_real_name, edt_pay_pass;

    @ViewById
    Button btn_transfer;

    MyAlertDialog dialog1;

    @ViewById
    TextView tip_txt, txtmoney;

    @StringRes
    String coupon_transfer_tip;

    @Extra
    String mz, dlmc;

    MyConfirmAlertDialog dialog;

    @Bean
    MyErrorHandler myErrorHandler;

    @RestService
    MyDotNetRestClient myDotNetRestClient;


    @RestService
    MyRestClient myRestClient;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
        myDotNetRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        tip_txt.setText(coupon_transfer_tip);
        txtmoney.setText(dlmc);
    }

    @AfterTextChange(R.id.edt_receiver)
    void edt_receiver() {
        String str = edt_receiver.getText().toString();
        //缺少调用根据会员用户名显示真实姓名的方法
        if (!StringUtils.isEmpty(str)) {
            if (isNetworkAvailable(this)) {
                getmember();
            } else {
                edt_real_name.setText(no_net);
            }
        }
    }

    //从服务器接口获取会员真实姓名
    @Background
    void getmember() {
        BaseModelJson<String> bmj = myDotNetRestClient.GetUserNameByUlogin(edt_receiver.getText().toString());
        setmembername(bmj);
    }

    //绑定会员真实姓名的UI线程
    @UiThread
    void setmembername(BaseModelJson<String> bmj) {
        edt_real_name.setText("");
        if (bmj != null && bmj.Successful) {
            edt_real_name.setText(bmj.Data);
        } else {
            edt_real_name.setText("没有此会员！");
        }
    }

    //转账按钮点击事件
    @Click
    void btn_transfer() {
        if (isNetworkAvailable(this)) {
            String money = edt_money.getText().toString();
            String str = edt_receiver.getText().toString();
            if (money == null || money == "" || money.isEmpty()) {
                MyAlertDialog dialog = new MyAlertDialog(this, "数量不能为空！", null);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                return;
            }
            //正整数正则表达式
            Pattern p = Pattern.compile("^[1-9]\\d*$");
            Matcher m = p.matcher(edt_money.getText().toString());
            if (!m.matches()) {
                MyAlertDialog dialog = new MyAlertDialog(this, "数量必须为整数", null);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                return;
            }
            if (str == null || str == "" || str.isEmpty()) {
                MyAlertDialog dialog = new MyAlertDialog(this, "接收人用户名不能为空！", null);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                return;
            }
            if (edt_real_name.getText().toString().equals("没有此会员！")) {
                MyAlertDialog dialog = new MyAlertDialog(this, "没有此会员！", null);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                return;
            }
            if ("".equals(edt_pay_pass.getText().toString().trim())) {
                MyAlertDialog dialog = new MyAlertDialog(this, "请输入支付密码！", null);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                return;
            }
            dialog = new MyConfirmAlertDialog(this, "操作确认", clickListener);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            closeInputMethod(this);
        } else {
            Toast.makeText(this, no_net, Toast.LENGTH_SHORT).show();
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AndroidTool.showCancelabledialog(CouponTransferActivity.this);
            savetxsq();
            dialog.close();
        }
    };

    @Background
    void savetxsq() {
        String token = pre.token().get();
        myRestClient.setHeader("Token", token);
        BaseModelJson<String> bmj = myRestClient.PlZrRecord(mz, edt_money.getText().toString(), edt_receiver.getText().toString(), edt_pay_pass.getText().toString());
        getmesagess(bmj);
    }

    @UiThread
    void getmesagess(BaseModelJson<String> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
            MyAlertDialog dialog2 = new MyAlertDialog(this, "网络连接失败", null);
            dialog2.show();
            dialog2.setCanceledOnTouchOutside(false);
        } else if (bmj.Successful) {
            dialog1 = new MyAlertDialog(this, "转让成功！", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog1.close();
                    finish();
                }
            });
            dialog1.show();
            dialog1.setCancelable(false);
        } else {
            MyAlertDialog dialog2 = new MyAlertDialog(this, bmj.Error, null);
            dialog2.show();
            dialog2.setCanceledOnTouchOutside(false);
        }
    }

}
