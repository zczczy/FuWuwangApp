package com.zczczy.leo.fuwuwangapp.activities;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FromHtml;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

/**
 * Created by zczczy on 2016/2/25.
 * 安全信使
 */
@EActivity(R.layout.activity_safe)
public class SafeMessengerActivity extends BaseActivity {

    @ViewById
    Button btn_cancel, btn_subscribe;


    @RestService
    MyDotNetRestClient newMyRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    @StringRes
    String txt_subscribe, txt_continue_subscribe;

    @ViewById
    @FromHtml(R.string.subscribe_notice)
    TextView txt_subscribe_notice;


    @AfterViews
    void afterView() {
        newMyRestClient.setRestErrorHandler(myErrorHandler);
        AndroidTool.showLoadDialog(this);
        GetSafeMessage();

    }

    //验证订阅
    @Background
    void GetSafeMessage() {
        BaseModelJson<String> bmj = newMyRestClient.SubscriptionExist(pre.username().get());
        AfterGetSafe(bmj);

    }

    @UiThread
    void AfterGetSafe(BaseModelJson<String> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
        } else if (bmj.Successful) {
            btn_subscribe.setVisibility(View.GONE);
            btn_cancel.setVisibility(View.VISIBLE);
            checkIsExistSubByUserName();
        } else {
            btn_cancel.setVisibility(View.GONE);
            btn_subscribe.setVisibility(View.VISIBLE);
        }
    }

    @Background
    void checkIsExistSubByUserName() {
        afterCheckIsExistSubByUserName(newMyRestClient.checkIsExistSubByUserName(pre.username().get()));
    }

    @UiThread
    void afterCheckIsExistSubByUserName(BaseModel bm) {
        if (bm != null && bm.Successful) {
            btn_cancel.setVisibility(View.GONE);
            btn_subscribe.setVisibility(View.VISIBLE);
            btn_subscribe.setText(txt_continue_subscribe);
        } else {
            btn_subscribe.setText(txt_subscribe);
        }
    }

    @OnActivityResult(value = 1000)
    void onResult(int resultCode) {
        if (resultCode == 1001) {
            super.finish();
        }
    }

    @UiThread
    void setBind(BaseModelJson<String> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj != null) {
            if (bmj.Successful) {
                //获取手机号码
                SubscribeActivity_.intent(this).startForResult(1000);
            } else {
                AndroidTool.showToast(this, bmj.Error);
            }
        }
    }


    //获取手机号码
    @Background
    void getBind() {
        BaseModelJson<String> bmj = newMyRestClient.GetMobile(pre.username().get());
        setBind(bmj);
    }

    //订阅
    @Click
    void btn_subscribe() {
        AndroidTool.showLoadDialog(this);
        getBind();

    }

    //取消订阅
    @Click
    void btn_cancel() {
        SubscribeCancelActivity_.intent(this).startForResult(1000);
    }
}
