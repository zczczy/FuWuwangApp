package com.zczczy.leo.fuwuwangapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.items.BaseUltimateViewHolder;
import com.zczczy.leo.fuwuwangapp.items.GoodsItemView_;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.Goods;
import com.zczczy.leo.fuwuwangapp.model.PagerResult;
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

import java.util.List;

/**
 * Created by Leo on 2016/5/5.
 */
@EBean
public class GoodsAdapters extends BaseUltimateRecyclerViewAdapter<Goods> {

    @Override
    public void getMoreData(int pageIndex, int pageSize, boolean isRefresh, Object... objects) {
        BaseModelJson<PagerResult<Goods>> bmj = null;
        this.isRefresh = isRefresh;
        switch (Integer.valueOf(objects[0].toString())) {
            case 0:
                bmj = new BaseModelJson<>();
                PagerResult<Goods> pagerResult = new PagerResult<>();
                bmj.Data = pagerResult;
                bmj.Successful = true;
                pagerResult.ListData = (List<Goods>) objects[1];
                break;
            case 1:
                bmj = myRestClient.getGoodsByGoodsTypeId(
                        (objects[1] == null || "".equals(objects[1])) ? "0" : objects[1].toString(),
                        objects[2].toString(),
                        objects[3] == null ? "" : objects[3].toString(),
                        objects[4].toString(),
                        (objects[5] == null || "".equals(objects[5])) ? "0" : objects[5].toString(),
                        (objects[6] == null || "".equals(objects[6])) ? "0" : objects[6].toString(),
                        pageIndex, pageSize,
                        (objects[7] == null || "".equals(objects[7])) ? "" : objects[7].toString()
                );
                break;
        }
        afterGetMoreData(bmj);
    }


    @Override
    void onBindHeaderViewHolder(BaseUltimateViewHolder viewHolder) {

    }

    @Override
    protected View onCreateItemView(ViewGroup parent) {
        return GoodsItemView_.build(parent.getContext());
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
