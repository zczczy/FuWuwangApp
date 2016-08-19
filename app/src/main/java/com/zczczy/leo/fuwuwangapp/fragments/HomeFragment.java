package com.zczczy.leo.fuwuwangapp.fragments;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;
import com.marshalchen.ultimaterecyclerview.ObservableScrollState;
import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.uiUtils.BasicGridLayoutManager;
import com.squareup.otto.Subscribe;
import com.uuch.adlibrary.AdConstant;
import com.uuch.adlibrary.AdManager;
import com.uuch.adlibrary.bean.AdInfo;
import com.uuch.adlibrary.transformer.DepthPageTransformer;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.activities.CartActivity_;
import com.zczczy.leo.fuwuwangapp.activities.CategoryActivity_;
import com.zczczy.leo.fuwuwangapp.activities.CommonSearchResultActivity_;
import com.zczczy.leo.fuwuwangapp.activities.CommonWebViewActivity_;
import com.zczczy.leo.fuwuwangapp.activities.GoodsDetailActivity_;
import com.zczczy.leo.fuwuwangapp.activities.LoginActivity_;
import com.zczczy.leo.fuwuwangapp.activities.MainActivity;
import com.zczczy.leo.fuwuwangapp.activities.MerchantSearchResultActivity_;
import com.zczczy.leo.fuwuwangapp.activities.SearchActivity_;
import com.zczczy.leo.fuwuwangapp.activities.StoreInformationActivity_;
import com.zczczy.leo.fuwuwangapp.adapters.BaseUltimateRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.RecommendedGoodsAdapter;
import com.zczczy.leo.fuwuwangapp.items.BaseUltimateViewHolder;
import com.zczczy.leo.fuwuwangapp.items.HomeAdvertisementItemView;
import com.zczczy.leo.fuwuwangapp.items.HomeAdvertisementItemView_;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.AdvertModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.Goods;
import com.zczczy.leo.fuwuwangapp.rest.MyBackgroundTask;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;

