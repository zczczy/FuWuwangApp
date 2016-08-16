package com.zczczy.leo.fuwuwangapp.fragments;

import android.support.v7.widget.RecyclerView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.activities.GoodsDetailActivity_;
import com.zczczy.leo.fuwuwangapp.adapters.BaseUltimateRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.SecondCategoryGoodsAdapter;
import com.zczczy.leo.fuwuwangapp.model.Goods;
import com.zczczy.leo.fuwuwangapp.tools.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

/**
 * @author Created by LuLeo on 2016/8/16.
 *         you can contact me at :361769045@qq.com
 * @since 2016/8/16.
 */
@EFragment(R.layout.fragment_common_category)
public class SecondCategoryGoodsFragment extends BaseUltimateRecyclerViewFragment<Goods> {


    @FragmentArg
    String GoodsTypeId, GoodsType, likeName, orderBy, priceMin, priceMax;

    @Bean
    void setMyAdapter(SecondCategoryGoodsAdapter myAdapter) {
        this.myAdapter = myAdapter;
    }

    @AfterViews
    void afterView() {
        myAdapter.setOnItemClickListener(new BaseUltimateRecyclerViewAdapter.OnItemClickListener<Goods>() {
            public void onItemClick(RecyclerView.ViewHolder viewHolder, Goods obj, int position) {
                GoodsDetailActivity_.intent(SecondCategoryGoodsFragment.this).goodsId(obj.GoodsInfoId).start();
            }

            @Override
            public void onHeaderClick(RecyclerView.ViewHolder viewHolder, int position) {

            }
        });
    }

    @Override
    void afterLoadMore() {
        myAdapter.getMoreData(pageIndex, Constants.PAGE_COUNT, isRefresh, GoodsTypeId, GoodsType, likeName, orderBy, priceMin, priceMax);
    }
}
