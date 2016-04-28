package com.zczczy.leo.fuwuwangapp.rest;

import android.content.Context;

import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.res.StringRes;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * Created by leo on 2015/6/2.
 */
@EBean
public class MyInterceptor implements ClientHttpRequestInterceptor {

    @RootContext
    Context context;

    @StringRes
    String no_net;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] data,
                                        ClientHttpRequestExecution execution) throws IOException {
        // do something
        //afterCheck();
        System.out.println();
        return execution.execute(request, data);
    }

    @Background
    void check() {
        afterCheck();
    }

    @UiThread
    void afterCheck() {
        AndroidTool.showLoadDialog(context);
        //AndroidTool.showToast(context, no_net);
    }
}
