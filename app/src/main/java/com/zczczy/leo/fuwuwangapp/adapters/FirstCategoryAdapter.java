package com.zczczy.leo.fuwuwangapp.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.items.FirstCategoryItemView_;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.GoodsTypeModel;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
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
public class FirstCategoryAdapter extends BaseRecyclerViewAdapter<GoodsTypeModel> {

    @RestService
    MyDotNetRestClient myRestClient;

    @App
    MyApplication app;

    @Pref
    MyPrefs_ pre;

    @StringRes
    String no_net;

    @Bean
    OttoBus bus;

    @Bean
    MyErrorHandler myErrorHandler;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @Override
    @Background
    public void getMoreData(Object... objects) {
        BaseModelJson<List<GoodsTypeModel>> bmj;
        if (app.getGoodsTypeModelList().size() == 0) {
            bmj = myRestClient.getGoodsType(objects[0].toString());
        } else {
            bmj = new BaseModelJson<>();
            bmj.Successful = true;
            bmj.Data = app.getGoodsTypeModelList();
        }
        afterGetData(bmj);
    }

    @UiThread
    void afterGetData(BaseModelJson<List<GoodsTypeModel>> bmj) {
        if (bmj == null) {
            bmj = new BaseModelJson<>();
//            AndroidTool.showToast(context, no_net);
        } else if (bmj.Successful) {
            if (bmj.Data.size() > 0) {
                insertAll(bmj.Data, getItems().size());
            }
        }
        bus.post(bmj);
    }


    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return FirstCategoryItemView_.build(parent.getContext());
    }


}