package com.zczczy.leo.fuwuwangapp.activities;

import android.widget.RadioButton;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.Volume;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

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

/**
 * Created by Leo on 2016/4/28.
 */
@EActivity(R.layout.activity_electron_queue_wx)
public class ElectronQueueWxActivity extends BaseActivity {

    @ViewById
    RadioButton rb_one_hundred, rb_two_hundred, rb_four_hundred, rb_five_hundred;


    @ViewById
    TextView txt_one_hundred, txt_two_hundred, txt_four_hundred, txt_five_hundred;

    @Bean
    MyErrorHandler myErrorHandler;

    @RestService
    MyDotNetRestClient myRestClient;

    @AfterInject
    void afterInject(){
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView(){
        AndroidTool.showLoadDialog(this);
        getHttp();
    }

    //查询兑现券数量
    @Background
    void getHttp(){
        String token  = pre.token().get();
        myRestClient.setHeader("Token",token);
        BaseModelJson<Volume> bmj = myRestClient.GetQueueCount();
        show(bmj);
    }

    @UiThread
    void show( BaseModelJson<Volume> bmj){
        AndroidTool.dismissLoadDialog();
        if (bmj!=null) {
            if (bmj.Successful) {
                txt_one_hundred.setText(bmj.Data.getMz1());
                txt_two_hundred.setText(bmj.Data.getMz2());
                txt_four_hundred.setText(bmj.Data.getMz4());
                txt_five_hundred.setText(bmj.Data.getMz5());
            }
        }
    }


    //排队按钮点击事件
    @Click
    void btn_queue(){
        if(rb_one_hundred.isChecked()){
            ElectronCouponQueueActivity_.intent(this).mianzhi("7").start();
        }
        if(rb_two_hundred.isChecked()){
            ElectronCouponQueueActivity_.intent(this).mianzhi("8").start();
        }
        if(rb_four_hundred.isChecked()){
            ElectronCouponQueueActivity_.intent(this).mianzhi("10").start();
        }
        if(rb_five_hundred.isChecked()){
            ElectronCouponQueueActivity_.intent(this).mianzhi("9").start();
        }
    }

    //转让按钮点击事件
    @Click
    void btn_transfer(){
            if(rb_one_hundred.isChecked()){
                CouponTransferActivity_.intent(this).mz("7").dlmc("100").start();
            }
            if(rb_two_hundred.isChecked()){
                CouponTransferActivity_.intent(this).mz("8").dlmc("200").start();
            }
            if(rb_four_hundred.isChecked()){
                CouponTransferActivity_.intent(this).mz("10").dlmc("400").start();
            }
            if(rb_five_hundred.isChecked()){
            CouponTransferActivity_.intent(this).mz("9").dlmc("500").start();
        }
    }

    @CheckedChange
    void rb_one_hundred(boolean checked) {
        if (checked) {
            rb_two_hundred.setChecked(!checked);
            rb_four_hundred.setChecked(!checked);
            rb_five_hundred.setChecked(!checked);
        }

    }

    @CheckedChange
    void rb_two_hundred(boolean checked) {
        if (checked) {
            rb_one_hundred.setChecked(!checked);
            rb_four_hundred.setChecked(!checked);
            rb_five_hundred.setChecked(!checked);
        }

    }

    @CheckedChange
    void rb_four_hundred(boolean checked) {
        if (checked) {
            rb_two_hundred.setChecked(!checked);
            rb_one_hundred.setChecked(!checked);
            rb_five_hundred.setChecked(!checked);
        }

    }

    @CheckedChange
    void rb_five_hundred(boolean checked) {
        if (checked) {
            rb_two_hundred.setChecked(!checked);
            rb_four_hundred.setChecked(!checked);
            rb_one_hundred.setChecked(!checked);
        }
    }

}
