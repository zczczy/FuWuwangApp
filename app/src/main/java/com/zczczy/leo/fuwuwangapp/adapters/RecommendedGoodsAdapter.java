package com.zczczy.leo.fuwuwangapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.items.BaseUltimateViewHolder;
import com.zczczy.leo.fuwuwangapp.items.RecommendedGoodsItemView_;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.Goods;
import com.zczczy.leo.fuwuwangapp.model.PagerResult;

import org.androidannotations.annotations.EBean;

import java.util.List;

/**
 * Created by Leo on 2016/4/27.
 */
@EBean
public class RecommendedGoodsAdapter extends BaseUltimateRecyclerViewAdapter<Goods> {


    @Override
    public void getMoreData(int pageIndex, int pageSize, boolean isRefresh, Object... objects) {
        BaseModelJson<PagerResult<Goods>> bmj = null;
        this.isRefresh = isRefresh;

        switch (Integer.valueOf(objects[0].toString())) {
            case 1:
                bmj = myRestClient.getRecommendedGoods(pageIndex, pageSize);
                break;
            case 2:
                bmj = myRestClient.getGoodsInfoByCity(objects[1] == null ? "" : objects[1].toString(), pageIndex, pageSize);
                break;
            case 3:
                bmj = new BaseModelJson<>();
                bmj.Successful = true;
                PagerResult<Goods> pagerResult = new PagerResult<>();
                pagerResult.ListData = (List<Goods>) objects[1];
                bmj.Data = pagerResult;
                break;
            case 4:
                bmj = myRestClient.getStoreAllGoodsList(objects[1].toString(), pageIndex, pageSize);
                break;
            case 5:
                BaseModelJson<List<Goods>> bm = myRestClient.getStoreIsCommendGoodsList(objects[1].toString());
                if (bm != null) {
                    bmj = new BaseModelJson<>();
                    bmj.Successful = bm.Successful;
                    bmj.Error = bm.Error;
                    PagerResult<Goods> pagerResult1 = new PagerResult<>();
                    pagerResult1.ListData = bm.Data;
                    bmj.Data = pagerResult1;
                }
                break;
            case 6:
                BaseModelJson<List<Goods>> bm1 = myRestClient.getStoreNewGoodsList(objects[1].toString());
                if (bm1 != null) {
                    bmj = new BaseModelJson<>();
                    bmj.Successful = bm1.Successful;
                    bmj.Error = bm1.Error;
                    PagerResult<Goods> pagerResult1 = new PagerResult<>();
                    pagerResult1.ListData = bm1.Data;
                    bmj.Data = pagerResult1;
                }
                break;
        }
        afterGetMoreData(bmj);
    }

    @Override
    void onBindHeaderViewHolder(BaseUltimateViewHolder viewHolder) {

    }

    @Override
    protected View onCreateItemView(ViewGroup parent) {
        View view = RecommendedGoodsItemView_.build(parent.getContext());
        view.setBackgroundResource(R.color.home_background);
        return view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
