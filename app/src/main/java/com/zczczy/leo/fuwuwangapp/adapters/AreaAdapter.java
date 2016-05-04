package com.zczczy.leo.fuwuwangapp.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zczczy.leo.fuwuwangapp.items.AreaItemView_;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.NewArea;
import com.zczczy.leo.fuwuwangapp.model.NewCity;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;

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
 * Created by Leo on 2016/5/4.
 */
@EBean
public class AreaAdapter extends BaseRecyclerViewAdapter<NewArea> {

    @RestService
    MyDotNetRestClient myRestClient;

    @Pref
    MyPrefs_ pre;

    @StringRes
    String no_net;

    @Bean
    MyErrorHandler myErrorHandler;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @Override
    @Background
    public void getMoreData(Object... objects) {
        afterGetData(myRestClient.getAreaListByCityId(objects[0].toString()));
    }

    @UiThread
    void afterGetData(BaseModelJson<List<NewArea>> bmj) {
        if (bmj == null) {
            bmj = new BaseModelJson<>();
//            AndroidTool.showToast(context, no_net);
        } else if (bmj.Successful) {
            if (bmj.Data.size() > 0) {
                insertAll(bmj.Data, getItems().size());
            }
        }

    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return AreaItemView_.build(parent.getContext());
    }
}
