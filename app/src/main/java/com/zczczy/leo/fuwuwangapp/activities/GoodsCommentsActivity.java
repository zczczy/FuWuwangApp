package com.zczczy.leo.fuwuwangapp.activities;

import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.GoodsCommentsAdapter;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.GoodsCommentsModel;
import com.zczczy.leo.fuwuwangapp.tools.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2016/5/1.
 */
@EActivity(R.layout.activity_goods_comments)
public class GoodsCommentsActivity extends BaseUltimateRecyclerViewActivity<GoodsCommentsModel> {

    @ViewById
    TextView emptyView;

    @Bean
    OttoBus bus;

    @Extra
    String goodsId;

    @Bean
    void setAdapter(GoodsCommentsAdapter myAdapter) {
        this.myAdapter = myAdapter;
    }

    @AfterViews
    void afterView() {

    }

    void afterLoadMore() {
        myAdapter.getMoreData(pageIndex, Constants.PAGE_COUNT, isRefresh, goodsId);
    }
}
