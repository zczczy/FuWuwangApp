package com.zczczy.leo.fuwuwangapp.activities;

import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.marshalchen.ultimaterecyclerview.divideritemdecoration.HorizontalDividerItemDecoration;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.LogisticsInfoAdapter;
import com.zczczy.leo.fuwuwangapp.tools.DisplayUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Created by zczczy on 2016/5/5.
 * 物流信息
 */
@EActivity(R.layout.activity_logistics_info)
public class LogisticsInfoActivity extends BaseActivity {

    @Bean(LogisticsInfoAdapter.class)
    LogisticsInfoAdapter myAdapter;

    @ViewById
    RecyclerView recyclerView;

    LinearLayoutManager linearLayoutManager;

    Paint paint = new Paint();

    @Extra
    String MOrderId;

    @AfterViews
    void afterView() {
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
        //"fab253f8f1534a8f819eb0730a091805"
        myAdapter.getMoreData(MOrderId);
        paint.setStrokeWidth(1);
        paint.setColor(line_color);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).margin(DisplayUtil.dip2px(this, 60), DisplayUtil.dip2px(this, 10)).paint(paint).build());
    }

}
