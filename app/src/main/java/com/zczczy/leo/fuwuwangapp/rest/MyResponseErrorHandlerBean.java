package com.zczczy.leo.fuwuwangapp.rest;

import org.androidannotations.annotations.EBean;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * Created by leo on 2016/5/8.
 */
@EBean
public class MyResponseErrorHandlerBean implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return false;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

    }
}
