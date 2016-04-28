package com.zczczy.leo.fuwuwangapp.activities;

import android.widget.TextView;
import android.widget.Toast;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.UserBaseInfo;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.rest.MyRestClient;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterInject;
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

/**
 * Created by Leo on 2016/4/28.
 */
@EActivity(R.layout.activity_vip)
public class VipActivity extends BaseActivity {

    @ViewById
    TextView txt_username, txt_realname;

    @ViewById
    MyTitleBar myTitleBar;

    @StringRes
    String   my_lottery_record;

    @RestService
    MyRestClient myRestClient;

    @RestService
    MyDotNetRestClient newMyRestClient;

    @Pref
    MyPrefs_ pre;

    @Bean
    MyErrorHandler myErrorHandler;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
        newMyRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
//        fileName = AndroidTool.BaseFilePath() + System.currentTimeMillis() + ".jpg";
        AndroidTool.showLoadDialog(this);
        getBind();
    }


    @Background
    void getBind() {
        String token = pre.token().get();
        myRestClient.setHeader("Token", token);
        BaseModelJson<UserBaseInfo> bmj = myRestClient.GetZcUserById();
        setBind(bmj);
    }

    @UiThread
    void setBind(BaseModelJson<UserBaseInfo> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj != null) {
            if (bmj.Successful) {
                pre.username().put(bmj.Data.getM_Uid());
                pre.realname().put(bmj.Data.getM_realrName());
                txt_username.setText(bmj.Data.getM_Uid());
                txt_realname.setText(bmj.Data.getM_realrName());
            } else {
                AndroidTool.showToast(this, bmj.Error);
            }
        }
    }

    //财富
    @Click
    void ll_wealth() {
        if (isNetworkAvailable(this)) {
            WealthActivity_.intent(this).start();
        } else {

        }
    }

    //资料完善
    @Click
    void ll_perfect_info() {
        if (isNetworkAvailable(this)) {
            PerfectInfoActivity_.intent(this).start();
        } else {
            AndroidTool.showToast(this, no_net);
        }
    }

    //兑现卷排队
    @Click
    void ll_queue() {
        if (isNetworkAvailable(this)) {
            QueueActivity_.intent(this).start();
        } else {
            AndroidTool.showToast(this, no_net);
        }
    }

    //电子币抽奖记录
    @Click
    void ll_quan_record(){
        LotteryInfoRecordActivity_.intent(this).title(my_lottery_record).method(0).start();
    }

}
