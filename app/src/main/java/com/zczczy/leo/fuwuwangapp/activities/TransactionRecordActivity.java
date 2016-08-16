package com.zczczy.leo.fuwuwangapp.activities;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.TransactionRecordAdapter;
import com.zczczy.leo.fuwuwangapp.model.Purse;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

/**
 * Created by Leo on 2016/4/28.
 */
@EActivity(R.layout.activity_transaction_record)
public class TransactionRecordActivity extends BaseUltimateRecyclerViewActivity<Purse> {

    @Bean
    void setAdapter(TransactionRecordAdapter myAdapter) {
        this.myAdapter = myAdapter;
    }

    @AfterViews
    void afterView() {
    }

    void afterLoadMore() {
        myAdapter.getMoreData(pageIndex, 10, isRefresh);
    }

}
