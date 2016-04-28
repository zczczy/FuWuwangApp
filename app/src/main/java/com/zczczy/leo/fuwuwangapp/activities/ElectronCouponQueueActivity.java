package com.zczczy.leo.fuwuwangapp.activities;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.rest.MyRestClient;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyAlertDialog;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

/**
 * Created by Leo on 2016/4/28.
 */
@EActivity(R.layout.activity_electron_coupon_queue)
public class ElectronCouponQueueActivity extends BaseActivity {

    @ViewById
    EditText edt_business, edt_queue_num, edt_user_name;

    @ViewById
    RadioButton rb_fifty_queue, rb_twenty_five_queue, rb_member_queue, rb_business_queue;

    @ViewById
    RadioGroup r_duilie;

    @ViewById
    LinearLayout l_info, ll_user_name, coupon_focus;

    @ViewById
    Button btn_start;

    @ViewById
    ImageView img_line;

    @Extra
    String mianzhi;

    MyAlertDialog dialog;

    @Pref
    MyPrefs_ pre;

    @StringRes
    String txt_electron_coupon_queue_title;

    @Bean
    MyErrorHandler myErrorHandler;

    @RestService
    MyRestClient myRestClient;

    int flag=0;

    @AfterInject
    void afterInject(){
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    private String guize;
    private String duilie;
    private String companyLogin;
    private String paiduinum;

    @AfterViews
    void afterView() {
        edt_business.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    if (rb_business_queue.isChecked() && rb_business_queue.isChecked() && (!edt_business.getText().toString().isEmpty())) {
                        if(isNetworkAvailable(ElectronCouponQueueActivity.this)){
                            AndroidTool.showLoadDialog(ElectronCouponQueueActivity.this);
                            getBusinessName();
                        }
                        else{
                            Toast.makeText(ElectronCouponQueueActivity.this,no_net,Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        img_line.setVisibility(View.GONE);
                        ll_user_name.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @AfterTextChange
    void edt_business(){
        flag=0;
    }

    //查询商家名称
    @Background
    void getBusinessName() {
        BaseModelJson<String> bmj = myRestClient.GetCompanyNameByUlogin(edt_business.getText().toString());
        showBusinessName(bmj);
    }

    //商家名称查询结果显示
    @UiThread
    void showBusinessName(BaseModelJson<String> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj!=null) {
            if (bmj.Successful) {
                flag=1;
                img_line.setVisibility(View.VISIBLE);
                edt_user_name.setText(bmj.Data);
                ll_user_name.setVisibility(View.VISIBLE);
            } else {
                MyAlertDialog dialog = new MyAlertDialog(this, bmj.Error, null);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                edt_user_name.setText(null);
                img_line.setVisibility(View.GONE);
                ll_user_name.setVisibility(View.GONE);
            }
        }
    }

    //排队
    @Background
    void getHttp() {
        String token = pre.token().get();
        myRestClient.setHeader("Token", token);
        BaseModelJson<String> bmj = myRestClient.PlPd(mianzhi, guize, duilie, companyLogin, paiduinum);
        showsuccess(bmj);
    }

    @UiThread
    void showsuccess(BaseModelJson<String> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj!=null) {
            if (bmj.Successful) {
                if ("0".equals(bmj.Data)) {
                    dialog = new MyAlertDialog(this, "操作成功", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.close();
                            finish();
                        }
                    });
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                } else {
                    WheelActivity_.intent(this).times(Integer.valueOf(bmj.Data)).start();
                    finish();
                }
            } else {
                MyAlertDialog dialog = new MyAlertDialog(this, bmj.Error,null);
                dialog.setCancelable(false);
                dialog.show();
            }
        }
    }

    //25进1
    @CheckedChange
    void rb_twenty_five_queue(boolean isChecked) {
        if (isChecked) {
            r_duilie.setVisibility(View.GONE);
            l_info.setVisibility(View.GONE);
            coupon_focus.requestFocus();
            closeInputMethod(ElectronCouponQueueActivity.this);
        }
    }

    //50进1
    @CheckedChange
    void rb_fifty_queue(boolean isChecked) {
        if (isChecked) {
            if (rb_business_queue.isChecked()) {
                r_duilie.setVisibility(View.VISIBLE);
                l_info.setVisibility(View.VISIBLE);
            } else {
                r_duilie.setVisibility(View.VISIBLE);
            }
            coupon_focus.requestFocus();
            closeInputMethod(ElectronCouponQueueActivity.this);
        }
    }

    //开启商家队列
    @CheckedChange
    void rb_business_queue(boolean isChecked) {
        if (isChecked) {
            l_info.setVisibility(View.VISIBLE);
            coupon_focus.requestFocus();
            closeInputMethod(ElectronCouponQueueActivity.this);
        }
    }

    //开启会员队列
    @CheckedChange
    void rb_member_queue(boolean isChecked) {
        if (isChecked) {
            l_info.setVisibility(View.GONE);
            coupon_focus.requestFocus();
            closeInputMethod(ElectronCouponQueueActivity.this);
        }
    }

    /**
     * 点击排队
     */
    @Click
    void btn_start() {
        if(isNetworkAvailable(this)){
            if (rb_fifty_queue.isChecked()) {
                if (rb_business_queue.isChecked()) {
                    this.guize = "1";
                    this.duilie = "2";
                    this.companyLogin = edt_business.getText().toString();
                    this.paiduinum = edt_queue_num.getText().toString();
                    if (companyLogin == "" || companyLogin == null || companyLogin.isEmpty()) {
                        MyAlertDialog dialog = new MyAlertDialog(this, "商家名称不能为空", null);
                        dialog.show();
                        dialog.setCanceledOnTouchOutside(false);
                        return;
                    }
                    if(flag==0){
                        closeInputMethod(ElectronCouponQueueActivity.this);
                        AndroidTool.showCancelabledialog(this);
                        getBusinessName();
                        return;
                    }else if(paiduinum == "" || paiduinum == null || paiduinum.isEmpty()){
                        MyAlertDialog dialog = new MyAlertDialog(this, "排队数量不能为空", null);
                        dialog.show();
                        dialog.setCanceledOnTouchOutside(false);
                        return;
                    }else{
                        closeInputMethod(ElectronCouponQueueActivity.this);
                        AndroidTool.showCancelabledialog(this);
                        getHttp();
                        return;
                    }
                } else {
                    this.guize = "1";
                    this.duilie = "1";
                    this.paiduinum = edt_queue_num.getText().toString();
                }
            } else {
                this.guize = "0";
                this.paiduinum = edt_queue_num.getText().toString();
            }
            if (paiduinum == "" || paiduinum == null || paiduinum.isEmpty()) {
                MyAlertDialog dialog = new MyAlertDialog(this, "排队数量不能为空", null);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                return;
            } else {
                closeInputMethod(ElectronCouponQueueActivity.this);
                AndroidTool.showCancelabledialog(this);
                getHttp();
            }
        }
        else{
            Toast.makeText(this,no_net,Toast.LENGTH_SHORT).show();
        }
    }

}
