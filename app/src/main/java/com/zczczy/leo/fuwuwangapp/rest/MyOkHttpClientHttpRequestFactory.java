package com.zczczy.leo.fuwuwangapp.rest;

import android.content.Context;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.springframework.http.client.OkHttpClientHttpRequestFactory;

/**
 * Created by Leo on 2015/12/22. OkHttpClientHttpRequestFactory
 */
@EBean
public class MyOkHttpClientHttpRequestFactory extends OkHttpClientHttpRequestFactory {


    @RootContext
    Context context;

    @AfterInject
    void afterInject() {
        this.setConnectTimeout(30 * 1000);
        this.setReadTimeout(15 * 1000);
        this.setWriteTimeout(30 * 1000);
    }

}
