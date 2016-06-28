package com.zczczy.leo.fuwuwangapp.activities;

import android.content.DialogInterface;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.divideritemdecoration.FlexibleDividerDecoration;
import com.marshalchen.ultimaterecyclerview.divideritemdecoration.HorizontalDividerItemDecoration;
import com.squareup.otto.Subscribe;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseUltimateRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.ExchangeLongBiAdapter;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.ExchangeLongBiModel;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.rest.MyRestClient;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.tools.Constants;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * @author Created by LuLeo on 2016/6/28.
 *         you can contact me at :361769045@qq.com
 * @since 2016/6/28.
 */
@EActivity(R.layout.activity_exchange_long_bi)
public class ExchangeLongBiActivity extends BaseActivity {

    @Extra
    String QueuesInRule, CpId;

    @ViewById
    MyTitleBar myTitleBar;

    @ViewById
    CustomUltimateRecyclerview ultimateRecyclerView;

    @Bean
    OttoBus bus;

    @Bean(ExchangeLongBiAdapter.class)
    BaseUltimateRecyclerViewAdapter myAdapter;

    @RestService
    MyDotNetRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    LinearLayoutManager linearLayoutManager;

    MaterialHeader materialHeader;

    int pageIndex = 1;

    boolean isRefresh;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
        myRestClient.setHeader("Token", pre.token().get());
    }

    @AfterViews
    void afterView() {
        AndroidTool.showLoadDialog(this);
        ultimateRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setAdapter(myAdapter);
        ultimateRecyclerView.enableLoadmore();
        myAdapter.setCustomLoadMoreView(R.layout.custom_bottom_progressbar);
        afterLoadMore();
        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
                if (myAdapter.getItems().size() >= myAdapter.getTotal()) {
                    ultimateRecyclerView.disableLoadmore();
                    myAdapter.notifyItemRemoved(itemsCount > 0 ? itemsCount - 1 : 0);
                } else {
                    pageIndex++;
                    afterLoadMore();
                }
            }
        });
        ultimateRecyclerView.setCustomSwipeToRefresh();
        Paint paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(line_color);
        ultimateRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).margin(35).visibilityProvider(new FlexibleDividerDecoration.VisibilityProvider() {
            @Override
            public boolean shouldHideDivider(int position, RecyclerView parent) {
                return false;
            }
        }).paint(paint).build());
        refreshingMaterial();
        myAdapter.setOnItemClickListener(new BaseUltimateRecyclerViewAdapter.OnItemClickListener<ExchangeLongBiModel>() {
            @Override
            public void onItemClick(final RecyclerView.ViewHolder viewHolder, final ExchangeLongBiModel obj, final int position) {
                AlertDialog.Builder adb = new AlertDialog.Builder(ExchangeLongBiActivity.this);
                adb.setTitle("提示").setMessage("确定兑换吗？").setPositiveButton("兑换", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AndroidTool.showLoadDialog(ExchangeLongBiActivity.this);
                        dhlb(obj, position);
                    }
                }).setNegativeButton("取消", null).setIcon(R.mipmap.logo).create().show();
            }

            @Override
            public void onHeaderClick(RecyclerView.ViewHolder viewHolder, int position) {

            }
        });
    }

    @Background
    void dhlb(ExchangeLongBiModel model, int position) {
        afterDhlb(myRestClient.Dhlb(model.qi_loginKey), position);
    }

    @UiThread
    void afterDhlb(BaseModel result, int position) {
        AndroidTool.dismissLoadDialog();
        if (result == null) {
            AndroidTool.showToast(this, no_net);
        } else if (!result.Successful) {
            AndroidTool.showToast(this, result.Error);
        } else {
            myAdapter.remove(position);
        }
    }


    void afterLoadMore() {
        myAdapter.getMoreData(pageIndex, Constants.PAGE_COUNT, isRefresh, QueuesInRule, CpId);
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
            if (myAdapter.getTotal() > 0 && myAdapter.getItems().size() < myAdapter.getTotal()) {
                ultimateRecyclerView.reenableLoadmore(layoutInflater.inflate(R.layout.custom_bottom_progressbar, null));
            } else {
                ultimateRecyclerView.disableLoadmore();
            }
        } else if (pageIndex == 1) {
            linearLayoutManager.scrollToPosition(0);
        }
    }

    @Override
    protected void onPause() {
        bus.unregister(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        bus.register(this);
        super.onResume();
    }

}
