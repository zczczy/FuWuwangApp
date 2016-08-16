package com.zczczy.leo.fuwuwangapp.activities;

import android.support.v7.widget.RecyclerView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseUltimateRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.InformationAdapter;
import com.zczczy.leo.fuwuwangapp.model.Information;
import com.zczczy.leo.fuwuwangapp.tools.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

/**
 * Created by Leo on 2016/4/28.
 */
@EActivity(R.layout.activity_notice)
public class InformationActivity extends BaseUltimateRecyclerViewActivity<Information> {


    @Bean
    void setAdapter(InformationAdapter myAdapter) {
        this.myAdapter = myAdapter;
    }

    @AfterViews
    void afterView() {
        setListener();
    }

    void setListener() {
        myAdapter.setOnItemClickListener(new BaseUltimateRecyclerViewAdapter.OnItemClickListener<Information>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, Information information, int position) {
                CommonWebViewActivity_.intent(InformationActivity.this).title("资讯详情").methodName(Constants.DETAIL_PAGE_ACTION + Constants.NEWS_DETAIL_METHOD + information.getNoticeId()).start();
            }

            @Override
            public void onHeaderClick(RecyclerView.ViewHolder viewHolder, int position) {
            }
        });
    }

    void afterLoadMore() {
        myAdapter.getMoreData(pageIndex, 10, isRefresh);
    }

}
