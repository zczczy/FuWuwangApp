package com.zczczy.leo.fuwuwangapp.activities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseUltimateRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.StoreAdapter;
import com.zczczy.leo.fuwuwangapp.model.StoreDetailModel;
import com.zczczy.leo.fuwuwangapp.tools.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Created by leo on 2016/5/16.
 */
@EActivity(R.layout.activity_store_search)
public class StoreSearchActivity extends BaseUltimateRecyclerViewActivity<StoreDetailModel> {

    @ViewById
    TextView emptyView;

    @ViewById
    TextView txt_title_search;

    @Extra
    String searchName;

    @Bean
    void setAdapter(StoreAdapter myAdapter) {
        this.myAdapter = myAdapter;
    }

    @AfterViews
    void afterView() {
        emptyView.setText(empty_search);
        txt_title_search.setText(searchName);
        myAdapter.setOnItemClickListener(new BaseUltimateRecyclerViewAdapter.OnItemClickListener<StoreDetailModel>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, StoreDetailModel obj, int position) {
                StoreInformationActivity_.intent(StoreSearchActivity.this).storeId(obj.StoreInfoId).start();

            }

            @Override
            public void onHeaderClick(RecyclerView.ViewHolder viewHolder, int position) {

            }
        });
        myTitleBar.setCustomViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void afterLoadMore() {
        myAdapter.getMoreData(pageIndex, Constants.PAGE_COUNT, isRefresh, 2, searchName);
    }

    @Override
    public void finish() {
        bus.unregister(this);
        setResult(RESULT_OK);
        super.finish();
    }
}
