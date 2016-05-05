package com.zczczy.leo.fuwuwangapp.activities;

import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;
import com.marshalchen.ultimaterecyclerview.divideritemdecoration.FlexibleDividerDecoration;
import com.marshalchen.ultimaterecyclerview.divideritemdecoration.HorizontalDividerItemDecoration;
import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseUltimateRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.GoodsAdapters;
import com.zczczy.leo.fuwuwangapp.items.StoreInformationHeaderItemView_;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.StoreDetailModel;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

/**
 * Created by Leo on 2016/5/5.
 */
@EActivity(R.layout.activity_store_information)
public class StoreInformationActivity extends BaseActivity {

    @ViewById
    CustomUltimateRecyclerview ultimateRecyclerView;

    @Bean(GoodsAdapters.class)
    BaseUltimateRecyclerViewAdapter myAdapter;

    @RestService
    MyDotNetRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    @Extra
    String storeId;

    LinearLayoutManager linearLayoutManager;

    int pageIndex = 1;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }


    @AfterViews
    void afterView() {
        ultimateRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setAdapter(myAdapter);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setNormalHeader(StoreInformationHeaderItemView_.build(this));
        Paint paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(line_color);
        paint.setAntiAlias(true);
        ultimateRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).margin(35).visibilityProvider(new FlexibleDividerDecoration.VisibilityProvider() {
            @Override
            public boolean shouldHideDivider(int position, RecyclerView parent) {
                return position == 0;
            }
        }).paint(paint).build());
        getStoreInfo();
    }


    @Background
    void getStoreInfo() {
        afterGetStoreInfo(myRestClient.getStoreDetailById(storeId));
    }

    @UiThread
    void afterGetStoreInfo(BaseModelJson<StoreDetailModel> bmj) {
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (!bmj.Successful) {
            AndroidTool.showToast(this, bmj.Error);
        } else {
            myAdapter.getMoreData(pageIndex, MyApplication.PAGECOUNT, false, 0, bmj.Data.GoodsList);
        }

    }

}
