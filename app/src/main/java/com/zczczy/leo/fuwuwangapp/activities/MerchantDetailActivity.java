package com.zczczy.leo.fuwuwangapp.activities;

import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;
import com.marshalchen.ultimaterecyclerview.uiUtils.BasicGridLayoutManager;
import com.squareup.otto.Subscribe;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseUltimateRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.RecommendedGoodsAdapter;
import com.zczczy.leo.fuwuwangapp.items.MerchantDetailHeaderView;
import com.zczczy.leo.fuwuwangapp.items.MerchantDetailHeaderView_;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.CooperationMerchant;
import com.zczczy.leo.fuwuwangapp.model.RebuiltRecommendedGoods;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
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

import java.util.List;

import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * @author Created by LuLeo on 2016/7/13.
 *         you can contact me at :361769045@qq.com
 * @since 2016/7/13.
 */
@EActivity(R.layout.activity_merchant_detail)
public class MerchantDetailActivity extends BaseActivity {

    @RestService
    MyDotNetRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    @ViewById
    MyTitleBar myTitleBar;

    @Bean(RecommendedGoodsAdapter.class)
    BaseUltimateRecyclerViewAdapter myAdapter;

    @ViewById
    CustomUltimateRecyclerview ultimateRecyclerView;

    @Extra
    String companyId;

    BasicGridLayoutManager gridLayoutManager;

    MaterialHeader materialHeader;

    MerchantDetailHeaderView merchantDetailHeaderView;

    int pageIndex = 1;

    boolean isRefresh = false;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        setListener();
        AndroidTool.showLoadDialog(this);
        ultimateRecyclerView.setHasFixedSize(true);
        gridLayoutManager = new BasicGridLayoutManager(this, 2, OrientationHelper.VERTICAL, false, myAdapter);
        ultimateRecyclerView.setLayoutManager(gridLayoutManager);
        ultimateRecyclerView.setAdapter(myAdapter);
        merchantDetailHeaderView = MerchantDetailHeaderView_.build(this);
        ultimateRecyclerView.setNormalHeader(merchantDetailHeaderView);
        getCompanyDetailById();
    }

    @Background
    void getCompanyDetailById() {
        afterGetCompanyDetailById(myRestClient.getCompanyDetailById(companyId));
    }

    @UiThread
    void afterGetCompanyDetailById(BaseModelJson<CooperationMerchant> result) {
        AndroidTool.dismissLoadDialog();
        if (result == null) {
            AndroidTool.showToast(this, no_net);
        } else if (!result.Successful) {
            AndroidTool.showToast(this, result.Error);
        } else {
            merchantDetailHeaderView.init(result.Data);
            afterLoadMore(result.Data.GoodsList);
        }
    }


    void afterLoadMore(List<RebuiltRecommendedGoods> b) {
        myAdapter.getMoreData(pageIndex, 10, isRefresh, 3, b);
    }

    void setListener() {
        myAdapter.setOnItemClickListener(new BaseUltimateRecyclerViewAdapter.OnItemClickListener<RebuiltRecommendedGoods>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, RebuiltRecommendedGoods obj, int position) {
                GoodsDetailActivity_.intent(MerchantDetailActivity.this).goodsId(obj.GoodsInfoId).start();
            }

            @Override
            public void onHeaderClick(RecyclerView.ViewHolder viewHolder, int position) {
            }
        });
    }
}
