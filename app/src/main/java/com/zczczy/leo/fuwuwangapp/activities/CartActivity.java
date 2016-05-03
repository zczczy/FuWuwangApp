package com.zczczy.leo.fuwuwangapp.activities;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.marshalchen.ultimaterecyclerview.CustomLinearLayoutManager;
import com.squareup.otto.Subscribe;
import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.CartAdapter;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Created by Leo on 2016/5/1.
 */
@EActivity(R.layout.activity_cart)
public class CartActivity extends BaseActivity {

    @ViewById
    RecyclerView recyclerView;

    @Bean(CartAdapter.class)
    BaseRecyclerViewAdapter myAdapter;

    @Bean
    OttoBus bus;

    CustomLinearLayoutManager linearLayoutManager;

    MaterialHeader materialHeader;

    int pageIndex = 1;

    boolean isRefresh;

    @AfterViews
    void afterView() {
        bus.register(this);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new CustomLinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
        afterLoadMore();
    }

    void afterLoadMore() {
        myAdapter.getMoreData(pageIndex, MyApplication.PAGECOUNT, isRefresh);
    }

    @Subscribe
    public void notifyUI(BaseModel bm) {

    }
}
