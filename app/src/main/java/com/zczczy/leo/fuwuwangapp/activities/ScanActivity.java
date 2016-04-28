package com.zczczy.leo.fuwuwangapp.activities;


import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import com.zczczy.leo.fuwuwangapp.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

/**
 * Created by Leo on 2016/4/28.
 */
@EActivity(R.layout.activity_scan)
public class ScanActivity extends BaseActivity implements QRCodeView.Delegate {


    @ViewById
    ZBarView zbarView;

    @AfterViews
    void afterView(){
        zbarView.setResultHandler(this);
        zbarView.startSpot();
    }

    @Override
    protected void onStart() {
        super.onStart();
        zbarView.startCamera();
    }

    @Override
    protected void onStop() {
        zbarView.stopCamera();
        super.onStop();
    }

    void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("result", result);
        resultIntent.putExtras(bundle);
        setResult(RESULT_OK,resultIntent);
        finish();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(ScanActivity.class.getSimpleName(), "打开相机出错");
    }


}
