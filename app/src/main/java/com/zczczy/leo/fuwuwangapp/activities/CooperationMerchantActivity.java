package com.zczczy.leo.fuwuwangapp.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.divideritemdecoration.HorizontalDividerItemDecoration;
import com.squareup.otto.Subscribe;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseUltimateRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.CooperationMerchantAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.GoodsAdapters;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.CooperationMerchant;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.tools.Constants;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Created by Leo on 2016/4/28.
 */
@EActivity(R.layout.activity_cooperation_merchant)
public class CooperationMerchantActivity extends BaseUltimateRecyclerViewActivity<CooperationMerchant> {


    @ViewById
    EditText edt_search;

    @ViewById
    LinearLayout ll_search;

    @Bean
    MyErrorHandler myErrorHandler;

    @RestService
    MyDotNetRestClient myRestClient;

    //本地定位的城市名称
    String city;

    //服务器返回的城市编码
    String cityCode;

    //服务器返回的城市名称
    String cityName;

    @Bean
    void setAdapter(CooperationMerchantAdapter myAdapter) {
        this.myAdapter = myAdapter;
    }

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
        isLoadData = false;
    }

    @AfterViews
    void afterView() {
        city = "北京";
        getHttp();
        setListener();
    }

    void setListener() {

        myTitleBar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityChooseActivity_.intent(CooperationMerchantActivity.this).flagType("1").startForResult(1000);
            }
        });

        myAdapter.setOnItemClickListener(new BaseUltimateRecyclerViewAdapter.OnItemClickListener<CooperationMerchant>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, CooperationMerchant obj, int position) {
                CommonWebViewActivity_.intent(CooperationMerchantActivity.this).title("联盟商家详细").methodName(Constants.DETAIL_PAGE_ACTION + Constants.COMPANY_DETAIL_METHOD + obj.cp_id).start();
            }

            @Override
            public void onHeaderClick(RecyclerView.ViewHolder viewHolder, int position) {
            }
        });
    }


    void afterLoadMore() {
        myAdapter.getMoreData(pageIndex, 10, isRefresh, "0", edt_search.getText().toString(), cityCode);
    }

    /**
     * 模糊搜索功能
     *
     * @param actionId
     */
    @EditorAction
    void edt_search(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            /*隐藏软键盘*/
            if (inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
            }
            if (isNetworkAvailable(this)) {
                isRefresh = true;
                pageIndex = 1;
                afterLoadMore();
            } else {
                AndroidTool.showToast(this, no_net);
            }
        }
    }

    @OnActivityResult(1000)
    void getBillId(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            city = bundle.getString("ncity");
            cityCode = bundle.getString("ncitycode");
            myTitleBar.setRightText(city);
            isRefresh = true;
            pageIndex = 1;
            afterLoadMore();
        }
    }

    //根据城市名称模糊搜索城市code
    @Background
    void getHttp() {
        BaseModelJson<String> bmj = myRestClient.GetCityCodeByName(city);
        show(bmj);
    }

    @UiThread
    void show(BaseModelJson<String> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj != null) {
            if (bmj.Successful) {
                String[] l = bmj.Data.split(",");
                cityCode = l[0];
                cityName = l[1];
                myTitleBar.setRightText(cityName);
                isRefresh = true;
                pageIndex = 1;
                afterLoadMore();
            }
        }
    }

    @Override
    public void finish() {
        bus.unregister(this);
        super.finish();
    }
}
