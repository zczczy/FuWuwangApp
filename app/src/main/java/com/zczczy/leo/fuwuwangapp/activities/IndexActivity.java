package com.zczczy.leo.fuwuwangapp.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.squareup.otto.Subscribe;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.rest.MyBackgroundTask;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import java.util.ArrayList;

/**
 * Created by Leo on 2016/4/29.
 */
@EActivity(R.layout.activity_index)
public class IndexActivity extends BaseActivity {

    @Bean
    MyBackgroundTask myBackgroundTask;

    final int SDK_PERMISSION_REQUEST = 127;

    @Bean
    OttoBus bus;

    int i = 0;

    @AfterViews
    void afterView() {
        bus.register(this);
        myBackgroundTask.getAdvertByKbn();
        myBackgroundTask.getHomeBanner();
        myBackgroundTask.getLotteryConfigInfo();
        myBackgroundTask.getHomeGoodsTypeList();
        myBackgroundTask.getServiceAd();
        myBackgroundTask.getServiceGoodsTypeList();
        myBackgroundTask.getHomePupAd();
    }

    @Override
    public void finish() {
        bus.unregister(this);
        super.finish();
    }

    @Subscribe
    public void notifyUI(BaseModel bm) {
        i++;
        if (i == 7) {
            getPersimmions();
        }
    }

    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
            // 读写权限
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            } else {
                MainActivity_.intent(this).start();
                finish();
            }
        } else {
            MainActivity_.intent(this).start();
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivity_.intent(this).start();
        finish();
    }

}