import java.util.ArrayList;
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
    MyBackgroundTask myBackgroundTask;

    @Bean
    OttoBus bus;

    @ColorRes
    int white_color, home_search_text_scrolled;

    Drawable title_search, title_search_scrolled;

    View view;

    TextView textView;

    BasicGridLayoutManager gridLayoutManager;

    MaterialHeader materialHeader;

    int pageIndex = 1;

    boolean isRefresh = false;

    HomeAdvertisementItemView itemView;

    int progress;

    AdManager adManager;

    @AfterInject
    void afterInject() {

    }

    @AfterViews
    void afterView() {
//        AndroidTool.showLoadDialog(this);
        //初始化控件
        myTitleBar.getBackground().mutate().setAlpha(0);
        view = myTitleBar.getmCustomView();
        textView = (TextView) view.findViewById(R.id.txt_title_search);
        title_search = getResources().getDrawable(R.drawable.title_search);
        title_search_scrolled = getResources().getDrawable(R.drawable.title_search_scrolled);
        title_search.setBounds(0, 0, title_search.getMinimumWidth(), title_search.getMinimumHeight());
        title_search_scrolled.setBounds(0, 0, title_search_scrolled.getMinimumWidth(), title_search_scrolled.getMinimumHeight());
        bus.register(this);
        setListener();
        ultimateRecyclerView.setHasFixedSize(true);
        gridLayoutManager = new BasicGridLayoutManager(getActivity(), 2, OrientationHelper.VERTICAL, false, myAdapter);
        ultimateRecyclerView.setLayoutManager(gridLayoutManager);
        ultimateRecyclerView.setAdapter(myAdapter);
        ultimateRecyclerView.enableLoadmore();
        myAdapter.setCustomLoadMoreView(R.layout.custom_bottom_progressbar);
        afterLoadMore();
        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
                if (myAdapter.getItems().size() >= myAdapter.getTotal()) {
//                    AndroidTool.showToast(HomeFragment.this, "没有更多的数据了！~");
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
                SearchActivity_.intent(HomeFragment.this).start();
            }
        });

        ultimateRecyclerView.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
                myTitleBar.setVisibility(View.VISIBLE);
                if (scrollY < 0) {
                    myTitleBar.getBackground().setAlpha(0);
                } else if (scrollY < 500) {
                    progress = (scrollY / 2);//255
                    myTitleBar.getBackground().mutate().setAlpha(progress);
                    if (scrollY < 250) {
                        myTitleBar.setNavigationIcon(R.drawable.title_category);
                        myTitleBar.setRightButtonIcon(R.drawable.title_cart);
                        view.setBackgroundResource(R.drawable.title_selector);
                        textView.setHintTextColor(white_color);
                        textView.setCompoundDrawables(title_search, null, null, null);
                    } else {
                        myTitleBar.setNavigationIcon(R.drawable.title_category_scrolled);
                        myTitleBar.setRightButtonIcon(R.drawable.title_cart_scrolled);
                        view.setBackgroundResource(R.drawable.title_scrolled_selector);
                        textView.setHintTextColor(home_search_text_scrolled);
                        textView.setCompoundDrawables(title_search_scrolled, null, null, null);
                    }
                } else {
                    myTitleBar.getBackground().mutate().setAlpha(250);
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
        refreshingMaterial();
//        paint.setStrokeWidth(1);
//        paint.setColor(line_color);
//        ultimateRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).margin(35).visibilityProvider(new FlexibleDividerDecoration.VisibilityProvider() {
//            @Override
//            public boolean shouldHideDivider(int position, RecyclerView parent) {
//                return position == 0;
//            }
//        }).paint(paint).build());

        setAd();
    }

    @UiThread
    void setAd() {
        List<AdInfo> advList = new ArrayList<>();
        for (AdvertModel am : app.getHomePupAd()) {
            AdInfo adInfo = new AdInfo();
            adInfo.setActivityImg(am.AdvertImg);
            adInfo.setAdId(am.JumpType + "");
            adInfo.setUrl(am.InfoId);
            adInfo.setTitle(am.AdsenseTypeName);
            advList.add(adInfo);
        }
        adManager = new AdManager(getActivity(), advList);
        adManager.setOverScreen(true)
                .setSpeed(1)
                .setBounciness(5)
                .setPageTransformer(new DepthPageTransformer())
                .setOnImageClickListener(new AdManager.OnImageClickListener() {
                    @Override
                    public void onImageClick(View view, AdInfo advInfo) {
                        //1跳转到店铺页面  2跳转到商品页面
                        if ("1".equals(advInfo.getAdId())) {
                            StoreInformationActivity_.intent(getActivity()).storeId(advInfo.getUrl()).start();
                        } else if ("2".equals(advInfo.getAdId())) {
                            GoodsDetailActivity_.intent(getActivity()).goodsId(advInfo.getUrl()).start();
                        } else if ("3".equals(advInfo.getAdId())) {
                            CommonWebViewActivity_.intent(getActivity()).title(advInfo.getTitle()).methodName(advInfo.getUrl()).start();
                        } else if ("4".equals(advInfo.getAdId())) {
//                            CategoryActivity_.intent(getActivity()).goodsTypeId(advInfo.getUrl()).goodsType("2").title(advInfo.getTitle()).start();
                            CommonSearchResultActivity_.intent(getActivity()).goodsTypeId(Integer.valueOf(advInfo.getUrl())).start();
                        }
                    }
                })
        ;
        adManager.showAdDialog(AdConstant.ANIM_UP_TO_DOWN);
        closeAd();
    }

    @UiThread(delay = 5000)
    void closeAd() {
        adManager.dismissAdDialog();
    }


    @Click
    void img_shangjia() {
        MerchantSearchResultActivity_.intent(this).searchContent("").startForResult(1000);
//        String url = "mqqwpa://im/chat?chat_type=wpa&uin=261227869";
//        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    @OnActivityResult(1000)
    void onResult(int requestCode) {
        if (requestCode == 1000) {
            SearchActivity_.intent(this).isMerchant(true).start();
        }
    }


    void setListener() {
        myAdapter.setBindHeaderViewHolder(new BaseUltimateRecyclerViewAdapter.BindHeaderViewHolder() {
            @Override
            public void onBindHeaderViewHolder(BaseUltimateViewHolder viewHolder) {
                UltimateRecyclerView.CustomRelativeWrapper customRelativeWrapper = (UltimateRecyclerView.CustomRelativeWrapper) viewHolder.itemView;
                itemView = (HomeAdvertisementItemView) (customRelativeWrapper.getChildAt(0));
                itemView.init(app.getAdvertModelList());
            }
        });
        myAdapter.setOnItemClickListener(new BaseUltimateRecyclerViewAdapter.OnItemClickListener<Goods>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, Goods obj, int position) {
                GoodsDetailActivity_.intent(HomeFragment.this).goodsId(obj.GoodsInfoId).start();
            }

            @Override
            public void onHeaderClick(RecyclerView.ViewHolder viewHolder, int position) {
            }
        });
        myTitleBar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkUserIsLogin()) {
                    CartActivity_.intent(HomeFragment.this).start();
                } else {
                    LoginActivity_.intent(HomeFragment.this).start();
                }
            }
        });

        myTitleBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).tabHost.setCurrentTab(1);
            }
        });
    }

    void afterLoadMore() {
        myAdapter.getMoreData(pageIndex, 10, isRefresh, 1);
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
                boolean g = PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                if (g) {
                    myTitleBar.setVisibility(View.GONE);
                } else {
                    myTitleBar.setVisibility(View.VISIBLE);
                }
                return g;
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
        showTitleBar();
    }

    @UiThread(delay = 1000)
    void showTitleBar() {
        myTitleBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            bus.unregister(this);
            if (itemView != null) {
                itemView.stopAutoCycle();
            }
        } else {
            bus.register(this);
            if (itemView != null) {
                itemView.startAutoCycle();
            }
        }
    }
}
