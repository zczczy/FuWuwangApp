package com.zczczy.leo.fuwuwangapp.fragments;

import android.annotation.SuppressLint;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;


import com.zczczy.leo.fuwuwangapp.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

/**
 * @author Created by LuLeo on 2016/6/12.
 *         you can contact me at :361769045@qq.com
 * @since 2016/6/12.
 */
@EFragment(R.layout.fragment_goods_detail)
public class GoodsDetailFragment extends BaseFragment {

    @ViewById
    WebView web_view;

    @FragmentArg
    String linkUrl;

    WebSettings settings;

    @SuppressLint("SetJavaScriptEnabled")
    @AfterViews
    void afterView() {
        settings = web_view.getSettings();
        settings.setJavaScriptEnabled(true);
        web_view.getSettings().setAllowFileAccess(true);
        web_view.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存
        web_view.loadUrl(linkUrl);
        web_view.setWebChromeClient(new WebChromeClient() {

        });
    }

}
