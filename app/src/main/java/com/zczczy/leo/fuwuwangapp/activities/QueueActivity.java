package com.zczczy.leo.fuwuwangapp.activities;

import android.widget.Toast;

import com.zczczy.leo.fuwuwangapp.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

/**
 * Created by Leo on 2016/4/28.
 */
@EActivity(R.layout.activity_duixianjuan_queue)
public class QueueActivity  extends BaseActivity{


    @AfterViews
    void afterView(){
    }

    //电子券按钮点击事件
    @Click
    void btn_dianziquan(){
        if(isNetworkAvailable(this)){
            ElectronQueueWxActivity_.intent(this).start();
        }
        else{
            Toast.makeText(this,no_net,Toast.LENGTH_SHORT).show();
        }
    }

    //普通纸券按钮点击事件
    @Click
    void btn_ptzq(){
        if(isNetworkAvailable(this)){
            PaperCouponQueueWxActivity_.intent(this).start();
        }
        else{
            Toast.makeText(this,no_net,Toast.LENGTH_SHORT).show();
        }
    }

    //二维码纸券按钮点击事件
    @Click
    void btn_ewmzq(){
        if(isNetworkAvailable(this)){
            QrCodeActivity_.intent(this).start();
        }
        else{
            Toast.makeText(this,no_net,Toast.LENGTH_SHORT).show();
        }
    }

    //已排队查看按钮点击事件
    @Click
    void btn_ypdck(){
//        Toast.makeText(this,"开发中",Toast.LENGTH_SHORT).show();
        QueueSeeActivity_.intent(this).start();
    }

}
