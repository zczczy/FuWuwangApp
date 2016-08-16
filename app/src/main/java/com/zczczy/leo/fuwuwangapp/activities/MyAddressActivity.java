package com.zczczy.leo.fuwuwangapp.activities;

import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.rest.MyRestClient;
import com.zczczy.leo.fuwuwangapp.service.LocationService;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyAlertDialog;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.util.StringUtils;

/**
 * Created by Leo on 2016/5/16.
 */
@EActivity(R.layout.activity_my_address)
public class MyAddressActivity extends BaseActivity implements BDLocationListener {

    @RestService
    MyDotNetRestClient myDotNetRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    @ViewById
    MapView mMapView;

    @RestService
    MyRestClient myRestClient;

    LocationService locationService;

    BaiduMap mBaiduMap = null;

    Marker markersj = null;

    String longitude = "";

    String latitude = "";

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
        myDotNetRestClient.setRestErrorHandler(myErrorHandler);
        locationService = app.locationService;
    }

    @AfterViews
    void afterView() {
        AndroidTool.showLoadDialog(this);
        mBaiduMap = mMapView.getMap();
        locationService.registerListener(this);
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        getBind();
    }

    @Background
    void getBind() {
        myDotNetRestClient.setHeader("Token", pre.token().get());
        BaseModelJson<String> bmj = myDotNetRestClient.GetCompanyZb();
        afterGetBind(bmj);
    }

    @UiThread
    void afterGetBind(BaseModelJson<String> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj != null && bmj.Successful) {
            String[] str = bmj.Data.split(",");
            if (str.length >= 2) {
                setCenter(str[1], str[0]);
                //定义Maker坐标点
                LatLng point = new LatLng(Double.parseDouble(str[1]), Double.parseDouble(str[0]));
                //构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.baidumaptb);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap);

                //在地图上添加Marker，并显示
                markersj = (Marker) mBaiduMap.addOverlay(option);

                mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

                    @Override
                    public boolean onMarkerClick(Marker arg0) {
                        // TODO Auto-generated method stub
                        Toast.makeText(getApplicationContext(), "当前店铺位置！", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
            }
        }
        locationService.start();
    }

    //获取地理位置按钮点击事件
    @Click
    void btn_reset() {
        markersj.remove();
        setCenter(latitude, longitude);
        //定义Maker坐标点
        LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.baidumaptb);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);

        //在地图上添加Marker，并显示
        markersj = (Marker) mBaiduMap.addOverlay(option);
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                // TODO Auto-generated method stub
                AndroidTool.showToast(MyAddressActivity.this, "当前手机位置！");
                return false;
            }
        });
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("提示").setMessage("获取地理位置成功").setNegativeButton("确定", null).setIcon(R.mipmap.logo).create().show();
    }

    //保存按钮点击事件
    @Click
    void btnsave() {
        if (!StringUtils.isEmpty(longitude) && !StringUtils.isEmpty(latitude)) {
            AndroidTool.showLoadDialog(this);
            savezuo();
        } else {
            AndroidTool.showToast(MyAddressActivity.this, "没获取到当前位置");
        }
    }

    @Background
    void savezuo() {
        myRestClient.setHeader("Token", pre.token().get());
        BaseModelJson<String> bmj = myRestClient.SaveCompanyZb(latitude, longitude);
        showresult(bmj);
    }

    @UiThread
    void showresult(BaseModelJson<String> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj.Successful) {
            MyAlertDialog dialog = new MyAlertDialog(this, "店铺位置保存成功！", null);
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        } else {
            MyAlertDialog dialog = new MyAlertDialog(this, bmj.Error, null);
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        }
    }

    public void setCenter(String str1, String str2) {
        LatLng cenpt = new LatLng(Double.parseDouble(str1), Double.parseDouble(str2));
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(15)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        longitude = bdLocation.getLongitude() + "";
        latitude = bdLocation.getLatitude() + "";
        locationService.stop();
        if (markersj == null) {
            //Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
            setCenter(latitude, longitude);
            //定义Maker坐标点
            LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.baidumaptb);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);

            //在地图上添加Marker，并显示
            markersj = (Marker) mBaiduMap.addOverlay(option);

            mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

                @Override
                public boolean onMarkerClick(Marker arg0) {
                    // TODO Auto-generated method stub
                    Toast.makeText(getApplicationContext(), "当前手机位置！", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationService.stop();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mMapView = null;
    }

    @Override
    protected void onStop() {
        locationService.stop();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
