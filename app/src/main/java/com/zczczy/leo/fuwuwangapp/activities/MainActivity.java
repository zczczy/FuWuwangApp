package com.zczczy.leo.fuwuwangapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.liulishuo.magicprogresswidget.MagicProgressCircle;
import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.fragments.CategoryFragment_;
import com.zczczy.leo.fuwuwangapp.fragments.HomeFragment_;
import com.zczczy.leo.fuwuwangapp.fragments.MineFragment_;
import com.zczczy.leo.fuwuwangapp.fragments.NewsFragment_;
import com.zczczy.leo.fuwuwangapp.fragments.ServiceFragment_;
import com.zczczy.leo.fuwuwangapp.model.Announcement;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.CityModel;
import com.zczczy.leo.fuwuwangapp.model.NewArea;
import com.zczczy.leo.fuwuwangapp.model.StreetInfo;
import com.zczczy.leo.fuwuwangapp.model.UpdateApp;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.service.LocationService;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.viewgroup.FragmentTabHost;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyHomedialog;
import com.zczczy.leo.fuwuwangapp.views.AnimTextView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.StringArrayRes;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * Created by Leo on 2016/4/27.
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements BDLocationListener {

    @ViewById
    public FragmentTabHost tabHost;

    @StringArrayRes
    String[] tabTag, tabTitle;

    @ViewById(android.R.id.tabs)
    TabWidget tabWidget;

    @RestService
    MyDotNetRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    //导航
//    Class[] classTab = {HomeFragment_.class, CategoryFragment_.class, ServiceFragment_.class, NewsFragment_.class, MineFragment_.class};
    Class[] classTab = {HomeFragment_.class, CategoryFragment_.class, ServiceFragment_.class, MineFragment_.class};

    @DrawableRes
    Drawable home_selector, mine_selector, category_selector, service_selector;
//    Drawable home_selector, news_selector, mine_selector, category_selector, service_selector;

//    Drawable[] drawables = new Drawable[5];
    Drawable[] drawables = new Drawable[4];

    @StringRes
    String progress_de;

    LocationService locationService;

    BaseModelJson<UpdateApp> appInfo;

    long firstTime = 0;

    View view;

    AlertDialog.Builder adb;

    AlertDialog ad;

    String downloadName;

    MagicProgressCircle p_downloadApk;

    AnimTextView p_text;

    /* 下载保存路径 */
    String mSavePath;

    File apkFile;

    MyHomedialog homedialog;

    @AfterInject
    void afterInject() {
        drawables[0] = home_selector;
        drawables[1] = category_selector;
        drawables[2] = service_selector;
//        drawables[3] = news_selector;
        drawables[3] = mine_selector;
        myRestClient.setRestErrorHandler(myErrorHandler);
        locationService = app.locationService;
    }

    @AfterViews
    void afterView() {
        initTab();
        locationService.registerListener(this);
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locationService.start();
        getannouncement();
    }

    protected void initTab() {
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        for (int i = 0; i < tabTag.length; i++) {
            Bundle bundle = new Bundle();
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(tabTag[i]);
            tabSpec.setIndicator(buildIndicator(i));
            tabHost.addTab(tabSpec, classTab[i], bundle);
        }
        tabHost.setCurrentTab(0);
    }

    protected View buildIndicator(int position) {
        View view = layoutInflater.inflate(R.layout.tab_indicator, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.icon_tab);
        TextView textView = (TextView) view.findViewById(R.id.text_indicator);
        imageView.setImageDrawable(drawables[position]);
        textView.setText(tabTitle[position]);
        return view;
    }

    @Background
    void getUpdateApp() {
        BaseModelJson<UpdateApp> bmj = myRestClient.AppUpdCheck(1);
        GetUpdate(bmj);
    }

    @UiThread
    void GetUpdate(BaseModelJson<UpdateApp> bmj) {
        if (bmj == null) {
//            AndroidTool.showToast(this, no_net);
        } else if (bmj.Successful) {
            appInfo = bmj;
            int versionCode = 0;
            try {
                // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
                versionCode = this.getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if (bmj.Data.getVersioncode() > versionCode) {
                //升级
                updateNotice();
            }
        } else {
            AndroidTool.showToast(this, bmj.Error);
        }
    }

    void updateNotice() {
        AlertDialog.Builder adbr = new AlertDialog.Builder(this);
        adbr.setTitle("提示").setMessage("有新版本，请更新").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                view = layoutInflater.inflate(R.layout.new_update_dialog, null);
                p_downloadApk = (MagicProgressCircle) view.findViewById(R.id.p_downloadApk);
                p_text = (AnimTextView) view.findViewById(R.id.p_text);
                adb = new AlertDialog.Builder(MainActivity.this);
                ad = adb.setView(view).create();
                ad.setCancelable(false);
                downloadApk();
                ad.show();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setCancelable(false).create().show();
    }

    @Background
    void downloadApk() {

        try {
            // 判断SD卡是否存在，并且是否具有读写权限
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                // 获得存储卡的路径
                String sdpath = Environment.getExternalStorageDirectory() + "/";
                mSavePath = sdpath + "download_cache";
                URL url = new URL(appInfo.Data.versionurl);
                // 创建连接
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                // 获取文件大小
                int length = conn.getContentLength();
                // 创建输入流
                InputStream is = conn.getInputStream();
                File file = new File(mSavePath);
                // 判断文件目录是否存在
                if (!file.exists()) {
                    file.mkdir();
                }
                downloadName = AndroidTool.getYYYYMMDDHHMMSS(new Date()) + "fuwuwang.apk";
                apkFile = new File(mSavePath, downloadName);
                FileOutputStream fos = new FileOutputStream(apkFile);
                int count = 0;
                // 缓存
                byte buf[] = new byte[1024];
                // 写入到文件中
                do {
                    int numread = is.read(buf);
                    count += numread;
                    // 计算进度条位置
                    updateDownloadProgress(((float) count) / length);
                    if (numread <= 0) {
                        // 下载完成
                        pre.clear();
                        installApk();
                        break;
                    }
                    // 写入文件
                    fos.write(buf, 0, numread);
                } while (true);// 点击取消就停止下载.
                fos.close();
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @UiThread
    void updateDownloadProgress(float progress) {
        p_downloadApk.setPercent(progress);
        p_text.setText(String.format(progress_de, (int) (progress * 100)));
    }

    //安装apk文件
    private void installApk() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);     //浏览网页的Action(动作)
        String type = "application/vnd.android.package-archive";
        intent.setDataAndType(Uri.fromFile(apkFile), type);  //设置数据类型
        startActivity(intent);
    }

    // 通告
    @Background
    void getannouncement() {
        BaseModelJson<Announcement> bmj = myRestClient.GetAppConfig(1);
        if (bmj != null && bmj.Successful) {
            Getannouncement(bmj.Data.getAppConfigId(), bmj.Data.getAppConfigTitle(), bmj.Data.getAppConfigContent(), bmj.Data.getIsCloseBtn(), bmj.Data.getIsShow());
        }
    }

    @UiThread
    void Getannouncement(int AppConfigId, String AppConfigTitle, String AppConfigContent, String IsCloseBtn, String IsShow) {
        if ("1".equals(IsShow)) {
            homedialog = new MyHomedialog(this, AppConfigTitle, AppConfigContent, IsCloseBtn, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    homedialog.dismiss();
                    getUpdateApp();
                }
            });
            homedialog.show();
            homedialog.setCancelable(false);


        } else {
            getUpdateApp();
        }
    }

    /**
     * 百度定位回调
     *
     * @param bdLocation
     */
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        pre.locationAddress().put(bdLocation.getCity());
        getCityCode();
    }

    /**
     * 获取城市id
     */
    @Background
    void getCityCode() {
        afterGetCityCode(myRestClient.getCityId(pre.locationAddress().getOr("北京")));
    }

    @UiThread
    void afterGetCityCode(BaseModelJson<CityModel> bmj) {
        if (bmj != null && bmj.Successful) {
            pre.locationAddress().put(bmj.Data.CityName);
            pre.cityId().put(bmj.Data.CityId);
            getArea(bmj.Data.CityId);
        }
    }

    /**
     * 查询区域（包括商圈）根据城市id （服务页面用的）
     *
     * @param cityId
     */
    @Background
    void getArea(String cityId) {
        BaseModelJson<List<NewArea>> bmj = myRestClient.getAreaByCity(cityId);
        if (bmj != null && bmj.Successful) {
            app.setRegionList(bmj.Data);
            for (NewArea newRegion : app.getRegionList()) {
                if (newRegion.listStreet != null) {
                    StreetInfo newStreet = new StreetInfo();
                    newStreet.StreetInfoId = Integer.valueOf(newRegion.CityId);
                    newStreet.StreetName = "全部";
                    newStreet.AreaId = Integer.valueOf(newRegion.AreaId);
                    newRegion.listStreet.add(0, newStreet);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            AndroidTool.showToast(this, "再按一次退出程序");
            firstTime = secondTime;
        } else {
            finish();
            System.exit(-1);
        }
    }

}
