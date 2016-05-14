package com.zczczy.leo.fuwuwangapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.springframework.util.StringUtils;

/**
 * Created by Leo on 2016/4/27.
 */
@EFragment
public abstract class BaseFragment extends Fragment {

    @SystemService
    LayoutInflater layoutInflater;

    @App
    MyApplication app;

    @StringRes
    String no_net, empty_search, empty_order, empty_review, empty_logistics;

    @Pref
    MyPrefs_ pre;

    @ColorRes
    int line_color;

    /**
     * 判断用户是否登录
     *
     * @return
     */
    protected boolean checkUserIsLogin() {
        return !StringUtils.isEmpty(pre.shopToken().get());
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isVisible()) {
            onHiddenChanged(isVisible());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible()) {
            onHiddenChanged(isHidden());
        }
    }

    /**
     * 检查当前网络是否可用
     */
    public boolean isNetworkAvailable(Activity activity) {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
