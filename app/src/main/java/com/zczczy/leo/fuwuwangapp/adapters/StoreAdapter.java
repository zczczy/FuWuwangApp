package com.zczczy.leo.fuwuwangapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.items.BaseUltimateViewHolder;
import com.zczczy.leo.fuwuwangapp.items.StoreItemView_;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.PagerResult;
import com.zczczy.leo.fuwuwangapp.model.StoreDetailModel;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

/**
 * Created by Leo on 2016/5/10.
 */
@EBean
public class StoreAdapter extends BaseUltimateRecyclerViewAdapter<StoreDetailModel> {

    @RestService
    MyDotNetRestClient myRestClient;

    @App
    MyApplication app;

    @Pref
    MyPrefs_ pre;

    @Bean
    OttoBus bus;

    @StringRes
    String no_net;

    @Bean
    MyErrorHandler myErrorHandler;

    boolean isRefresh = false;


    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }


    @Override
    @Background
    public void getMoreData(int pageIndex, int pageSize, boolean isRefresh, Object... objects) {
        this.isRefresh = isRefresh;
        BaseModelJson<PagerResult<StoreDetailModel>> bmj = null;
        switch (Integer.valueOf(objects[0].toString())) {
            case 1:
                bmj = myRestClient.getStoreInfo(
                        objects[1] == null ? 0 : Integer.valueOf(objects[1].toString()),
                        objects[2] == null ? 0 : Integer.valueOf(objects[2].toString()),
                        objects[3] == null ? 0 : Integer.valueOf(objects[3].toString()),
                        objects[4] == null ? "" : objects[4].toString(),
                        objects[5] == null ? "" : objects[5].toString(),
                        pageIndex, pageSize);
                break;
            case 2:
                bmj = myRestClient.getStoreInfoByGoodsType(objects[1] == null ? "" : objects[1].toString(), pageIndex, pageSize);
                break;
        }
        afterGetData(bmj);
    }

    @UiThread
    void afterGetData(BaseModelJson<PagerResult<StoreDetailModel>> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
            AndroidTool.showToast(context, no_net);
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
    void onBindHeaderViewHolder(BaseUltimateViewHolder viewHolder) {

    }

    @Override
    protected View onCreateItemView(ViewGroup parent) {
        return StoreItemView_.build(parent.getContext());
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
