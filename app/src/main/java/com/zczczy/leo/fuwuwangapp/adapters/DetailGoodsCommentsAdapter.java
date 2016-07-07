package com.zczczy.leo.fuwuwangapp.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zczczy.leo.fuwuwangapp.items.GoodsCommentsItemView_;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.GoodsCommentsModel;
import com.zczczy.leo.fuwuwangapp.model.PagerResult;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.rest.spring.annotations.RestService;

/**
 * Created by leo on 2016/6/18.
 */
@EBean
public class DetailGoodsCommentsAdapter extends BaseRecyclerViewAdapter<GoodsCommentsModel> {

    @Bean
    OttoBus bus;

    @RestService
    MyDotNetRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    @AfterInject
    void afterBaseInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @Override
    @Background
    public void getMoreData(Object... objects) {
        afterGetData(myRestClient.getGoodsCommentsByGoodsInfoId(objects[0].toString(), 1, 20));
    }

    @UiThread
    void afterGetData(BaseModelJson<PagerResult<GoodsCommentsModel>> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
            bmj = new BaseModelJson<>();
//            AndroidTool.showToast(context, no_net);
        } else if (bmj.Successful) {
            if (bmj.Data.ListData.size() > 0) {
                insertAll(bmj.Data.ListData, getItems().size());
                bus.post(bmj);
            }
        }
    }


    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return GoodsCommentsItemView_.build(parent.getContext());
    }
}

