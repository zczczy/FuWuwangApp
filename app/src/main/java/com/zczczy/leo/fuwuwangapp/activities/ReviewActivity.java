package com.zczczy.leo.fuwuwangapp.activities;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseUltimateRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.ReviewAdapter;
import com.zczczy.leo.fuwuwangapp.model.OrderDetailModel;
import com.zczczy.leo.fuwuwangapp.tools.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

/**
 * Created by leo on 2016/5/8.
 */
@EActivity(R.layout.activity_review)
public class ReviewActivity extends BaseUltimateRecyclerViewActivity<OrderDetailModel> {

    @ViewById
    TextView emptyView;

    @Bean
    void setAdapter(ReviewAdapter myAdapter) {
        this.myAdapter = myAdapter;
    }


    @AfterViews
    void afterView() {
        emptyView.setText(empty_no_review);
        myAdapter.setOnItemClickListener(new BaseUltimateRecyclerViewAdapter.OnItemClickListener<OrderDetailModel>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, OrderDetailModel obj, int position) {
                PublishReviewActivity_.intent(ReviewActivity.this).model(obj).startForResult(1000);
            }

            @Override
            public void onHeaderClick(RecyclerView.ViewHolder viewHolder, int position) {

            }
        });

    }

    void afterLoadMore() {
        myAdapter.getMoreData(pageIndex, Constants.PAGE_COUNT, isRefresh);
    }

    @OnActivityResult(1000)
    void afterReview(int resultCode) {
        if (resultCode == RESULT_OK) {
            isRefresh = true;
            pageIndex = 1;
            afterLoadMore();
        }
    }


    @Override
    public void finish() {
        bus.unregister(this);
        super.finish();
    }
}
