package com.zczczy.leo.fuwuwangapp.activities;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
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
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.util.StringUtils;

/**
 * Created by Leo on 2016/4/28.
 */
@EActivity(R.layout.activity_activity_info)
public class ActivityInfoActivity extends BaseActivity {

    @ViewById
    MyTitleBar myTitleBar;

    @ViewById
    Button btn_apply;

    @ViewById
    WebView wv_web;

    @Extra
    String status, url;

    @Extra
    Integer aid;

    WebSettings settings;

    @Bean
    MyErrorHandler myErrorHandler;

    @RestService
    MyRestClient myRestClient;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        //根据status设置button的属性
        if (status.equals("0")) {
            btn_apply.setEnabled(true);
            btn_apply.setBackground(getResources().getDrawable(R.drawable.button_selector));
        }
        if (status.equals("1")) {
            btn_apply.setEnabled(false);
            btn_apply.setBackground(getResources().getDrawable(R.drawable.button_act_gray));
        }
        if (status.equals("2")) {
            btn_apply.setEnabled(false);
            btn_apply.setBackground(getResources().getDrawable(R.drawable.button_act_gray));
        }
        settings = wv_web.getSettings();
        settings.setJavaScriptEnabled(true);
        wv_web.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存
        //wv_web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存：
        wv_web.loadUrl(url);
        //判断页面加载过程
        wv_web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    // 网页加载完成
                    AndroidTool.dismissLoadDialog();
                } else {
                    AndroidTool.showLoadDialog(ActivityInfoActivity.this);
                }
            }
        });
    }

    //报名按钮点击事件
    @Click
    void btn_apply() {
        if (isNetworkAvailable(this)) {
            String token = pre.token().get();
            if (StringUtils.isEmpty(token)) {
                AndroidTool.showToast(this, "请先登录");
            } else {
                AndroidTool.showLoadDialog(this);
                getHttp();
            }
        } else {
            AndroidTool.showToast(this, no_net);
        }
    }

    @Background
    void getHttp() {
        String token = pre.token().get();
        myRestClient.setHeader("Token", token);
        BaseModelJson<String> bmj = myRestClient.Apply(aid);
        show(bmj);
    }

    @UiThread
    void show(BaseModelJson<String> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj != null) {
            if (bmj.Successful) {
                AndroidTool.showToast(this, "报名成功");
                btn_apply.setEnabled(false);
                btn_apply.setBackground(getResources().getDrawable(R.drawable.button_act_gray));
            } else {
                AndroidTool.showToast(this, bmj.Error);
            }
        }
    }

    public void onBackPressed() {
        // Do something
        if (wv_web.canGoBack()) {
            wv_web.goBack();//返回上一页面
        } else {
            // System.exit(0);//退出程序
            finish();
        }
    }
}
