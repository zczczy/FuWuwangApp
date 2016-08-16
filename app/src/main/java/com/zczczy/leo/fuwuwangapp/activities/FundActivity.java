package com.zczczy.leo.fuwuwangapp.activities;

import android.support.v7.widget.RecyclerView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseUltimateRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.NoticeAdapter;
import com.zczczy.leo.fuwuwangapp.model.Notice;
import com.zczczy.leo.fuwuwangapp.tools.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

/**
 * Created by Leo on 2016/4/28.
 */
@EActivity(R.layout.activity_notice)
public class FundActivity extends BaseUltimateRecyclerViewActivity<Notice> {

    @Bean
    void setAdapter(NoticeAdapter myAdapter) {
        this.myAdapter = myAdapter;
    }

    @AfterViews
    void afterView() {
        setListener();
    }

    void setListener() {
        myAdapter.setOnItemClickListener(new BaseUltimateRecyclerViewAdapter.OnItemClickListener<Notice>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, Notice notice, int position) {
                CommonWebViewActivity_.intent(FundActivity.this).title("资讯详情").methodName(Constants.DETAIL_PAGE_ACTION + Constants.FUNCTION_DETAIL_METHOD + notice.NoticeId).start();
            }

            @Override
            public void onHeaderClick(RecyclerView.ViewHolder viewHolder, int position) {
            }
        });
    }


    void afterLoadMore() {
        myAdapter.getMoreData(pageIndex, 10, isRefresh, 1);
    }
}

