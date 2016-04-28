package com.zczczy.leo.fuwuwangapp.activities;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2016/4/28.
 */
@EActivity(R.layout.activity_common_web_view)
public class CommonWebViewActivity extends BaseActivity {

    @ViewById
    MyTitleBar myTitleBar;

    @ViewById
    WebView web_common;

    @Extra
    String title, methodName;

    WebSettings settings;

    @AfterViews
    void setListener() {
        myTitleBar.setTitle(title);
        settings = web_common.getSettings();
        settings.setJavaScriptEnabled(true);
        web_common.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存
        web_common.loadUrl(MyApplication.URL + methodName);

        //判断页面加载过程
        web_common.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    // 网页加载完成
                    AndroidTool.dismissLoadDialog();
                } else {
                    AndroidTool.showLoadDialog(CommonWebViewActivity.this);
                }
            }
        });
    }

    public void onBackPressed() {
        // Do something
        if (web_common.canGoBack()) {
            web_common.goBack();//返回上一页面
        } else {
            // System.exit(0);//退出程序
            finish();
        }
    }
}
