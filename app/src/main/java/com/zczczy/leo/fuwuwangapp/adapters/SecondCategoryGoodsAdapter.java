package com.zczczy.leo.fuwuwangapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zczczy.leo.fuwuwangapp.items.BaseUltimateViewHolder;
import com.zczczy.leo.fuwuwangapp.items.SecondCategoryGoodsItemView_;
import com.zczczy.leo.fuwuwangapp.model.Goods;

import org.androidannotations.annotations.EBean;

/**
 * @author Created by LuLeo on 2016/8/16.
 *         you can contact me at :361769045@qq.com
 * @since 2016/8/16.
 */
@EBean
public class SecondCategoryGoodsAdapter extends BaseUltimateRecyclerViewAdapter<Goods> {

    @Override
    public void getMoreData(int pageIndex, int pageSize, boolean isRefresh, Object... objects) {
        this.isRefresh = isRefresh;
        afterGetMoreData(myRestClient.getGoodsByGoodsTypeId(
                objects[0].toString(),
                objects[1].toString(),
                objects[2].toString(),
                objects[3].toString(),
                (objects[4] == null || "".equals(objects[4])) ? "0" : objects[4].toString(),
                (objects[5] == null || "".equals(objects[5])) ? "0" : objects[5].toString(),
                pageIndex, pageSize,
                (objects[6] == null || "".equals(objects[6])) ? "" : objects[6].toString()
                )
        );
    }

    @Override
    void onBindHeaderViewHolder(BaseUltimateViewHolder viewHolder) {

    }

    @Override
    protected View onCreateItemView(ViewGroup parent) {
        return SecondCategoryGoodsItemView_.build(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
