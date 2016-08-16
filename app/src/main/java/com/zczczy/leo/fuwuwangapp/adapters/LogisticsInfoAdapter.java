package com.zczczy.leo.fuwuwangapp.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.activities.LogisticsInfoActivity;
import com.zczczy.leo.fuwuwangapp.items.LogisticsInfoItemView_;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.LogisticsInfo;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.tools.Constants;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;


/**
 * Created by zczczy on 2016/5/5.
 */
@EBean
public class LogisticsInfoAdapter extends BaseRecyclerViewAdapter<LogisticsInfo> {


    @RootContext
    LogisticsInfoActivity mLogisticsInfoActivity;

    @Override
    @Background
    public void getMoreData(Object... objects) {
        myRestClient.setHeader("Token", pre.token().get());
        myRestClient.setHeader("ShopToken", pre.shopToken().get());
        myRestClient.setHeader("Kbn", Constants.ANDROID);
        BaseModelJson<List<LogisticsInfo>> bmj = myRestClient.getLogistics(objects[0].toString());
        afterGetData(bmj);
    }

    @UiThread
    void afterGetData(BaseModelJson<List<LogisticsInfo>> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj != null && bmj.Successful) {
            if (bmj.Data.size() > 0) {
                bmj.Data.get(0).isLast = true;
                insertAll(bmj.Data, getItems().size());
            } else {
                mLogisticsInfoActivity.notifyUI();
            }
        } else {
            mLogisticsInfoActivity.notifyUI();
        }
    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return LogisticsInfoItemView_.build(parent.getContext());
    }
}
