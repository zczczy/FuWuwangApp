package com.zczczy.leo.fuwuwangapp.activities;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseUltimateRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.UnionMemberAdapter;
import com.zczczy.leo.fuwuwangapp.model.Purse;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

/**
 * Created by Leo on 2016/4/29.
 */
@EActivity(R.layout.activity_union_member)
public class UnionMemberActivity extends BaseUltimateRecyclerViewActivity<Purse> {

    @StringRes
    String txt_unionmember_title;

    @Extra
    String username;

    @Extra
    int userId;

    @ViewById
    TextView empty_view;

    @Bean
    void setAdapter(UnionMemberAdapter myAdapter) {
        this.myAdapter = myAdapter;
    }

    @AfterViews
    void afterView() {
        empty_view.setText("还没有联盟会员");
        myTitleBar.setTitle(String.format(txt_unionmember_title, username));
        setListener();
    }

    void setListener() {
        myAdapter.setOnItemClickListener(new BaseUltimateRecyclerViewAdapter.OnItemClickListener<Purse>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, Purse obj, int position) {
                if (Integer.parseInt(obj.childCount) > 0) {
                    UnionMemberActivity_.intent(UnionMemberActivity.this).userId(obj.UserId).username(obj.m_realrname).start();
                }
            }

            @Override
            public void onHeaderClick(RecyclerView.ViewHolder viewHolder, int position) {
            }
        });
    }

    void afterLoadMore() {
        myAdapter.getMoreData(pageIndex, 10, isRefresh, userId);
    }
}
