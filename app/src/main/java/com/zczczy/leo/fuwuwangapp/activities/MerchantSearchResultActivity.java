package com.zczczy.leo.fuwuwangapp.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.divideritemdecoration.HorizontalDividerItemDecoration;
import com.squareup.otto.Subscribe;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseUltimateRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.CooperationMerchantAdapter;
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
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * @author Created by LuLeo on 2016/7/11.
 *         you can contact me at :361769045@qq.com
 * @since 2016/7/11.
 */
@EActivity(R.layout.activity_merchant_search_result)
public class MerchantSearchResultActivity extends BaseActivity {

    @ViewById
    MyTitleBar myTitleBar;

    @ViewById
    CustomUltimateRecyclerview ultimateRecyclerView;

    @Bean(CooperationMerchantAdapter.class)
    BaseUltimateRecyclerViewAdapter myAdapter;

    @Bean
    OttoBus bus;

    @Extra
    String searchContent;

    @ViewById
    TextView txt_title_search;

    @Bean
    MyErrorHandler myErrorHandler;

    @RestService
    MyDotNetRestClient myRestClient;

    LinearLayoutManager linearLayoutManager;

    MaterialHeader materialHeader;

    Paint paint = new Paint();

    //本地定位的城市名称
    String city;

    //服务器返回的城市编码
    String cityCode;

    //服务器返回的城市名称
    String cityName;

    int pageIndex = 1;

    boolean isRefresh = false;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        AndroidTool.showLoadDialog(this);
        bus.register(this);
        txt_title_search.setText(searchContent);
        ultimateRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setAdapter(myAdapter);
        ultimateRecyclerView.enableLoadmore();
        myAdapter.setCustomLoadMoreView(R.layout.custom_bottom_progressbar);
        getHttp();
        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
                if (myAdapter.getItems().size() >= myAdapter.getTotal()) {
                    AndroidTool.showToast(MerchantSearchResultActivity.this, "没有更多的数据了！~");
                    ultimateRecyclerView.disableLoadmore();
                    myAdapter.notifyItemRemoved(maxLastVisiblePosition);
                } else {
                    pageIndex++;
                    afterLoadMore();
                }
            }
        });
        ultimateRecyclerView.setCustomSwipeToRefresh();
        paint.setStrokeWidth(1);
        paint.setColor(line_color);
        ultimateRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).margin(35).paint(paint).build());
        refreshingMaterial();

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

    void refreshingMaterial() {
        materialHeader = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        materialHeader.setColorSchemeColors(colors);
        materialHeader.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        materialHeader.setPadding(0, 15, 0, 10);
        materialHeader.setPtrFrameLayout(ultimateRecyclerView.mPtrFrameLayout);
        ultimateRecyclerView.mPtrFrameLayout.autoRefresh(false);
        ultimateRecyclerView.mPtrFrameLayout.setHeaderView(materialHeader);
        ultimateRecyclerView.mPtrFrameLayout.addPtrUIHandler(materialHeader);
        ultimateRecyclerView.mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                isRefresh = true;
                pageIndex = 1;
                afterLoadMore();
            }
        });
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

    @Subscribe
    public void notifyUI(BaseModel bm) {
        if (isRefresh) {
            linearLayoutManager.scrollToPosition(0);
            ultimateRecyclerView.mPtrFrameLayout.refreshComplete();
            isRefresh = false;
            if (myAdapter.getItems().size() < myAdapter.getTotal()) {
                ultimateRecyclerView.reenableLoadmore(layoutInflater.inflate(R.layout.custom_bottom_progressbar, null));
            } else {
                ultimateRecyclerView.disableLoadmore();
            }
        } else if (pageIndex == 1) {
            linearLayoutManager.scrollToPosition(0);
        }
    }

    @Override
    public void finish() {
        bus.unregister(this);
        setResult(RESULT_OK);
        super.finish();
    }
}
