package com.zczczy.leo.fuwuwangapp.activities;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.AreaAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.BaseRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.model.NewArea;
import com.zczczy.leo.fuwuwangapp.model.NewCity;
import com.zczczy.leo.fuwuwangapp.model.NewProvince;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2016/5/4.
 */
@EActivity(R.layout.activity_province)
public class AreaActivity extends BaseActivity {

    @ViewById
    RecyclerView recyclerView;

    @Bean(AreaAdapter.class)
    BaseRecyclerViewAdapter myAdapter;

    @Extra
    NewProvince province;

    @Extra
    NewCity city;

    LinearLayoutManager linearLayoutManager;

    @AfterViews
    void afterView() {
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<NewArea>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, NewArea obj, int position) {
                Intent intent = new Intent();
                intent.putExtra("areaId", obj.AreaId);
                intent.putExtra("pca", province.ProvinceName + city.CityName + obj.AreaName);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        myAdapter.getMoreData(city.ProvinceId);
    }
}
