package com.zczczy.leo.fuwuwangapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseUltimateRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.CooperationMerchantAdapter;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.CooperationMerchant;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.Constants;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

/**
 * @author Created by LuLeo on 2016/7/11.
 *         you can contact me at :361769045@qq.com
 * @since 2016/7/11.
 */
@EActivity(R.layout.activity_merchant_search_result)
public class MerchantSearchResultActivity extends BaseUltimateRecyclerViewActivity<CooperationMerchant> {

    @Extra
    String searchContent;

    @ViewById
    TextView txt_title_search;

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

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
        isLoadData = false;
    }


    @Bean
    void setAdapter(CooperationMerchantAdapter myAdapter) {
        this.myAdapter = myAdapter;
    }

    @AfterViews
    void afterView() {
        getHttp();
        myTitleBar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityChooseActivity_.intent(MerchantSearchResultActivity.this).flagType("1").startForResult(1000);
            }
        });

        myAdapter.setOnItemClickListener(new BaseUltimateRecyclerViewAdapter.OnItemClickListener<CooperationMerchant>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, CooperationMerchant obj, int position) {
                if (obj.SellerInfoId == 0) {
                    CommonWebViewActivity_.intent(MerchantSearchResultActivity.this).title("联盟商家详细").methodName(Constants.DETAIL_PAGE_ACTION + Constants.COMPANY_DETAIL_METHOD + obj.cp_id).start();
                } else {
                    MerchantDetailActivity_.intent(MerchantSearchResultActivity.this).companyId(String.valueOf(obj.cp_id)).start();
                }
            }

            @Override
            public void onHeaderClick(RecyclerView.ViewHolder viewHolder, int position) {
            }
        });

        myTitleBar.setCustomViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(1000);
                finish();
            }
        });

        myTitleBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });

    }

    void afterLoadMore() {
        myAdapter.getMoreData(pageIndex, 10, isRefresh, "1", searchContent, cityCode);
    }

    //根据城市名称模糊搜索城市code
    @Background
    void getHttp() {
        BaseModelJson<String> bmj = myRestClient.GetCityCodeByName(pre.address().get());
        show(bmj);
    }

    @UiThread
    void show(BaseModelJson<String> bmj) {
        if (bmj != null) {
            if (bmj.Successful) {
                String[] l = bmj.Data.split(",");
                cityCode = l[0];
                cityName = l[1];
                pre.address().put(cityName);
                myTitleBar.setRightText(cityName);
                isRefresh = true;
                pageIndex = 1;
                afterLoadMore();
            }
        }
    }


    @OnActivityResult(1000)
    void getBillId(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            city = bundle.getString("ncity");
            cityCode = bundle.getString("ncitycode");
            pre.address().put(city);
            myTitleBar.setRightText(city);
            isRefresh = true;
            pageIndex = 1;
            afterLoadMore();
        }
    }

    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    public void finish() {
        bus.unregister(this);
        super.finish();
    }
}
