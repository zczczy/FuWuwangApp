package com.zczczy.leo.fuwuwangapp.activities;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2016/4/28.
 */
@EActivity(R.layout.activity_web_view)
public class ServiceActivity extends BaseActivity {

    @ViewById
    MyTitleBar myTitleBar;

    @ViewById
    WebView wv_web;

    WebSettings settings;

    @AfterViews
    void afterView() {
        AndroidTool.showLoadDialog(this);
        myTitleBar.setTitle("增值服务");
        settings = wv_web.getSettings();
        settings.setJavaScriptEnabled(true);
        wv_web.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存
        wv_web.loadUrl(rootUrl + "DetailPage/AddedServiceDetail");
        //判断页面加载过程
        wv_web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    // 网页加载完成
                    AndroidTool.dismissLoadDialog();
                }
            }
        });
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
