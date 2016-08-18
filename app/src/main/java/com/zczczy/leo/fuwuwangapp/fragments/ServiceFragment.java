package com.zczczy.leo.fuwuwangapp.fragments;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.uiUtils.BasicGridLayoutManager;
import com.squareup.otto.Subscribe;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.activities.CartActivity_;
import com.zczczy.leo.fuwuwangapp.activities.CityChooseActivity_;
import com.zczczy.leo.fuwuwangapp.activities.GoodsDetailActivity_;
import com.zczczy.leo.fuwuwangapp.activities.LoginActivity_;
import com.zczczy.leo.fuwuwangapp.activities.SearchActivity_;
import com.zczczy.leo.fuwuwangapp.adapters.BaseUltimateRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.RecommendedGoodsAdapter;
import com.zczczy.leo.fuwuwangapp.items.ServiceHeaderItemView_;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.Goods;
import com.zczczy.leo.fuwuwangapp.model.NewArea;
import com.zczczy.leo.fuwuwangapp.model.PagerResult;
import com.zczczy.leo.fuwuwangapp.model.StreetInfo;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.util.StringUtils;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Created by Leo on 2016/4/27.
 */
@EFragment(R.layout.fragment_service)
public class ServiceFragment extends BaseFragment {

    @ViewById
    MyTitleBar myTitleBar;

    @ViewById
    CustomUltimateRecyclerview ultimateRecyclerView;

    @Bean(RecommendedGoodsAdapter.class)
    BaseUltimateRecyclerViewAdapter myAdapter;

    @Bean
    MyErrorHandler myErrorHandler;

    @RestService
    MyDotNetRestClient myRestClient;

    @Bean
    OttoBus bus;

    @ViewById
    TextView txt_title_search;

    @StringRes
    String search_service_hint;

    String cityId;

    BasicGridLayoutManager gridLayoutManager;

    MaterialHeader materialHeader;

    int pageIndex = 1;

    boolean isRefresh = false;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {

        txt_title_search.setHint(search_service_hint);
        ultimateRecyclerView.setHasFixedSize(true);
        cityId = pre.cityId().get();
        gridLayoutManager = new BasicGridLayoutManager(getActivity(), 2, myAdapter);
        ultimateRecyclerView.setLayoutManager(gridLayoutManager);
        ultimateRecyclerView.setAdapter(myAdapter);
        ultimateRecyclerView.enableLoadmore();
        myAdapter.setCustomLoadMoreView(R.layout.custom_bottom_progressbar);
        afterLoadMore();
        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
                if (myAdapter.getItems().size() >= myAdapter.getTotal()) {
//                    AndroidTool.showToast(ServiceFragment.this, "没有更多的数据了！~");
                    ultimateRecyclerView.disableLoadmore();
                    myAdapter.notifyItemRemoved(itemsCount > 0 ? itemsCount - 1 : 0);
                } else {
                    pageIndex++;
                    afterLoadMore();
                }
            }
        });

        myTitleBar.setCustomViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity_.intent(ServiceFragment.this).isService(true).start();
            }
        });
        ultimateRecyclerView.setNormalHeader(ServiceHeaderItemView_.build(getActivity()));
        ultimateRecyclerView.setCustomSwipeToRefresh();
//        paint.setStrokeWidth(1);
//        paint.setColor(line_color);
//        ultimateRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).margin(35).visibilityProvider(new FlexibleDividerDecoration.VisibilityProvider() {
//            @Override
//            public boolean shouldHideDivider(int position, RecyclerView parent) {
//                return position == 0;
//            }
//        }).paint(paint).build());
        refreshingMaterial();
        setListener();
    }

    void setListener() {
        myTitleBar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkUserIsLogin()) {
                    CartActivity_.intent(ServiceFragment.this).start();
                } else {
                    LoginActivity_.intent(ServiceFragment.this).start();
                }
            }
        });
        myTitleBar.setLeftTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityChooseActivity_.intent(ServiceFragment.this).flagType("3").startForResult(1000);
            }
        });

        myAdapter.setOnItemClickListener(new BaseUltimateRecyclerViewAdapter.OnItemClickListener<Goods>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, Goods obj, int position) {
                GoodsDetailActivity_.intent(ServiceFragment.this).goodsId(obj.GoodsInfoId).start();
            }

            @Override
            public void onHeaderClick(RecyclerView.ViewHolder viewHolder, int position) {

            }
        });
    }


    void afterLoadMore() {
        myAdapter.getMoreData(pageIndex, 10, isRefresh, 2, cityId);
    }

    void refreshingMaterial() {
        materialHeader = new MaterialHeader(getActivity());
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
    void changed(int resultCode, @OnActivityResult.Extra String ncity, @OnActivityResult.Extra String ncitycode) {
        if (resultCode == Activity.RESULT_OK) {
            pre.locationAddress().put(ncity);
            myTitleBar.setLeftText(ncity);
            pre.cityId().put(ncitycode);
            cityId = ncitycode;
            isRefresh = true;
            pageIndex = 1;
            afterLoadMore();
            getArea(ncitycode);
        }
    }


    /**
     * 查询区域（包括商圈）根据城市id （服务页面用的）
     *
     * @param cityId
     */
    @Background
    void getArea(String cityId) {
        BaseModelJson<List<NewArea>> bmj = myRestClient.getAreaByCity(cityId);
        if (bmj != null && bmj.Successful) {
            app.setRegionList(bmj.Data);
            for (NewArea newRegion : app.getRegionList()) {
                if (newRegion.listStreet != null) {
                    StreetInfo newStreet = new StreetInfo();
                    newStreet.StreetInfoId = Integer.valueOf(newRegion.CityId);
                    newStreet.StreetName = "全部";
                    newStreet.AreaId = Integer.valueOf(newRegion.AreaId);
                    newRegion.listStreet.add(0, newStreet);
                }
            }
        }
    }

    @Subscribe
    public void notifyUI(BaseModelJson<PagerResult<Goods>> bm) {
        if (isRefresh) {
            gridLayoutManager.scrollToPosition(0);
            ultimateRecyclerView.mPtrFrameLayout.refreshComplete();
            isRefresh = false;
            if (myAdapter.getItems().size() < myAdapter.getTotal()) {
                ultimateRecyclerView.reenableLoadmore(layoutInflater.inflate(R.layout.custom_bottom_progressbar, null));
            } else {
                ultimateRecyclerView.disableLoadmore();
            }
        } else if (pageIndex == 1) {
            gridLayoutManager.scrollToPosition(0);
        }
        if (bm != null && bm.Successful && bm.Data != null && bm.Data.ListData.size() == 0 && !StringUtils.isEmpty(cityId)) {
            cityId = "";
            isRefresh = true;
            pageIndex = 1;
//            myTitleBar.setLeftText("全国");
            afterLoadMore();
        } else {
            myTitleBar.setLeftText(pre.locationAddress().get());
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            bus.unregister(this);
        } else {
            bus.register(this);
        }
    }

}
