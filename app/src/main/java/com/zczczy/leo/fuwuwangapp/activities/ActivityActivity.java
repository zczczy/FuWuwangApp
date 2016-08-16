package com.zczczy.leo.fuwuwangapp.activities;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.ActivityAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.BaseUltimateRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.model.Activity;
import com.zczczy.leo.fuwuwangapp.tools.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

/**
 * Created by Leo on 2016/4/28.
 */
@EActivity(R.layout.activity_activity)
public class ActivityActivity extends BaseUltimateRecyclerViewActivity<Activity> {

    @Bean
    void setAdapter(ActivityAdapter myAdapter) {
        this.myAdapter = myAdapter;
    }


    @AfterViews
    void afterView() {

        setListener();
    }

    void setListener() {

        myTitleBar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityChooseActivity_.intent(ActivityActivity.this).flagType("1").startForResult(1000);
            }
        });

        myAdapter.setOnItemClickListener(new BaseUltimateRecyclerViewAdapter.OnItemClickListener<Activity>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, Activity obj, int position) {
                ActivityInfoActivity_.intent(ActivityActivity.this).url(Constants.URL + "/DetailPage/ActivityDetail/" + obj.getAid()).status(obj.getStatus()).aid(obj.getAid()).start();
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
