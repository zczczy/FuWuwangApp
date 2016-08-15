package com.zczczy.leo.fuwuwangapp.adapters;

import android.view.View;
import android.view.ViewGroup;


import com.zczczy.leo.fuwuwangapp.items.GoodsDetailRecommendItemView_;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.Goods;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

import java.util.List;


/**
 * Created by leo on 2016/6/18.
 */
@EBean
public class DetailGoodsRecommendAdapter extends BaseRecyclerViewAdapter<Goods> {

    @Bean
    OttoBus bus;

    @Override
    public void getMoreData(Object... objects) {
        BaseModelJson<List<Goods>> result = new BaseModelJson<>();
        result.Data = (List<Goods>) objects[0];
        result.Successful = true;
        afterGetData(result);
    }

    @UiThread
    void afterGetData(BaseModelJson<List<Goods>> result) {
        AndroidTool.dismissLoadDialog();
        if (result == null) {
            result = new BaseModelJson<>();
//            AndroidTool.showToast(context, no_net);
        } else if (result.Successful) {
            if (result.Data != null && result.Data.size() > 0) {
                insertAll(result.Data, getItems().size());
                bus.post(result);
            }
        }
    }


    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return GoodsDetailRecommendItemView_.build(context);
    }
}

