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
public class CooperationMerchantActivity extends BaseActivity {

    @ViewById
    MyTitleBar myTitleBar;

    @ViewById
    EditText edt_search;

    @ViewById
    LinearLayout ll_search;

    @ViewById
    CustomUltimateRecyclerview ultimateRecyclerView;

    @Bean(CooperationMerchantAdapter.class)
    BaseUltimateRecyclerViewAdapter myAdapter;

    @Bean
    OttoBus bus;

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

    LinearLayoutManager linearLayoutManager;

    MaterialHeader materialHeader;

    Paint paint = new Paint();

    int pageIndex = 1;

    boolean isRefresh = false;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        AndroidTool.showLoadDialog(this);
        city = "北京";
        bus.register(this);
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
                    AndroidTool.showToast(CooperationMerchantActivity.this, "没有更多的数据了！~");
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
                WebViewActivity_.intent(CooperationMerchantActivity.this).header("联盟商家详细").url(Constants.URL + "/DetailPage/CompanyDetail/" + obj.getCp_id()).start();
            }

            @Override
            public void onHeaderClick(RecyclerView.ViewHolder viewHolder, int position) {
            }
        });
    }


    void afterLoadMore() {
        myAdapter.getMoreData(pageIndex, 10, isRefresh, edt_search.getText().toString(), cityCode);
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
