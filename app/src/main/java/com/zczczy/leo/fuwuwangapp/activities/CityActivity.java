package com.zczczy.leo.fuwuwangapp.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.marshalchen.ultimaterecyclerview.divideritemdecoration.HorizontalDividerItemDecoration;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.AreaAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.BaseRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.CityAdapter;
import com.zczczy.leo.fuwuwangapp.model.NewCity;
import com.zczczy.leo.fuwuwangapp.model.NewProvince;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2016/5/4.
 */
@EActivity(R.layout.activity_province)
public class CityActivity extends BaseRecyclerViewActivity<NewCity> {

    @Extra
    NewProvince province;

    @Bean
    void setAdapter(CityAdapter myAdapter) {
        this.myAdapter = myAdapter;
    }


    @AfterViews
    void afterView() {
        AndroidTool.showLoadDialog(this);
        myTitleBar.setTitle("城市");
        myAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<NewCity>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, NewCity obj, int position) {
                AreaActivity_.intent(CityActivity.this).province(province).city(obj).startForResult(1000);
            }
        });
        myAdapter.getMoreData(province.ProvinceId);
    }

    @OnActivityResult(1000)
    void onSelectPCA(int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK, intent);
            finish();
        }
    }

}
