package com.zczczy.leo.fuwuwangapp.activities;

import android.widget.TextView;
import android.widget.Toast;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.QueueCount;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

/**
 * Created by Leo on 2016/4/29.
 */
@EActivity(R.layout.activity_coupon_manager)
public class CouponManageActivity extends BaseActivity {

    @ViewById
    TextView one_twenty_five,two_twenty_five,four_twenty_five,five_twenty_five,one_fifty,two_fifty,four_fifty,five_fifty;

    @Bean
    MyErrorHandler myErrorHandler;

    @RestService
    MyDotNetRestClient myRestClient;

    @AfterInject
    void afterInject(){
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        AndroidTool.showLoadDialog(this);
        getHttp();
    }

    @Background
    void getHttp(){
        String token = pre.token().get();
        myRestClient.setHeader("Token",token);
        BaseModelJson<QueueCount> bmj = myRestClient.QueueM();
        show(bmj);
    }

    @UiThread
    void show(BaseModelJson<QueueCount> bmj){
        AndroidTool.dismissLoadDialog();
        if (bmj!=null) {
            if (bmj.Successful) {
                one_twenty_five.setText(bmj.Data.getM1_25());
                one_fifty.setText(bmj.Data.getM1_50());
                two_twenty_five.setText(bmj.Data.getM2_25());
                two_fifty.setText(bmj.Data.getM2_50());
                four_twenty_five.setText(bmj.Data.getM4_25());
                four_fifty.setText(bmj.Data.getM4_50());
                five_twenty_five.setText(bmj.Data.getM5_25());
                five_fifty.setText(bmj.Data.getM5_50());
            }
        }
    }

    //面值100的50进1队列点击事件
    @Click
    void ll_100_50(){
        if(isNetworkAvailable(this)){
            CouponManageInfoActivity_.intent(this).mianzhi("100").duilie("50进1队列").guize("2").start();
        }
        else{
            Toast.makeText(this, no_net, Toast.LENGTH_SHORT).show();
        }
    }

    //面值200的50进1队列点击事件
    @Click
    void ll_200_50(){
        if(isNetworkAvailable(this)){
            CouponManageInfoActivity_.intent(this).mianzhi("200").duilie("50进1队列").guize("4").start();
        }
        else{
            Toast.makeText(this,no_net,Toast.LENGTH_SHORT).show();
        }
    }

    //面值400的50进1队列点击事件
    @Click
    void ll_400_50(){
        if(isNetworkAvailable(this)){
            CouponManageInfoActivity_.intent(this).mianzhi("400").duilie("50进1队列").guize("8").start();
        }
        else{
            Toast.makeText(this,no_net,Toast.LENGTH_SHORT).show();
        }
    }

    //面值500的50进1队列点击事件
    @Click
    void ll_500_50(){
        if(isNetworkAvailable(this)){
            CouponManageInfoActivity_.intent(this).mianzhi("500").duilie("50进1队列").guize("6").start();
        }
        else{
            Toast.makeText(this,no_net,Toast.LENGTH_SHORT).show();
        }
    }
}
