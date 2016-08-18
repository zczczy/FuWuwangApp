package com.zczczy.leo.fuwuwangapp.fragments;

import android.support.v7.widget.RecyclerView;

import com.marshalchen.ultimaterecyclerview.divideritemdecoration.FlexibleDividerDecoration;
import com.marshalchen.ultimaterecyclerview.divideritemdecoration.HorizontalDividerItemDecoration;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.activities.GoodsDetailActivity_;
import com.zczczy.leo.fuwuwangapp.adapters.BaseUltimateRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.RecommendedGoodsAdapter;
import com.zczczy.leo.fuwuwangapp.items.StoreInformationHeaderItemView;
import com.zczczy.leo.fuwuwangapp.items.StoreInformationHeaderItemView_;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.Goods;
import com.zczczy.leo.fuwuwangapp.model.StoreDetailModel;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.tools.Constants;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.rest.spring.annotations.RestService;

/**
 * @author Created by LuLeo on 2016/8/18.
 *         you can contact me at :361769045@qq.com
 * @since 2016/8/18.
 */
@EFragment(R.layout.no_title_ultimate)
public class StoreHomeFragment extends BaseUltimateRecyclerViewFragment<Goods> {

    StoreInformationHeaderItemView storeInformationHeaderItemView;

    @RestService
    MyDotNetRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    @FragmentArg
    String storeId;

    @FragmentArg
    StoreDetailModel storeDetailModel;

    @Bean
    void setAdapter(RecommendedGoodsAdapter myAdapter) {
        this.myAdapter = myAdapter;
    }

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        storeInformationHeaderItemView = StoreInformationHeaderItemView_.build(getActivity());
        ultimateRecyclerView.setNormalHeader(storeInformationHeaderItemView);
        ultimateRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).margin(35).visibilityProvider(new FlexibleDividerDecoration.VisibilityProvider() {
            @Override
            public boolean shouldHideDivider(int position, RecyclerView parent) {
                return position == 0;
            }
        }).paint(paint).build());

        if (storeDetailModel == null) {
            getStoreInfo();
        } else {
            storeInformationHeaderItemView.init(storeDetailModel);
        }
        myAdapter.setOnItemClickListener(new BaseUltimateRecyclerViewAdapter.OnItemClickListener<Goods>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, Goods obj, int position) {
                GoodsDetailActivity_.intent(StoreHomeFragment.this).goodsId(obj.GoodsInfoId).start();
            }

            @Override
            public void onHeaderClick(RecyclerView.ViewHolder viewHolder, int position) {
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            storeInformationHeaderItemView.stopAutoCycle();
        } else {
            storeInformationHeaderItemView.startAutoCycle();
        }
    }


    @Background
    void getStoreInfo() {
        afterGetStoreInfo(myRestClient.getStoreDetailById(storeId));
    }

    @UiThread
    void afterGetStoreInfo(BaseModelJson<StoreDetailModel> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (!bmj.Successful) {
            AndroidTool.showToast(this, bmj.Error);
        } else {
            if (Constants.STORE_STATE_ACTIVITY.equals(bmj.Data.StoreStatus)) {
                storeInformationHeaderItemView.init(bmj.Data);
            } else {
                AndroidTool.showToast(this, "该店铺已锁定");
            }
        }
    }


    @Override
    void afterLoadMore() {
        myAdapter.getMoreData(pageIndex, Constants.PAGE_COUNT, isRefresh, 5, storeId);
    }
}
