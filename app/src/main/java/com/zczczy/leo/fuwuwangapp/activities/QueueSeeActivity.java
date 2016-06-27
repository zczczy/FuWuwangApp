package com.zczczy.leo.fuwuwangapp.activities;

import android.support.v7.widget.LinearLayoutManager;

import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseUltimateRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.QueueSeeAdapter;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by Leo on 2016/4/28.
 */
@EActivity(R.layout.activity_queue_see)
public class QueueSeeActivity  extends BaseActivity {

    @ViewById
    MyTitleBar myTitleBar;

    @ViewById
    CustomUltimateRecyclerview ultimateRecyclerView;

    @Bean(QueueSeeAdapter.class)
    BaseUltimateRecyclerViewAdapter myAdapter;

    LinearLayoutManager linearLayoutManager;

    boolean isRefresh = false;

    @AfterViews
    void afterView() {
        AndroidTool.showLoadDialog(this);
        ultimateRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setAdapter(myAdapter);
        afterLoadMore("0");
    }

    void afterLoadMore(String str) {
        myAdapter.getMoreData(1, 10, isRefresh,str);
    }

    //上一次按钮点击事件
    @Click
    void btn_last(){
        afterLoadMore("1");
    }

    //下一次按钮点击事件
    @Click
    void btn_next(){
        afterLoadMore("2");
    }
}
