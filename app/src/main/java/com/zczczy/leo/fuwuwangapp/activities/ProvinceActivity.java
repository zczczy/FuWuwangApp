package com.zczczy.leo.fuwuwangapp.activities;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.ProvinceAdapter;
import com.zczczy.leo.fuwuwangapp.model.NewProvince;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2016/5/4.
 */
@EActivity(R.layout.activity_province)
public class ProvinceActivity extends BaseActivity {

    @ViewById
    RecyclerView recyclerView;

    @Bean(ProvinceAdapter.class)
    BaseRecyclerViewAdapter myAdapter;

    LinearLayoutManager linearLayoutManager;

    @AfterViews
    void afterView() {
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<NewProvince>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, NewProvince obj, int position) {
                CityActivity_.intent(ProvinceActivity.this).province(obj).startForResult(1000);
            }
        });
        myAdapter.getMoreData();
    }

    @OnActivityResult(1000)
    void onSelectPCA(int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
