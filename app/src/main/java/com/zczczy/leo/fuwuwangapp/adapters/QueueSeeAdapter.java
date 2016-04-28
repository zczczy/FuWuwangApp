package com.zczczy.leo.fuwuwangapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zczczy.leo.fuwuwangapp.items.BaseViewHolder;
import com.zczczy.leo.fuwuwangapp.items.QueueSeeItemView_;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.YpdRecord;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.rest.MyRestClient;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

/**
 * Created by Leo on 2016/4/28.
 */
@EBean
public class QueueSeeAdapter extends BaseRecyclerViewAdapter<YpdRecord> {

    @StringRes
    String no_net;

    @Bean
    MyErrorHandler myErrorHandler;

    @RestService
    MyRestClient myRestClient;


    @Pref
    MyPrefs_ pre;

    boolean isRefresh = false;


    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @Override
    @Background
    public void getMoreData(int pageIndex, int pageSize, boolean isRefresh, Object... objects) {
        String token = pre.token().get();
        myRestClient.setHeader("Token", token);
        BaseModelJson<List<YpdRecord>> bmj;
        if (!"0".equals(objects[0].toString())) {
            bmj = myRestClient.GetCurrYpdInfo(getItems().get(0).getDateVal(), objects[0].toString());
        } else {
            bmj = myRestClient.GetCurrYpdInfo("", objects[0].toString());
        }
        afterGetData(bmj);
    }


    @UiThread
    void afterGetData(BaseModelJson<List<YpdRecord>> bmj) {
        if (bmj == null) {
            bmj = new BaseModelJson<>();
//            AndroidTool.showToast(context, no_net);
        } else if (bmj.Successful) {
            clear();
            if (bmj.Data.size() > 0) {
                insertAll(bmj.Data, getItems().size());
            }
        } else {
            AndroidTool.showToast(context, bmj.Error);
        }
    }

    @Override
    void onBindHeaderViewHolder(BaseViewHolder viewHolder) {

    }

    @Override
    protected View onCreateItemView(ViewGroup parent) {
        return QueueSeeItemView_.build(parent.getContext());
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
