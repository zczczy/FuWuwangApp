package com.zczczy.leo.fuwuwangapp.adapters;

import android.content.Context;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.items.FirstAreaItemView;
import com.zczczy.leo.fuwuwangapp.items.FirstAreaItemView_;
import com.zczczy.leo.fuwuwangapp.items.ItemView;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.NewArea;
import com.zczczy.leo.fuwuwangapp.model.StreetInfo;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 2016/5/10.
 */
@EBean
public class FirstAreaAdapter extends MyBaseAdapter<NewArea> {


    public List<FirstAreaItemView> getFirstAreaItemViewList() {
        return firstAreaItemViewList;
    }

    private List<FirstAreaItemView> firstAreaItemViewList = new ArrayList<>();

    @RestService
    MyDotNetRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    @App
    MyApplication app;

    @StringRes
    String no_net;

    @RootContext
    Context context;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
        if (app.getRegionList() != null) {
            setList(app.getRegionList());
        }
    }


    @Override
    @Background
    public void getMoreData(int pageIndex, int pageSize, Object... objects) {
        BaseModelJson<List<NewArea>> bmj = myRestClient.getAreaListByCityId(objects[0].toString());
        afterGetData(bmj);
    }


    @UiThread
    public void afterGetData(BaseModelJson<List<NewArea>> bmj) {
        if (bmj == null) {
            AndroidTool.showToast(context, no_net);
        } else if (bmj.Successful) {
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
            setList(app.getRegionList());
        } else {
            AndroidTool.showToast(context, bmj.Error);
        }
    }

    @Override
    protected ItemView<NewArea> getItemView(Context context) {
        FirstAreaItemView itemView = FirstAreaItemView_.build(context);
        firstAreaItemViewList.add(itemView);
        return itemView;
    }
}
