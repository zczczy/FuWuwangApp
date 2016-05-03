package com.zczczy.leo.fuwuwangapp.fragments;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;
import com.marshalchen.ultimaterecyclerview.ObservableScrollState;
import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.divideritemdecoration.FlexibleDividerDecoration;
import com.marshalchen.ultimaterecyclerview.divideritemdecoration.HorizontalDividerItemDecoration;
import com.marshalchen.ultimaterecyclerview.uiUtils.BasicGridLayoutManager;
import com.squareup.otto.Subscribe;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.activities.CartActivity_;
import com.zczczy.leo.fuwuwangapp.activities.GoodsDetailInfoActivity_;
import com.zczczy.leo.fuwuwangapp.adapters.BaseUltimateRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.RecommendedGoodsAdapter;
import com.zczczy.leo.fuwuwangapp.items.BaseUltimateViewHolder;
import com.zczczy.leo.fuwuwangapp.items.HomeAdvertisementItemView_;
import com.zczczy.leo.fuwuwangapp.items.ItemView;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.AdvertModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.RebuiltRecommendedGoods;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyBackgroundTask;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Created by Leo on 2016/4/27.
 */
@EFragment(R.layout.fragment_home)
public class HomeFragment extends BaseFragment {

    @ViewById
    CustomUltimateRecyclerview ultimateRecyclerView;

    @Bean(RecommendedGoodsAdapter.class)
    BaseUltimateRecyclerViewAdapter myAdapter;

    @ViewById
    MyTitleBar myTitleBar;


    @Bean
    MyErrorHandler myErrorHandler;

    @Bean
    MyBackgroundTask myBackgroundTask;

    @Pref
    MyPrefs_ pre;

    @Bean
    OttoBus bus;

    @ColorRes
    int white, home_search_text_scrolled;

    Drawable title_search, title_search_scrolled;

    Paint paint = new Paint();

    View view;

    TextView textView;

    BasicGridLayoutManager gridLayoutManager;

    MaterialHeader materialHeader;

    int pageIndex = 1;

    boolean isRefresh = false;

    ItemView<List<AdvertModel>> itemView;

    @AfterInject
    void afterInject() {
    }

    @AfterViews
    void afterView() {
        //初始化控件
        myTitleBar.getBackground().setAlpha(0);
        view = myTitleBar.getmCustomView();
        textView = (TextView) view.findViewById(R.id.txt_title_search);
        title_search = getResources().getDrawable(R.drawable.title_search);
        title_search_scrolled = getResources().getDrawable(R.drawable.title_search_scrolled);
        title_search.setBounds(0, 0, title_search.getMinimumWidth(), title_search.getMinimumHeight());
        title_search_scrolled.setBounds(0, 0, title_search_scrolled.getMinimumWidth(), title_search_scrolled.getMinimumHeight());

        bus.register(this);
        setListener();
        ultimateRecyclerView.setHasFixedSize(true);
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
                    AndroidTool.showToast(HomeFragment.this, "没有更多的数据了！~");
                    ultimateRecyclerView.disableLoadmore();
                } else {
                    pageIndex++;
                    afterLoadMore();
                }
            }
        });
        ultimateRecyclerView.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
                if (scrollY < 0) {
                    myTitleBar.getBackground().setAlpha(0);
                } else if (scrollY < 500) {
                    int progress = (int) (new Float(scrollY) / new Float(2));//255
                    myTitleBar.getBackground().setAlpha(progress);
                    if (scrollY < 250) {
                        myTitleBar.setNavigationIcon(R.drawable.title_category);
                        myTitleBar.setRightButtonIcon(R.drawable.title_cart);
                        view.setBackgroundResource(R.drawable.title_selector);
                        textView.setHintTextColor(white);
                        textView.setCompoundDrawables(title_search, null, null, null);
                    } else {
                        myTitleBar.setNavigationIcon(R.drawable.title_category_scrolled);
                        myTitleBar.setRightButtonIcon(R.drawable.title_cart_scrolled);
                        view.setBackgroundResource(R.drawable.title_scrolled_selector);
                        textView.setHintTextColor(home_search_text_scrolled);
                        textView.setCompoundDrawables(title_search_scrolled, null, null, null);
                    }
                } else {
                    myTitleBar.getBackground().setAlpha(250);
                }
            }

            @Override
            public void onDownMotionEvent() {
            }

            @Override
            public void onUpOrCancelMotionEvent(ObservableScrollState observableScrollState) {

            }
        });
        ultimateRecyclerView.setNormalHeader(HomeAdvertisementItemView_.build(getActivity()));
        ultimateRecyclerView.setCustomSwipeToRefresh();
        paint.setStrokeWidth(1);
        paint.setColor(line_color);
        ultimateRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).margin(35).visibilityProvider(new FlexibleDividerDecoration.VisibilityProvider() {
            @Override
            public boolean shouldHideDivider(int position, RecyclerView parent) {
                return position == 0;
            }
        }).paint(paint).build());
        refreshingMaterial();
    }

    void setListener() {
        myAdapter.setBindHeaderViewHolder(new BaseUltimateRecyclerViewAdapter.BindHeaderViewHolder() {
            @Override
            public void onBindHeaderViewHolder(BaseUltimateViewHolder viewHolder) {
                UltimateRecyclerView.CustomRelativeWrapper customRelativeWrapper = (UltimateRecyclerView.CustomRelativeWrapper) viewHolder.itemView;
                itemView = (ItemView<List<AdvertModel>>) (customRelativeWrapper.getChildAt(0));
                itemView.init(myApplication.getAdvertModelList());
            }
        });
        myAdapter.setOnItemClickListener(new BaseUltimateRecyclerViewAdapter.OnItemClickListener<RebuiltRecommendedGoods>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, RebuiltRecommendedGoods obj, int position) {
//                NewProductDetailActivity_.intent(NewHomeFragment.this).goods(obj).start();
                GoodsDetailInfoActivity_.intent(HomeFragment.this).goodsId(obj.GoodsInfoId).start();
            }

            @Override
            public void onHeaderClick(RecyclerView.ViewHolder viewHolder, int position) {
            }
        });
        myTitleBar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartActivity_.intent(HomeFragment.this).start();
            }
        });
    }

    void afterLoadMore() {
        myAdapter.getMoreData(pageIndex, 10, isRefresh);
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
                myBackgroundTask.getHomeBanner();
                myBackgroundTask.getAdvertByKbn();
                afterLoadMore();
            }
        });
    }

    @Subscribe
    public void notifyUI(BaseModel bm) {
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
