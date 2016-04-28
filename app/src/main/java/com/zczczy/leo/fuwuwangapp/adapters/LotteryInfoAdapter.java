package com.zczczy.leo.fuwuwangapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zczczy.leo.fuwuwangapp.items.BaseViewHolder;
import com.zczczy.leo.fuwuwangapp.items.LotteryInfoItemView_;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.LotteryInfo;
import com.zczczy.leo.fuwuwangapp.model.PagerResult;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

/**
 * Created by Leo on 2016/3/9.
 */
@EBean
public class LotteryInfoAdapter extends BaseRecyclerViewAdapter<LotteryInfo> {

    @RestService
    MyDotNetRestClient myDotNetRestClient;

    @Pref
    MyPrefs_ pre;

    @StringRes
    String no_net;

    @Bean
    MyErrorHandler myErrorHandler;

    @Bean
    OttoBus bus;

    boolean isRefresh;

    @AfterInject
    void afterInject() {
        myDotNetRestClient.setRestErrorHandler(myErrorHandler);
    }


    @Override
    @Background
    public void getMoreData(int pageIndex, int pageSize, boolean isRefresh, Object... objects) {
        BaseModelJson<PagerResult<LotteryInfo>> bmj = null;
        this.isRefresh = isRefresh;
        switch (Integer.valueOf(objects[0].toString())) {
            case 0:
                bmj = myDotNetRestClient.getMyLotteryInfo(pre.username().get(), pageIndex, pageSize);
                break;
            case 1:
                bmj = myDotNetRestClient.getAllLotteryInfo(pageIndex, pageSize);
                break;
        }
        afterGetData(bmj);
    }

    @UiThread
    void afterGetData(BaseModelJson<PagerResult<LotteryInfo>> bmj) {
        if (bmj == null) {
            bmj = new BaseModelJson<>();
        } else if (bmj.Successful) {
            if (isRefresh) {
                clear();
            }
            setTotal(bmj.Data.RowCount);
            if (bmj.Data.ListData.size() > 0) {
                insertAll(bmj.Data.ListData, getItems().size());
            }
        } else {
            AndroidTool.showToast(context, bmj.Error);
        }
        bus.post(bmj);
    }


    @Override
    void onBindHeaderViewHolder(BaseViewHolder viewHolder) {

    }

    @Override
    protected View onCreateItemView(ViewGroup parent) {
        return LotteryInfoItemView_.build(parent.getContext());
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
